package br.com.apicriptomoeda.service.criptomoeda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apicriptomoeda.criptomoeda.dto.RSI;
import br.com.apicriptomoeda.criptomoeda.dto.Variancia;
import br.com.apicriptomoeda.model.criptomoeda.Alerta;
import br.com.apicriptomoeda.model.criptomoeda.Moeda;
import br.com.apicriptomoeda.model.criptomoeda.Valor;
import br.com.apicriptomoeda.model.firebase.PushNotificationRequest;
import br.com.apicriptomoeda.repository.criptomoeda.MoedaRepository;
import br.com.apicriptomoeda.repository.criptomoeda.ValorRepository;
import br.com.apicriptomoeda.service.firebase.PushNotificationService;
import br.com.comunicacao.cliente.email.model.DadosEmail;
import br.com.comunicacao.cliente.email.service.EmailServiceCliente;
import br.com.comunicacao.cliente.sms.model.Sms;
import br.com.comunicacao.cliente.sms.service.SmsServiceCliente;

@Service
public class RSIService {

	@Autowired
	MoedaRepository moedaRepository;
	@Autowired
	ValorRepository valorRepository;
	@Autowired
	BinanceWebClient binanceWebClient;
	@Autowired
	PushNotificationService pushNotificationService;
	@Autowired
	AlertaService alertaService;

	// private List<Variancia> getVariancia(Moeda moeda, List<Valor> listValor,
	// String perido, String tipoIntervalo, int qnt) throws Exception {
	private List<Variancia> getVariancia(List<Valor> listValor, int qnt) throws Exception {
		List<Variancia> listVariancia = new ArrayList<>();
		Valor valor1 = listValor.get(0);
		for (int i = 1; i < listValor.size(); i++) {

			Valor valor2 = listValor.get(i);
			double differFechamento = valor2.getValNrClose() - valor1.getValNrClose();
			double differAbertura = valor2.getValNrOpen() - valor1.getValNrOpen();
			double differMin = valor2.getValNrLow() - valor1.getValNrLow();
			double differMax = valor2.getValNrHigh() - valor1.getValNrHigh();

			Variancia variancia = new Variancia();
			if (differFechamento < 0) {
				variancia.setVarNegativoFechamento(-1 * differFechamento);
			} else {
				variancia.setVarPositivoFechamento(differFechamento);
			}
			if (differAbertura < 0) {
				variancia.setVarNegativoAbertura(-1 * differAbertura);
			} else {
				variancia.setVarPositivoAbertura(differAbertura);
			}

			if (differMax < 0) {
				variancia.setVarNegativoMax(-1 * differMax);
			} else {
				variancia.setVarPositivoMax(differMax);
			}
			if (differMin < 0) {
				variancia.setVarNegativoMin(-1 * differMin);
			} else {
				variancia.setVarPositivoMin(differMin);
			}

			// System.out.println("Variancia: " + variancia);
			listVariancia.add(variancia);
			valor1 = valor2;
		}

		return listVariancia;
	}

	private List<Variancia> getVarianciaAnalise(Moeda moeda, String perido, String tipoIntervalo, int qnt)
			throws Exception {
		List<Valor> listValor = binanceWebClient.loadValoresBinanceByMoedaLimite(moeda.getMoeTxSigla(), perido,
				tipoIntervalo, qnt);
		List<Variancia> listVariancia = new ArrayList<>();
		if (listValor.size() > qnt) {
			Valor valor1 = listValor.get(0);
			System.out.println("Preco fechamento: " + valor1.getValNrClose());
			for (int i = 1; i < listValor.size(); i++) {

				Valor valor2 = listValor.get(i);
				double differFechamento = valor2.getValNrClose() - valor1.getValNrClose();
				double differAbertura = valor2.getValNrOpen() - valor1.getValNrOpen();
				double differMin = valor2.getValNrLow() - valor1.getValNrLow();
				double differMax = valor2.getValNrHigh() - valor1.getValNrHigh();

				Variancia variancia = new Variancia();
				if (differFechamento < 0) {
					variancia.setVarNegativoFechamento(-1 * differFechamento);
				} else {
					variancia.setVarPositivoFechamento(differFechamento);
				}
				if (differAbertura < 0) {
					variancia.setVarNegativoAbertura(-1 * differAbertura);
				} else {
					variancia.setVarPositivoAbertura(differAbertura);
				}

				if (differMax < 0) {
					variancia.setVarNegativoMax(-1 * differMax);
				} else {
					variancia.setVarPositivoMax(differMax);
				}
				if (differMin < 0) {
					variancia.setVarNegativoMin(-1 * differMin);
				} else {
					variancia.setVarPositivoMin(differMin);
				}
				listVariancia.add(variancia);
				valor1 = valor2;
			}
		}
		return listVariancia;
	}

	private RSI calcular(Moeda moeda, String periodo, String tipoIntervalo, int qnt, int limiteMax, int limiteMin)
			throws Exception {
		List<Valor> listValor = binanceWebClient.loadValoresBinanceByMoedaLimite(moeda.getMoeTxSigla(), periodo,
				tipoIntervalo, qnt);
		Valor valorAtual = listValor.get(listValor.size()-1);
		List<Variancia> listVar = getVariancia(listValor, qnt);
		int cont = 0;
		double mediaVarPositivoFechamento = 0;
		double mediaVarNegativoFechamento = 0;

		int qntNegativo = 0;
		int qntPositivo = 0;
		for (int i = 0; i < 14; i++) {
			Variancia var = listVar.get(i);
			mediaVarPositivoFechamento += var.getVarPositivoFechamento();
			mediaVarNegativoFechamento += var.getVarNegativoFechamento();
		}
		mediaVarPositivoFechamento = mediaVarPositivoFechamento / 14;
		mediaVarNegativoFechamento = mediaVarNegativoFechamento / 14;
		int newQnt = listVar.size() - 14;
		if (newQnt > 0) {
			for (int i = 14; i < listVar.size(); i++) {
				Variancia var = listVar.get(i);
				mediaVarPositivoFechamento = ((mediaVarPositivoFechamento * 13) + var.getVarPositivoFechamento()) / 14;
				mediaVarNegativoFechamento = ((mediaVarNegativoFechamento * 13) + var.getVarNegativoFechamento()) / 14;
			}
		}

		double rsFechamento = mediaVarPositivoFechamento / mediaVarNegativoFechamento;

		double rsiFechamento = 100 - (100 / (1 + rsFechamento));
		System.out.println("RSI Fec: " + rsiFechamento);
		if (rsiFechamento >= limiteMax) {
			System.out.println("Sobrecompra: " + rsiFechamento);
		} else if (rsiFechamento <= limiteMin) {
			System.out.println("Sobrevenda: " + rsiFechamento);
		} else {
			System.out.println("Normal: " + rsiFechamento);
		}

		RSI rsi = new RSI();
		rsi.setPeriodo(periodo + tipoIntervalo);
		rsi.setMoeda(moeda);
		rsi.setRsiFechamento(valorAtual.getValNrClose());
		rsi.setRsiAbertura(valorAtual.getValNrOpen());
		rsi.setRsiMax(valorAtual.getValNrHigh());
		rsi.setRsiMin(valorAtual.getValNrLow());
		rsi.setRsi(rsiFechamento);
		return rsi;
	}

	public List<RSI> calulcarRsiAll(String periodo, String tipoIntervalo, int qnt, int limiteMax, int limiteMin) {
		List<RSI> listRsi = new ArrayList<>();
		try {
			List<Moeda> listMoedas = moedaRepository.findByMoeTxStatusOrderByMoeTxNome("A");
			for (Moeda moeda : listMoedas) {
				RSI rsi = calcular(moeda, periodo, tipoIntervalo, qnt, limiteMax, limiteMin);
				if (rsi.getRsi() >= limiteMax) {
					rsi.setObs("Sobrecompra:");
					rsi.setTipo(RSI.SOBRE_COMPRA);
					registraEComunincaAlerta(rsi);

				} else if (rsi.getRsi() <= limiteMin) {
					rsi.setObs("Sobrevenda:");
					rsi.setTipo(RSI.SOBRE_VENDA);
					registraEComunincaAlerta(rsi);

				} else {
					rsi.setObs("Normal");
					rsi.setTipo(RSI.NORMAL);
					System.out.println("Normal1:" + rsi.toString());
					// registraEComunincaAlerta(rsi);
				}
				listRsi.add(rsi);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listRsi;
	}

	/**
	 * Registra o alerta e envia o push
	 * 
	 * @param rsi
	 */
	private void registraEComunincaAlerta(RSI rsi) {
		Alerta alerta = rsi.toAlerta();
		alertaService.criarAlerta(alerta);
		sendComunicado(rsi);
	}

	public void sendComunicado(RSI rsi) {
		System.out.println("Enviando email RSI atencao " + rsi);
		PushNotificationRequest request = new PushNotificationRequest();
		request.setMessage(rsi.toString());

		LocalDateTime dateTime = LocalDateTime.now();
		String dtHr = dateTime.format(DateTimeFormatter.ofPattern("dd/MM HH:mm"));
		switch (rsi.getTipo()) {
		case RSI.NORMAL:
			break;
		case RSI.SOBRE_COMPRA:
			request.setTitle("Alerta SOBRECOMPRA: " + dtHr);
			sendPush(request);
			sendEmail();
			break;
		case RSI.SOBRE_VENDA:
			request.setTitle("Alerta SOBREVENDA: " + dtHr);
			sendPush(request);
			sendEmail();
			break;
		}
	}

	public void sendEmail() {

	}

	public void sendPush(PushNotificationRequest request) {

		pushNotificationService.sendPushNotificationAllToken(request);
	}
}

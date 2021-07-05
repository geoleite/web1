package br.com.ifs.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.ifs.dto.PrecoAtual;

@Service
public class ClienteWSBinance {
	
	private WebClient webClient = null;
	
	public ClienteWSBinance() {
		// TODO Auto-generated constructor stub
		//webClient = WebClient.create("https://api.binance.com/api/v3");
		
		webClient = WebClient.builder().baseUrl("https://api.binance.com/api/v3")
				.exchangeStrategies(ExchangeStrategies.builder()
	            .codecs(configurer -> configurer
	                      .defaultCodecs()
	                      .maxInMemorySize(16*1024*1024)
	                      )
	                    .build())
	                  .build();
		
	}
	
	public Object[][] loadDataBinance(String moeda, String intervalo) {
		Object[][] dados = this.webClient.get().uri("/klines?symbol={moeda}&interval={intervalo}m", moeda, intervalo)
				.retrieve().bodyToMono(Object[][].class).block();

		return dados;
		
	}
	
	public void loadMoedas() {
		//https://api.binance.com/api/v3/exchangeInfo
		Object dados = this.webClient.get().uri("/exchangeInfo")
				.retrieve().bodyToMono(Object.class).block();
		//inserir
		//if (symbol == "USDT")
	}

	public PrecoAtual getValorAtual(String simbolo) {
		//https://api.binance.com/api/v3/exchangeInfo
		PrecoAtual dados = this.webClient.get().uri("/ticker/price?symbol={simbolo}", simbolo)
				.retrieve().bodyToMono(PrecoAtual.class).block();
		return dados;
	}
	
}

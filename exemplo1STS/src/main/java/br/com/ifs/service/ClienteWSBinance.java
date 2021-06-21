package br.com.ifs.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ClienteWSBinance {

	private WebClient webClient = null;
	
	public ClienteWSBinance() {
		// TODO Auto-generated constructor stub
		webClient = WebClient.create("https://api.binance.com/api/v3");
	}
	
	public Object[][] loadDataBinance(String moeda, String intervalo) {
		Object[][] dados = this.webClient.get().uri("/klines?symbol={moeda}&interval={intervalo}m", moeda, intervalo)
				.retrieve().bodyToMono(Object[][].class).block();

		return dados;
		
	}
		
}

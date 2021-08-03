package br.com.ifs.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.ifs.service.MoedaService;

@Component
@EnableScheduling
public class ThreadMoedas {
	
	@Autowired
	private MoedaService moedaService;
	
	@Scheduled(fixedDelay = 5 * 60 * 1000)
	public void run() {
		moedaService.fazAMagiaAcontecer();
	}

}

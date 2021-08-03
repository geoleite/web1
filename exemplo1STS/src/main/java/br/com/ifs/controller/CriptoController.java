package br.com.ifs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifs.service.ClienteWSBinance;


@RestController
@RequestMapping("cripto")
public class CriptoController {

	@Autowired
	ClienteWSBinance clienteWSBinance;
	

	@CrossOrigin
	@RequestMapping(value = "/loadData/{moeda}/{intervalo}", method = RequestMethod.GET)
	public Object loadData(@PathVariable String moeda, @PathVariable String intervalo) {
		return clienteWSBinance.loadDataBinance(moeda, intervalo);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/getMoedas", method = RequestMethod.GET)
	public Object getMoedas() throws Exception {
		return clienteWSBinance.loadMoedas();
	} 

	@CrossOrigin
	@RequestMapping(value = "/getValorMoeda/{simbolo}", method = RequestMethod.GET)
	public Object getMoedas(@PathVariable String simbolo) throws Exception {
		return clienteWSBinance.getValorAtual(simbolo);
	}
	
}

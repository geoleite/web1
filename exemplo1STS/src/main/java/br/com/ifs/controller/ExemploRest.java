package br.com.ifs.controller;

import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifs.dto.AlunoDTO;
import br.com.ifs.model.Aluno;
import br.com.ifs.service.AlunoService;
import br.com.ifs.service.AvaliacaoService;

@RestController
@RequestMapping("exemplo")
public class ExemploRest {

	@Autowired
	private AlunoService alunoService;
	@Autowired
	private AvaliacaoService avaliacaoService;
	
	@RequestMapping(value = "/olamundo", method = RequestMethod.GET)
	public Object olamundo() {
		AlunoDTO aluno = new AlunoDTO();
		aluno.setMatricula("12312");
		aluno.setNome("George Leite");
		aluno.setFone("799999903939");
		aluno.setEndereco("Rua Beco dos cocos");
		return aluno;
	}	

	@RequestMapping(value = "/novoAluno", method = RequestMethod.POST)
	public Object novoAluno(@RequestBody Aluno aluno) throws Exception {
		alunoService.cadastrar(aluno);
		return aluno;
	}
	
	@RequestMapping(value = "/novoAluno", method = RequestMethod.GET)
	public Object getAllAlunos() throws Exception {
		return alunoService.getAll();
	}
	
	@RequestMapping(value = "/getAvaliacoesByAluno", method = RequestMethod.GET)
	public Object getAvaliacoesByAluno(@RequestBody Aluno aluno) throws Exception {
		return avaliacaoService.getByAluno(aluno.getId());
	}
	
}

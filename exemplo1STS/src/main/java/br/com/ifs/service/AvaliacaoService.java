package br.com.ifs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifs.model.Avaliacao;
import br.com.ifs.model.VWAvaliacaoAluno;
import br.com.ifs.repository.AvaliacaoRepository;
import br.com.ifs.repository.VWAvaliacaoAlunoRepository;

@Service
public class AvaliacaoService {
	@Autowired
	private VWAvaliacaoAlunoRepository vwAvaliacaoRepository;
	
	public List<VWAvaliacaoAluno> getByAluno(int alunoId) {
		return vwAvaliacaoRepository.findByAlunoId(alunoId);
	}
}

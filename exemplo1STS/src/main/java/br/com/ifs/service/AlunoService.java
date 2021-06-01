package br.com.ifs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.ifs.model.Aluno;
import br.com.ifs.repository.AlunoRepository;
@Service
public class AlunoService {

	@Autowired
	private AlunoRepository alunoRepository;
	
	public void cadastrar(Aluno aluno) throws Exception{
		if (aluno == null) {
			throw new Exception("Aluno nulo!");
		} 
		alunoRepository.save(aluno);
	}
	
	public List<Aluno> getAll() {
		return (List<Aluno>)alunoRepository.findAll();
	}
}

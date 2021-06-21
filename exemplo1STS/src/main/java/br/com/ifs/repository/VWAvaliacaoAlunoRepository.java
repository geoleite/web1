package br.com.ifs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ifs.model.Avaliacao;
import br.com.ifs.model.VWAvaliacaoAluno;

@Repository
public interface VWAvaliacaoAlunoRepository extends JpaRepository<VWAvaliacaoAluno, Integer>{

	List<VWAvaliacaoAluno> findByAlunoId(int id);
}

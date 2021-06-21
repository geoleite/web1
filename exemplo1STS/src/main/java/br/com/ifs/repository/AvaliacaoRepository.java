package br.com.ifs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.ifs.model.Avaliacao;

@Repository
public interface AvaliacaoRepository extends CrudRepository<Avaliacao, Integer>{

	List<Avaliacao> findByAlunoId(int id);
}

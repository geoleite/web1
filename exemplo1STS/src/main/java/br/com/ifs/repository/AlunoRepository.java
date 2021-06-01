package br.com.ifs.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.ifs.model.Aluno;

@Repository
public interface AlunoRepository extends CrudRepository<Aluno, Integer>{

}

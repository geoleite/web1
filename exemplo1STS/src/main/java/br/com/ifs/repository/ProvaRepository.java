package br.com.ifs.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.ifs.model.Prova;

@Repository
public interface ProvaRepository extends CrudRepository<Prova, Integer>{

}

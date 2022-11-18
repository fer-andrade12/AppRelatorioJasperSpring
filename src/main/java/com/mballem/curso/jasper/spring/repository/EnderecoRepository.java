package com.mballem.curso.jasper.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mballem.curso.jasper.spring.entity.Endereco;

public interface EnderecoRepository extends CrudRepository<Endereco, Long>{

	@Query("select district e.uf from Endereco e order by e.uf asc")
	List<String> findByUfs();
}

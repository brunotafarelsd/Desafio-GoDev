package com.br.platform_godev.repository;

import com.br.platform_godev.model.Pessoa;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "pessoa", path = "pessoas")
public interface PessoaRepository extends PagingAndSortingRepository<Pessoa, Long> {

    /**
     * Retorna todas as pessoas
     * @return pessoa do id passado como parâmetro.
     */
    List<Pessoa> findAll();

}

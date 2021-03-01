package com.br.platform_godev.repository;

import com.br.platform_godev.model.Treinamento;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "treinamento", path = "treinamentos")
public interface TreinamentoRepository extends PagingAndSortingRepository<Treinamento, Long> {

}

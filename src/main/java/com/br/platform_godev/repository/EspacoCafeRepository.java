package com.br.platform_godev.repository;

import com.br.platform_godev.model.EspacoCafe;
import com.br.platform_godev.model.Pessoa;
import com.br.platform_godev.model.Sala;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "espaco_cafe", path = "espacos")
public interface EspacoCafeRepository extends PagingAndSortingRepository<EspacoCafe, Long> {

    /**
     * Retorna a soma total de lotações dos espaços de café.
     * @return
     */
    @Query("select sum(lotacao) as total from EspacoCafe ")
    Long findLotacaoTotal();

    /**
     * Retorna todos os registros de espaços de café
     * @return
     */
    List<EspacoCafe> findAll();

}

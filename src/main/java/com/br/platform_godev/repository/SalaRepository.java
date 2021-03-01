package com.br.platform_godev.repository;

import com.br.platform_godev.model.Sala;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "sala", path = "salas")
public interface SalaRepository extends PagingAndSortingRepository<Sala, Long> {
    /**
     * Retorna a soma total de lotações das salas.
     * @return
     */
    @Query("select sum(lotacao) as total from Sala ")
    Long findLotacaoTotal();


    /**
     * Retorna todos os registros de sala
     *
     * @return pessoa do id passado como parâmetro.
     */
    List<Sala> findAll();


}

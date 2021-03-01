package com.br.platform_godev.repository;

import com.br.platform_godev.model.EspacoCafe;
import com.br.platform_godev.model.Movimentacao;
import com.br.platform_godev.model.Sala;
import com.br.platform_godev.model.Treinamento;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "movimentacao", path = "movimentacoes")
public interface MovimentacaoRepository extends PagingAndSortingRepository<Movimentacao, Long> {

    /**
     * Busca movimentações por treinamento
     * @param treinamento identificador do treinamento
     * @return
     */
    List<Movimentacao> findByTreinamentoOrderById(Treinamento treinamento);

    /**
     * Busca movimentações por treinamento e pela sala da primeira etapa.
     * @param treinamento
     * @param primeiraSala
     * @return
     */
    List<Movimentacao> findByTreinamentoAndPrimeiraSala(Treinamento treinamento, Sala primeiraSala);

    /**
     * Busca movimentações por treinamento e pelo espaço de café da primeira etapa.
     * @param treinamento
     * @param espacoCafe1
     * @return
     */
    List<Movimentacao> findByTreinamentoAndEspacoCafe1(Treinamento treinamento, EspacoCafe espacoCafe1);

}


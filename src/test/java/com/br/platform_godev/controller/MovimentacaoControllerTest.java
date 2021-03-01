package com.br.platform_godev.controller;

import com.br.platform_godev.repository.EspacoCafeRepository;
import com.br.platform_godev.repository.SalaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.util.Assert;


class MovimentacaoControllerTest {

    @Mock
    SalaRepository salaRepository;

    @Mock
    EspacoCafeRepository espacoCafeRepository;

    @InjectMocks
    MovimentacaoController movimentacaoController;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void validaPessoasELotacaoSala_falha() throws Exception {

        Mockito.when(salaRepository.findLotacaoTotal()).thenReturn(5L);

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            movimentacaoController.validaPessoasELotacaoSala(10L);
        });

        Assertions.assertEquals("Número de Pessoas excedeu a lotação total das salas!", exception.getMessage());
    }

    @Test
    void validaPessoasELotacaoSala_sucesso() throws Exception {
        Mockito.when(salaRepository.findLotacaoTotal()).thenReturn(10L);
        movimentacaoController.validaPessoasELotacaoSala(5L);
    }

    @Test
    void validaPessoasELotacaoCafe_falha() throws Exception {

        Mockito.when(espacoCafeRepository.findLotacaoTotal()).thenReturn(5L);

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            movimentacaoController.validaPessoasELotacaoCafe(10L);
        });

        Assertions.assertEquals("Número de Pessoas excedeu a lotação total dos espaços de café!", exception.getMessage());
    }

    @Test
    void validaPessoasELotacaoCafe_sucesso() throws Exception {
        Mockito.when(espacoCafeRepository.findLotacaoTotal()).thenReturn(10L);
        movimentacaoController.validaPessoasELotacaoCafe(5L);
    }
}
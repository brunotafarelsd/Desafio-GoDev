package com.br.platform_godev.controller;

import com.br.platform_godev.model.*;
import com.br.platform_godev.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MovimentacaoController {

    private final MovimentacaoRepository movimentacaoRepository;
    private final PessoaRepository pessoaRepository;
    private final SalaRepository salaRepository;
    private final EspacoCafeRepository espacoCafeRepository;
    private final TreinamentoRepository treinamentoRepository;

    public MovimentacaoController(MovimentacaoRepository movimentacaoRepository, PessoaRepository pessoaRepository, SalaRepository salaRepository, EspacoCafeRepository espacoCafeRepository, TreinamentoRepository treinamentoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.pessoaRepository = pessoaRepository;
        this.salaRepository = salaRepository;
        this.espacoCafeRepository = espacoCafeRepository;
        this.treinamentoRepository = treinamentoRepository;
    }

    /**
     * Lista todas as movimentações
     * @param pessoaId
     * @param cafeId
     * @param salaId
     * @return
     * @throws Exception
     */
    @CrossOrigin
    @GetMapping("/lista-movimentacoes")
    ResponseEntity<Object> gerarMovimentacao(String pessoaId, String cafeId, String salaId) throws Exception {
        try {
            return ResponseEntity.ok(
                    movimentacaoRepository.findAll()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Api que inicia o treinamento e cria as movimentações de acordo com as regras descritas na doc. de requisito.
     * @param nomeTreinamento
     * @return
     * @throws Exception
     */
    @CrossOrigin
    @GetMapping("/gerar-movimentacao")
    ResponseEntity<Object> gerarMovimentacao(@RequestParam String nomeTreinamento) throws Exception {
        try {
            Long totalPessoa = pessoaRepository.count();
            validaPessoasELotacaoSala(totalPessoa);
            validaPessoasELotacaoCafe(totalPessoa);

            Treinamento treinamento = crearTreinamento(nomeTreinamento);

            gerarMovimentacaoPessoa(treinamento);

            List<Movimentacao> movimentacaoList = movimentacaoRepository.findByTreinamentoOrderById(treinamento);
            adicionarSalaPrimeiraEtapa(movimentacaoList, treinamento);
            adicionarEspacoCafePrimeiraEtapa(movimentacaoList, treinamento);

            adicionarSalaSegundaEtapa(movimentacaoList, treinamento);
            adicionarEspacoCafeSegundaEtapa(movimentacaoList, treinamento);

            return ResponseEntity.ok("OK");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Treinamento crearTreinamento(String name) {
        Treinamento treinamento = new Treinamento();
        treinamento.setNome(name);

        return treinamentoRepository.save(treinamento);
    }

    ;

    protected void validaPessoasELotacaoSala(Long qtd_pessoas) throws Exception {
        Long lotacaoTotal = salaRepository.findLotacaoTotal();

        if (qtd_pessoas > lotacaoTotal) {
            throw new Exception("Número de Pessoas excedeu a lotação total das salas!");
        }
    }

    protected void validaPessoasELotacaoCafe(Long qtd_pessoas) throws Exception {
        Long lotacaoTotal = espacoCafeRepository.findLotacaoTotal();

        if (qtd_pessoas > lotacaoTotal) {
            throw new Exception("Número de Pessoas excedeu a lotação total dos espaços de café!");
        }
    }

    private void gerarMovimentacaoPessoa(Treinamento treinamento) {
        List<Pessoa> listaPessoa = pessoaRepository.findAll();

        listaPessoa.forEach(pessoa -> {
            Movimentacao movimentacao = new Movimentacao();

            movimentacao.setTreinamentoId(treinamento);
            movimentacao.setPessoaId(pessoa);

            movimentacaoRepository.save(movimentacao);
        });

    }

    private void adicionarSalaPrimeiraEtapa(List<Movimentacao> listaMovimentacoes, Treinamento treinamento) {
        List<Sala> salas = salaRepository.findAll();

        int qtd_por_sala = listaMovimentacoes.size() / salas.size();

        for (int i = 0; i < salas.size(); i++) {
            int sequence = i;

            movimentacaoRepository.findByTreinamentoOrderById(treinamento).subList(i * qtd_por_sala, qtd_por_sala+i)
                    .forEach(movimentacao -> {
                        movimentacao.setPrimeiraSala(salas.get(sequence));
                        movimentacaoRepository.save(movimentacao);
                    });

            if (i == salas.size() - 1 && qtd_por_sala * salas.size() < listaMovimentacoes.size()) {
                listaMovimentacoes.get(listaMovimentacoes.size() - 1).setPrimeiraSala(salas.get(sequence));
                movimentacaoRepository.save(listaMovimentacoes.get(0));
            }
        }

    }

    private void adicionarEspacoCafePrimeiraEtapa(List<Movimentacao> listaMovimentacoes, Treinamento treinamento) {

        List<EspacoCafe> espacoCafe = espacoCafeRepository.findAll();

        int divisao_movimentacao = listaMovimentacoes.size() / 2;

        int qtd_1 = divisao_movimentacao;
        int qtd_2 = divisao_movimentacao;

        if (qtd_1 > espacoCafe.get(0).getLotacao()) {
            int diferenca = qtd_1 - espacoCafe.get(0).getLotacao();
            qtd_1 = divisao_movimentacao - diferenca;
            qtd_2 = diferenca + divisao_movimentacao;
        }
        if (qtd_2 > espacoCafe.get(1).getLotacao()) {
            int diferenca = qtd_2 - espacoCafe.get(1).getLotacao();
            qtd_2 = divisao_movimentacao - diferenca;
            qtd_1 = diferenca + divisao_movimentacao;
        }

        movimentacaoRepository.findByTreinamentoOrderById(treinamento).subList(0, qtd_1)
                .forEach(movimentacao -> {
                    movimentacao.setEspacoCafe1Id(espacoCafe.get(0));
                    movimentacaoRepository.save(movimentacao);
                });

        movimentacaoRepository.findByTreinamentoOrderById(treinamento).subList(qtd_1, qtd_2+qtd_1)
                .forEach(movimentacao -> {
                    movimentacao.setEspacoCafe1Id(espacoCafe.get(1));
                    movimentacaoRepository.save(movimentacao);
                });

        if (divisao_movimentacao * espacoCafe.size() < listaMovimentacoes.size()) {
            listaMovimentacoes.get(listaMovimentacoes.size() - 1).setEspacoCafe1Id(espacoCafe.get(1));
            movimentacaoRepository.save(listaMovimentacoes.get(0));
        }

    }

    private void adicionarSalaSegundaEtapa(List<Movimentacao> listaMovimentacoes, Treinamento treinamento) {
        if(listaMovimentacoes.size() > 0) {
            List<Sala> salas = salaRepository.findAll();
            int metade_movimentacoes = listaMovimentacoes.size() / 2;

            for (int i = 0; i < salas.size(); i++) {
                int sequencia = i;
                movimentacaoRepository.findByTreinamentoAndPrimeiraSala(treinamento, salas.get(i))
                        .forEach(movimentacao -> {
                            if (sequencia + 1 < salas.size()) {
                                movimentacao.setSegundaSalaId(salas.get(sequencia + 1));
                            } else {
                                movimentacao.setSegundaSalaId(salas.get(0));
                            }
                            movimentacaoRepository.save(movimentacao);
                        });
            }
        }
    }

    private void adicionarEspacoCafeSegundaEtapa(List<Movimentacao> listaMovimentacoes, Treinamento treinamento) {
        if(listaMovimentacoes.size() > 0) {
            List<EspacoCafe> cafeList = espacoCafeRepository.findAll();
            int metade_movimentacoes = listaMovimentacoes.size() / 2;

            for (int i = 0; i < cafeList.size(); i++) {
                int sequencia = i;
                movimentacaoRepository.findByTreinamentoAndEspacoCafe1(treinamento, cafeList.get(i))
                        .forEach(movimentacao -> {
                            if (sequencia + 1 < cafeList.size()) {
                                movimentacao.setEspacoCafe2Id(cafeList.get(sequencia + 1));
                            } else {
                                movimentacao.setEspacoCafe2Id(cafeList.get(0));
                            }
                            movimentacaoRepository.save(movimentacao);
                        });
            }
        }
    }
}

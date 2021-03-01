package com.br.platform_godev.controller;

import com.br.platform_godev.model.Sala;
import com.br.platform_godev.repository.SalaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SalaController {

    private final SalaRepository salaRepository;

    public SalaController(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    /**
     * Lista todos as salas cadastradas
     *
     * @return
     * @throws Exception
     */
    @CrossOrigin
    @GetMapping("/lista-salas")
    ResponseEntity<Object> listaSalas() throws Exception {
        try {
            return ResponseEntity.ok(salaRepository.findAll());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Api que cria sala
     *
     * @param nome
     * @param lotacao
     * @return
     * @throws Exception
     */
    @CrossOrigin
    @GetMapping("/criar-sala")
    ResponseEntity<Object> criarSala(String nome, int lotacao) throws Exception {
        try {
            Sala newSala = new Sala();
            newSala.setNome(nome);
            newSala.setLotacao(lotacao);

            salaRepository.save(newSala);

            return ResponseEntity.ok("OK");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

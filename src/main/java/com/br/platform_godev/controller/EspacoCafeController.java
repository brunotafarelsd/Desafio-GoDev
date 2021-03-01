package com.br.platform_godev.controller;

import com.br.platform_godev.model.EspacoCafe;
import com.br.platform_godev.repository.EspacoCafeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EspacoCafeController {

    private final EspacoCafeRepository espacoCafeRepository;

    public EspacoCafeController(EspacoCafeRepository espacoCafeRepository) {
        this.espacoCafeRepository = espacoCafeRepository;
    }

    /**
     * Lista todos os espaços de café cadastrado
     * @return
     * @throws Exception
     */
    @CrossOrigin
    @GetMapping("/lista-cafe")
    ResponseEntity<Object> listaSalas() throws Exception {
        try {
            return ResponseEntity.ok(espacoCafeRepository.findAll());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Api que cria espaços de café
     * @param nome
     * @param lotacao
     * @return
     * @throws Exception
     */
    @CrossOrigin
    @GetMapping("/criar-espaco-cafe")
    ResponseEntity<Object> criarEspacoCafe(String nome, int lotacao) throws Exception {
        try {
            EspacoCafe newEspacoCafe = new EspacoCafe();
            newEspacoCafe.setNome(nome);
            newEspacoCafe.setLotacao(lotacao);

            espacoCafeRepository.save(newEspacoCafe);

            return ResponseEntity.ok("OK");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

package com.br.platform_godev.controller;

import com.br.platform_godev.model.Pessoa;
import com.br.platform_godev.repository.PessoaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PessoaController {

    private final PessoaRepository pessoaRepository;

    public PessoaController(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    /**
     * Lista todos as pessoas cadastradas
     * @return
     * @throws Exception
     */
    @CrossOrigin
    @GetMapping("/lista-pessoas")
    ResponseEntity<Object> listaPessoas() throws Exception {
        try {
            return ResponseEntity.ok(pessoaRepository.findAll());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Api que cria pessoas
     * @param nome
     * @param sobrenome
     * @return
     * @throws Exception
     */
    @CrossOrigin
    @GetMapping("/criar-pessoa")
    ResponseEntity<Object> criarPessoa(String nome, String sobrenome) throws Exception {
        try {

            Pessoa newPessoa = new Pessoa();
            newPessoa.setNome(nome);
            newPessoa.setSobrenome(sobrenome);


            pessoaRepository.save(newPessoa);

            return ResponseEntity.ok("OK");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

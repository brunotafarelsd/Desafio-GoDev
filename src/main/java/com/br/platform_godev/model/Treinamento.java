package com.br.platform_godev.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "treinamento")
public class Treinamento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

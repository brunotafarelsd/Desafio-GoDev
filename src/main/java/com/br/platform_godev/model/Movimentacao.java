package com.br.platform_godev.model;

import javax.persistence.*;

@Entity
@Table(name = "movimentacao")
public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(targetEntity=Pessoa.class, fetch=FetchType.EAGER)
    @JoinColumn(name="pessoa_id")
    private Pessoa pessoa;

    @OneToOne(targetEntity=Sala.class, fetch=FetchType.EAGER)
    @JoinColumn(name="primeira_sala_id")
    private Sala primeiraSala;

    @OneToOne(targetEntity=Sala.class, fetch=FetchType.EAGER)
    @JoinColumn(name="segunda_sala_id")
    private Sala segundaSala;

    @OneToOne(targetEntity=EspacoCafe.class, fetch=FetchType.EAGER)
    @JoinColumn(name="espaco_cafe_1_id")
    private EspacoCafe espacoCafe1;

    @OneToOne(targetEntity=EspacoCafe.class, fetch=FetchType.EAGER)
    @JoinColumn(name="espaco_cafe_2_id")
    private EspacoCafe espacoCafe2;

    @OneToOne(targetEntity=Treinamento.class, fetch=FetchType.EAGER)
    @JoinColumn(name="treinamento_id")
    private Treinamento treinamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getPessoaId() {
        return pessoa;
    }

    public void setPessoaId(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public void setPrimeiraSala(Sala sala_1) {
        this.primeiraSala = sala_1;
    }

    public Sala getPrimeiraSala() {
        return primeiraSala;
    }

    public void setSegundaSalaId(Sala sala_2) {
        this.segundaSala = sala_2;
    }

    public Sala getSegundaSalaId() {
        return segundaSala;
    }

    public EspacoCafe getEspacoCafe1Id() {
        return espacoCafe1;
    }

    public void setEspacoCafe1Id(EspacoCafe espacoCafe1) {
        this.espacoCafe1 = espacoCafe1;
    }

    public EspacoCafe getEspacoCafe2Id() {
        return espacoCafe2;
    }

    public void setEspacoCafe2Id(EspacoCafe espacoCafe2) {
        this.espacoCafe2 = espacoCafe2;
    }

    public Treinamento getTreinamentoId() {
        return treinamento;
    }

    public void setTreinamentoId(Treinamento treinamento) {
        this.treinamento = treinamento;
    }
}

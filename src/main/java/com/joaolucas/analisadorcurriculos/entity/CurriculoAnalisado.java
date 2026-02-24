package com.joaolucas.analisadorcurriculos.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "curriculos_analisados")
public class CurriculoAnalisado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;

    @Column(name = "link_linkedin")
    private String linkLinkedin;

    @Column(name = "link_github")
    private String linkGithub;

    @ElementCollection
    @CollectionTable(name = "curriculo_habilidades", joinColumns = @JoinColumn(name = "curriculo_id"))
    @Column(name = "habilidade")
    private List<String> listaDeHabilidades;

    @Column(columnDefinition = "TEXT")
    private String resumoExperiencia;

    private String nivelEstimado;

    private LocalDateTime dataAnalise;

    @PrePersist
    public void prePersist() {
        dataAnalise = LocalDateTime.now();
    }

    public CurriculoAnalisado() {
    }

    public CurriculoAnalisado(String nome, String email, String telefone, String linkLinkedin, String linkGithub,
            List<String> listaDeHabilidades, String resumoExperiencia, String nivelEstimado) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.linkLinkedin = linkLinkedin;
        this.linkGithub = linkGithub;
        this.listaDeHabilidades = listaDeHabilidades;
        this.resumoExperiencia = resumoExperiencia;
        this.nivelEstimado = nivelEstimado;
    }

    // Getters and Setters necessários para o Hibernate

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getLinkLinkedin() {
        return linkLinkedin;
    }

    public void setLinkLinkedin(String linkLinkedin) {
        this.linkLinkedin = linkLinkedin;
    }

    public String getLinkGithub() {
        return linkGithub;
    }

    public void setLinkGithub(String linkGithub) {
        this.linkGithub = linkGithub;
    }

    public List<String> getListaDeHabilidades() {
        return listaDeHabilidades;
    }

    public void setListaDeHabilidades(List<String> listaDeHabilidades) {
        this.listaDeHabilidades = listaDeHabilidades;
    }

    public String getResumoExperiencia() {
        return resumoExperiencia;
    }

    public void setResumoExperiencia(String resumoExperiencia) {
        this.resumoExperiencia = resumoExperiencia;
    }

    public String getNivelEstimado() {
        return nivelEstimado;
    }

    public void setNivelEstimado(String nivelEstimado) {
        this.nivelEstimado = nivelEstimado;
    }

    public LocalDateTime getDataAnalise() {
        return dataAnalise;
    }

    public void setDataAnalise(LocalDateTime dataAnalise) {
        this.dataAnalise = dataAnalise;
    }
}

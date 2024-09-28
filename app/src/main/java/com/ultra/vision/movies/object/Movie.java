package com.ultra.vision.movies.object;

import java.io.Serializable;

public class Movie implements Serializable {
    private int id;
    private String nome;
    private String logo;
    private String categoria;
    private String type;
    private String link;

    public Movie(String nome, String logo, String categoria, String type, String link) {
        this.nome = nome;
        this.logo = logo;
        this.categoria = categoria;
        this.type = type;
        this.link = link;
    }

    public Movie(String nome, String categoria, String logo, String type) {
        this.nome = nome;
        this.logo = logo;
        this.categoria = categoria;
        this.type = type;
    }

    public Movie(int id, String nome, String categoria, String logo, String type) {
        this.id = id;
        this.nome = nome;
        this.logo = logo;
        this.categoria = categoria;
        this.type = type;
    }
    
    public Movie(int id, String nome, String categoria, String logo, String type, String link) {
        this.id = id;
        this.nome = nome;
        this.logo = logo;
        this.categoria = categoria;
        this.type = type;
        this.link= link;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

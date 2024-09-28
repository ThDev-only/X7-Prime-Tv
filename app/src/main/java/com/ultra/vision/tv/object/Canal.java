package com.ultra.vision.tv.object;

import java.util.List;

public class Canal {
    // private String link;
    private int id;
    private String nome;
    private String logo;
    private String categoria;
    private String type;

    public Canal(String nome, String logo, String categoria) {
        //    this.link = link;
        this.nome = nome;
        this.logo = logo;
        this.categoria = categoria;
    }

    public Canal(String nome, String logo, String categoria, String type) {
        //    this.link = link;
        this.nome = nome;
        this.logo = logo;
        this.categoria = categoria;
        this.type = type;
    }

    public Canal(int id, String nome, String logo, String categoria, String type) {
        //    this.link = link;
        this.id = id;
        this.nome = nome;
        this.logo = logo;
        this.categoria = categoria;
        this.type = type;
    }

    public Canal() {}

    /* public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }*/

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

package com.musicplayer;

public class NoMusica {
    private String nome;
    private String nomeArtista;
    private double minutagem;
    private NoMusica anterior;
    private NoMusica proxima;
    
    /* Sendo o nó mais simples da multilista, o nó "música" é composto apenas pelo nome da música,
    a sua duração e o nome do artista que a criou. Ademais, sabendo que esse nó será componente do
    nó Playlist, que por sua vez será uma lista duplamente encadeada circular, ele também recebe como
    parâmetro ponteiros para a música anterior e posterior. */
    public NoMusica(String nome, double minutagem, String nomeArtista, NoMusica anterior, NoMusica proxima){
        this.nome = nome;
        this.nomeArtista = nomeArtista;
        this.minutagem = minutagem;
        this.anterior = anterior;
        this.proxima = proxima;
    }

    /* Getters e setters. */
    public String getNome() {
        return nome;
    }

    public String getNomeArtista(){
        return nomeArtista;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getMinutagem() {
        return minutagem;
    }

    public void setMinutagem(int minutagem) {
        this.minutagem = minutagem;
    }

    public NoMusica getAnterior() {
        return anterior;
    }

    public void setAnterior(NoMusica anterior) {
        this.anterior = anterior;
    }

    public NoMusica getProxima() {
        return proxima;
    }

    public void setProxima(NoMusica proxima) {
        this.proxima = proxima;
    }

    @Override
    public String toString() {
        return nome + " - " + minutagem + " s";
    }
}

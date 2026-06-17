package com.musicplayer;

public class NoMusica {
    private String nome;
    private String nomeArtista;
    private double minutagem;
    private NoMusica anterior;
    private NoMusica proxima;

    public NoMusica(String nome, double minutagem, String nomeArtista, NoMusica anterior, NoMusica proxima){
        this.nome = nome;
        this.nomeArtista = nomeArtista;
        this.minutagem = minutagem;
        this.anterior = anterior;
        this.proxima = proxima;
    }

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

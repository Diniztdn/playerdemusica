package com.musicplayer;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Player {
    private Playlist headPlaylist;
    private Playlist playlistAtual;
    private ObjectProperty<NoMusica> musicaAtual = new SimpleObjectProperty<>(null);

    public Player() {
        this.headPlaylist = null;
        this.playlistAtual = null;
        this.musicaAtual.set(null);
    }

    public void proximaPlaylist() {
        if (playlistAtual != null) {
            playlistAtual = playlistAtual.getProximaPlaylist();
            if (!playlistAtual.estaVazia()) {
                musicaAtual.set(playlistAtual.getUltimaMusica().getProxima());
            } else {
                musicaAtual.set(null);
            }
            System.out.println("Playlist atual: " + playlistAtual.getNomePlaylist());
        } else {
            System.out.println("O player está vazio.");
        }
    }

    public void playlistAnterior() {
        if (playlistAtual != null) {
            playlistAtual = playlistAtual.getPlaylistAnterior();
            if (!playlistAtual.estaVazia()) {
                musicaAtual.set(playlistAtual.getUltimaMusica().getProxima());
            } else {
                musicaAtual.set(null);
            }
            System.out.println("Voltou para a Playlist: " + playlistAtual.getNomePlaylist());       
        } else {
            System.out.println("O player está vazio.");
        }
    }

    public void proximaMusica() {
        if (musicaAtual.get() != null) {
            NoMusica proxima = musicaAtual.get().getProxima();
            /* Se só existir uma música na playlist, quando ela terminar, ela começa a tocar novamente. */
            if (proxima == musicaAtual.get()) {
                musicaAtual.set(null);
            }
            musicaAtual.set(proxima);
            System.out.println("Tocando agora: " + musicaAtual.get().getNome());
        } else {
            System.out.println("Nenhuma música na playlist para avançar.");
        }
    }

    public void musicaAnterior() {
        if (musicaAtual.get() != null) {
            NoMusica anterior = musicaAtual.get().getAnterior();
            if (anterior == musicaAtual.get()) {
                musicaAtual.set(null);
            }
            musicaAtual.set(anterior);
            System.out.println("Tocando agora: " + musicaAtual.get().getNome());
        } else {
            System.out.println("Nenhuma música na playlist para voltar.");
        }
    }

    public void adicionarPlaylist(String nomePlaylist) {
        Playlist novaPlaylist = new Playlist(nomePlaylist);

        if (headPlaylist == null) {
            headPlaylist = novaPlaylist;
            novaPlaylist.setProximaPlaylist(novaPlaylist);
            novaPlaylist.setPlaylistAnterior(novaPlaylist); 
            
            playlistAtual = novaPlaylist;
            musicaAtual.set(novaPlaylist.estaVazia() ? null : novaPlaylist.getUltimaMusica().getProxima());
            System.out.println("Playlist de " + nomePlaylist + " criada como a primeira!");
            return;
        }
        Playlist ultimo = headPlaylist.getPlaylistAnterior();

        ultimo.setProximaPlaylist(novaPlaylist);
        novaPlaylist.setPlaylistAnterior(ultimo);
        
        novaPlaylist.setProximaPlaylist(headPlaylist);
        headPlaylist.setPlaylistAnterior(novaPlaylist); 

        System.out.println("Playlist de " + nomePlaylist + " adicionada!");
    }

    public void removerPlaylist() {
        if (playlistAtual == null) {
            System.out.println("O player está vazio.");
            return;
        }

        System.out.println("Removendo a playlist: " + playlistAtual.getNomePlaylist());

        if (playlistAtual.getProximaPlaylist() == playlistAtual) {
            headPlaylist = null;
            playlistAtual = null;
            musicaAtual.set(null);
            return;
        } 
        else {
            Playlist anterior = playlistAtual.getPlaylistAnterior();
            Playlist proxima = playlistAtual.getProximaPlaylist();

            anterior.setProximaPlaylist(proxima);
            proxima.setPlaylistAnterior(anterior);

            if (playlistAtual == headPlaylist) {
                headPlaylist = proxima;
            }
            playlistAtual = proxima;
            
            if (!playlistAtual.estaVazia()) {
                musicaAtual.set(playlistAtual.getUltimaMusica().getProxima());
            } 
            else {
                musicaAtual.set(null);
            }
        }
    }
    
    public void removerMusicaAtual() {
        if (playlistAtual == null || musicaAtual.get() == null) {
            System.out.println("Nenhuma música tocando no momento.");
            return;
        }
    
        System.out.println("Removendo a música: " + musicaAtual.get().getNome());
        NoMusica proximaMusica = musicaAtual.get().getProxima();
    
        if (musicaAtual.get() == proximaMusica) {
            playlistAtual.removerNoMusica(musicaAtual.get());
            musicaAtual.set(null);
        } else {
            playlistAtual.removerNoMusica(musicaAtual.get());
            musicaAtual.set(proximaMusica);
        }
    }

    public void inserirMusicaAtual(String nomeMusica, String nomeArtista, double minutagem) {
        if (playlistAtual == null) {
            System.out.println("Não existe playlist ativa para inserir a música.");
            return;
        }

        boolean estavaVazia = playlistAtual.estaVazia();
        playlistAtual.inserirMusica(nomeMusica, nomeArtista, minutagem);

        if (estavaVazia) {
            musicaAtual.set(playlistAtual.getUltimaMusica().getProxima());
        }
    }

    public NoMusica getMusicaAtual(){
        return musicaAtual.get();
    }

    public Playlist getPlaylistAtual(){
        return playlistAtual;
    }
    
    public ObjectProperty<NoMusica> musicaAtualProperty() {
        return musicaAtual;
    }
}

package com.musicplayer;

/*Classe que gerencia a playlist de músicas. Além de gerenciar as músicas em circulo sendo uma 
lista duplamente encadeada circular, ela funciiona também como um nó para uma lista circular 
de playlists gerenciada no Player*/

public class Playlist {
    private String nomePlaylist; // nome da playlist
    private NoMusica ultimaMusica; // guarda a referência da última música, assim a primeira música é sempre 'ultimaMusica.getProxima()'
    private Playlist proximaPlaylist, playlistAnterior;// ponteiros que permtem que as playlists se conectem entre si


    //Construtor que inicializa a playlist e define que ela começa vazia
    public Playlist(String nomePlaylist) {
        this.nomePlaylist = nomePlaylist;
        this.ultimaMusica = null;
        this.proximaPlaylist = this.playlistAnterior = null;
    }

    //método que verifiica se a playlist está vazia 
    public boolean estaVazia() {
        return ultimaMusica == null;
    }

    //método que insere uma nova música na playlist, a inserção é sempre feita no final 
    public void inserirMusica(String nomeMusica, String nomeArtista, double minutagem) {
        //se a playlist estiver vazia cria a primeira música do zero apontando para si mesma nos dois sentidos
        if (estaVazia()) {
            ultimaMusica = new NoMusica(nomeMusica, minutagem, nomeArtista, null, null);
            ultimaMusica.setAnterior(ultimaMusica);
            ultimaMusica.setProxima(ultimaMusica);
            return;
        }
        
        //se já existem músicas na playlist criamos o novo nó informando o anterior e o próximo
        ultimaMusica = new NoMusica(nomeMusica, minutagem, nomeArtista, ultimaMusica, ultimaMusica.getProxima());
        //ajusta os ponteiros dos novos vizinhos para que apontem para a novo música 
        ultimaMusica.getAnterior().setProxima(ultimaMusica);//a antiga última música tem como próxima a nova 
        ultimaMusica.getProxima().setAnterior(ultimaMusica);//a primeira música tem como anterior a nova
    }

    //método que remove uma música específica que foi passada como parâmetro
    public void removerNoMusica(NoMusica alvo) {
        //se a lista estiver vazia ou o alvo for nulo nada acontece
        if (estaVazia() || alvo == null) return;

        //se existe só uma música na playlist a lista volta a ficar vazia 
        if (alvo == alvo.getProxima()) {
            ultimaMusica = null;
        } 
        
        //se existem várias músicas na playlist
        else {
            alvo.getAnterior().setProxima(alvo.getProxima());//o vizinho de trás do alvo agora aponta para o vizinho da frente do alvo
            alvo.getProxima().setAnterior(alvo.getAnterior());//o vizinho da frente do alvo agora aponta para o vizinho de trás do alvo
            
            //se a música removida for a última é importante que a última música seja agora a anterior a ela
            if (alvo == ultimaMusica) {
                ultimaMusica = alvo.getAnterior();
            }
        }
    }
    
    //método que percorre a playlist inteira e imprime as músicas
    public void exibirPlaylist() {
        //se a playlist estiver vazia apenas informa isso e nada acontece
        if (estaVazia()) {
            System.out.println("Playlist vazia");
            return;
        }

        //ponteiro auxiliar que percorre a partir da primeira música
        NoMusica percorre = ultimaMusica.getProxima();
        
        //laço do-while para que o loop rode pelo menos uma vez antes de checar se deu a volta completa
        do {
            System.out.print(percorre + " // ");//imprime a música atual 
            percorre = percorre.getProxima();//avança para a próxima música
        } while (percorre != ultimaMusica.getProxima());//loop para quando dá a volta completa
        System.out.println();//pula uma linha no console
    }
    
    //MÉTODOS GETTERS E SETTERS
    public NoMusica getUltimaMusica() {
        return ultimaMusica;
    }

    public String getNomePlaylist() {
        return nomePlaylist;
    }

    public Playlist getPlaylistAnterior() {
        return playlistAnterior;
    }
    
    public Playlist getProximaPlaylist() {
        return proximaPlaylist;
    }

    public void setUltimaMusica(NoMusica ultimaMusica) {
        this.ultimaMusica = ultimaMusica;
    }

    public void setProximaPlaylist(Playlist proximaPlaylist) {
        this.proximaPlaylist = proximaPlaylist;
    }

    public void setPlaylistAnterior(Playlist playlistAnterior) {
        this.playlistAnterior = playlistAnterior;
    }
}

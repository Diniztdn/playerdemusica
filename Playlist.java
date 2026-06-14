public class Playlist {
    private String nomeArtista;
    private NoMusica ultimaMusica;
    private Playlist proximaPlaylist, playlistAnterior;

    public Playlist(String nomeArtista) {
        this.nomeArtista = nomeArtista;
        this.ultimaMusica = null;
        this.proximaPlaylist = this.playlistAnterior = null;
    }

    public boolean estaVazia() {
        return ultimaMusica == null;
    }

    public void inserirMusica(String nomeMusica, int minutagem) {
        if (estaVazia()) {
            ultimaMusica = new NoMusica(nomeMusica, minutagem, null, null);
            ultimaMusica.setAnterior(ultimaMusica);
            ultimaMusica.setProxima(ultimaMusica);
            return;
        }
        ultimaMusica = new NoMusica(nomeMusica, minutagem, ultimaMusica, ultimaMusica.getProxima());
        ultimaMusica.getAnterior().setProxima(ultimaMusica);
        ultimaMusica.getProxima().setAnterior(ultimaMusica);
    }

    public void removerMusica(String nomeMusica) {
        if (estaVazia()) {
            System.out.println("Playlist vazia");
            return;
        }
        
        NoMusica encontrado = null;
        NoMusica percorre = ultimaMusica.getProxima();
        
        do {
            if (percorre.getNome().equalsIgnoreCase(nomeMusica)) {
                encontrado = percorre;
                break;
            }
            percorre = percorre.getProxima();
        } while (percorre != ultimaMusica.getProxima());

        if (encontrado != null) {
            if (encontrado == encontrado.getProxima()) {
                ultimaMusica = null;
            } else {
                encontrado.getAnterior().setProxima(encontrado.getProxima());
                encontrado.getProxima().setAnterior(encontrado.getAnterior());
                
                if (encontrado == ultimaMusica) {
                    ultimaMusica = encontrado.getAnterior();
                }
            }
            System.out.println("Música " + nomeMusica + " removida.");
        } else {
            System.out.println("Música não encontrada.");
        }
    }

    public void exibirPlaylist() {
        if (estaVazia()) {
            System.out.println("Playlist vazia");
            return;
        }
        NoMusica percorre = ultimaMusica.getProxima();
        do {
            System.out.print(percorre + " // ");
            percorre = percorre.getProxima();
        } while (percorre != ultimaMusica.getProxima());
        System.out.println();
    }
    
    public NoMusica getUltimaMusica() {
        return ultimaMusica;
    }

    public String getNomeArtista() {
        return nomeArtista;
    }

    public Playlist getPlaylistAnterior() {
        return playlistAnterior;
    }
    
    public Playlist getProximaPlaylist() {
        return proximaPlaylist;
    }

    public void setNomeArtista(String nomeArtista) {
        this.nomeArtista = nomeArtista;
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

public class Playlist {
    private String nomePlaylist;
    private NoMusica ultimaMusica;
    private Playlist proximaPlaylist, playlistAnterior;

    public Playlist(String nomePlaylist) {
        this.nomePlaylist = nomePlaylist;
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

    public void removerNoMusica(NoMusica alvo) {
        if (estaVazia() || alvo == null) return;

        if (alvo == alvo.getProxima()) {
            ultimaMusica = null;
        } 
        else {
            alvo.getAnterior().setProxima(alvo.getProxima());
            alvo.getProxima().setAnterior(alvo.getAnterior());
            
            if (alvo == ultimaMusica) {
                ultimaMusica = alvo.getAnterior();
            }
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

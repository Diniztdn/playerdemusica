public class Player {
    private Playlist headArtista;
    private Playlist ArtistaAtual;
    private NoMusica musicaAtual;

    public Player() {
        this.headArtista = null;
        this.ArtistaAtual = null;
        this.musicaAtual = null;
    }

    public void proximaPlaylist() {
        if (ArtistaAtual != null) {
            ArtistaAtual = ArtistaAtual.getProximaPlaylist();
            musicaAtual = ArtistaAtual.getUltimaMusica().getProxima();
            System.out.println("Playlist de: " + ArtistaAtual.getNomeArtista());
        } else {
            System.out.println("O player está vazio.");
        }
    }

    public void playlistAnterior() {
        if (ArtistaAtual != null) {
            ArtistaAtual = ArtistaAtual.getPlaylistAnterior();
            musicaAtual = ArtistaAtual.getUltimaMusica().getProxima();
            System.out.println("Voltou para a Playlist de: " + ArtistaAtual.getNomeArtista());       
        } else {
            System.out.println("O player está vazio.");
        }
    }

    public void adicionarPlaylist(String nomeArtista) {
        Playlist novoArtista = new Playlist(nomeArtista);

        if (headArtista == null) {
            headArtista = novoArtista;
            novoArtista.setProximaPlaylist(novoArtista);
            novoArtista.setPlaylistAnterior(novoArtista); 
            
            ArtistaAtual = novoArtista;
            musicaAtual = novoArtista.getUltimaMusica().getProxima();
            System.out.println("Playlist de " + nomeArtista + " criada como a primeira!");
            return;
        }
        Playlist ultimo = headArtista.getPlaylistAnterior();

        ultimo.setProximaPlaylist(novoArtista);
        novoArtista.setPlaylistAnterior(ultimo);
        
        novoArtista.setProximaPlaylist(headArtista);
        headArtista.setPlaylistAnterior(novoArtista); 

        System.out.println("Playlist de " + nomeArtista + " adicionada!");
    }

    public void removerPlaylist(String nomeArtista) {
        if (headArtista == null) {
            System.out.println("O player está vazio.");
            return;
        }
        Playlist artistaParaRemover = null;
        Playlist atual = headArtista;
        
        do {
            if (atual.getNomeArtista().equalsIgnoreCase(nomeArtista)) {
                artistaParaRemover = atual;
                break;
            }
            atual = atual.getProximaPlaylist();
        } while (atual != headArtista);

        if (artistaParaRemover == null) {
            System.out.println("Playlist de '" + nomeArtista + "' não encontrada.");
            return;
        }

        if (ArtistaAtual == artistaParaRemover) {
            if (ArtistaAtual.getProximaPlaylist() != ArtistaAtual) {
                ArtistaAtual = ArtistaAtual.getProximaPlaylist();
                musicaAtual = ArtistaAtual.getUltimaMusica().getProxima();
            }
            else {
                ArtistaAtual = null;
                musicaAtual = null;
            }
        }
        
        if (artistaParaRemover.getProximaPlaylist() == artistaParaRemover) {
            headArtista = null;
        } 
        else {
            artistaParaRemover.getPlaylistAnterior().setProximaPlaylist(artistaParaRemover.getProximaPlaylist());
            artistaParaRemover.getProximaPlaylist().setPlaylistAnterior(artistaParaRemover.getPlaylistAnterior());

            if (artistaParaRemover == headArtista) {
                headArtista = artistaParaRemover.getProximaPlaylist();
            }
        }

        System.out.println("Playlist de " + nomeArtista + " removida com sucesso!");
    }
}

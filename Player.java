public class Player {
    private NoArtista headArtista;
    private NoArtista ArtistaAtual;
    private NoMusica musicaAtual;

    public Player() {
        this.headArtista = null;
        this.ArtistaAtual = null;
        this.musicaAtual = null;
    }

    public void proximaPlaylist() {
        if (ArtistaAtual != null) {
            ArtistaAtual = ArtistaAtual.getProxArtista();
            musicaAtual = ArtistaAtual.getPrimeiraMusica();
            System.out.println("Playlist de: " + ArtistaAtual.getNomeArtista());
        } else {
            System.out.println("O player está vazio.");
        }
    }

    public void playlistAnterior() {
        if (ArtistaAtual != null) {
            ArtistaAtual = ArtistaAtual.getAntArtista();
            musicaAtual = ArtistaAtual.getPrimeiraMusica();
            System.out.println("Voltou para a Playlist de: " + ArtistaAtual.getNomeArtista());       
        } else {
            System.out.println("O player está vazio.");
        }
    }

    public void adicionarPlaylist(String nomeArtista) {
        NoArtista novoArtista = new NoArtista(nomeArtista);

        if (headArtista == null) {
            headArtista = novoArtista;
            novoArtista.setProxArtista(novoArtista);
            novoArtista.setAntArtista(novoArtista); 
            
            ArtistaAtual = novoArtista;
            musicaAtual = novoArtista.getPrimeiraMusica();
            System.out.println("Playlist de " + nomeArtista + " criada como a primeira!");
            return;
        }
        NoArtista ultimo = headArtista.getAntArtista();

        ultimo.setProxArtista(novoArtista);
        novoArtista.setAntArtista(ultimo);
        
        novoArtista.setProxArtista(headArtista);
        headArtista.setAntArtista(novoArtista); 

        System.out.println("Playlist de " + nomeArtista + " adicionada!");
    }

    public void removerPlaylist(String nomeArtista) {
        if (headArtista == null) {
            System.out.println("O player está vazio.");
            return;
        }
        NoArtista artistaParaRemover = null;
        NoArtista atual = headArtista;
        
        do {
            if (atual.getNomeArtista().equalsIgnoreCase(nomeArtista)) {
                artistaParaRemover = atual;
                break;
            }
            atual = atual.getProxArtista();
        } while (atual != headArtista);

        if (artistaParaRemover == null) {
            System.out.println("Playlist de '" + nomeArtista + "' não encontrada.");
            return;
        }

        if (ArtistaAtual == artistaParaRemover) {
            if (ArtistaAtual.getProxArtista() != ArtistaAtual) {
                ArtistaAtual = ArtistaAtual.getProxArtista();
                musicaAtual = ArtistaAtual.getPrimeiraMusica();
            }
            else {
                ArtistaAtual = null;
                musicaAtual = null;
            }
        }
        
        if (artistaParaRemover.getProxArtista() == artistaParaRemover) {
            headArtista = null;
        } 
        else {
            artistaParaRemover.getAntArtista().setProxArtista(artistaParaRemover.getProxArtista());
            artistaParaRemover.getProxArtista().setAntArtista(artistaParaRemover.getAntArtista());

            if (artistaParaRemover == headArtista) {
                headArtista = artistaParaRemover.getProxArtista();
            }
        }

        System.out.println("Playlist de " + nomeArtista + " removida com sucesso!");
    }
}

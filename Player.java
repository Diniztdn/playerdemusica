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
            ArtistaAtual = ArtistaAtual.proxArtista;
            musicaAtual = ArtistaAtual.primeiraMusica;
            System.out.println("Playlist de: " + ArtistaAtual.nomeArtista);
        } else {
            System.out.println("O player está vazio.");
        }
    }

    public void playlistAnterior() {
        if (ArtistaAtual != null) {
            ArtistaAtual = ArtistaAtual.antArtista;
            musicaAtual = ArtistaAtual.primeiraMusica;
            System.out.println("Voltou para a Playlist de: " + ArtistaAtual.nomeArtista);       
        } else {
            System.out.println("O player está vazio.");
        }
    }

    public void adicionarPlaylist(String nomeArtista) {
        NoArtista novoArtista = new NoArtista(nomeArtista);

        if (headArtista == null) {
            headArtista = novoArtista;
            novoArtista.proxArtista = novoArtista;
            novoArtista.antArtista = novoArtista; 
            
            ArtistaAtual = novoArtista;
            musicaAtual = novoArtista.primeiraMusica;
            System.out.println("Playlist de " + nomeArtista + " criada como a primeira!");
            return;
        }
        NoArtista ultimo = headArtista.antArtista;

        ultimo.proxArtista = novoArtista;
        novoArtista.antArtista = ultimo;
        
        novoArtista.proxArtista = headArtista;
        headArtista.antArtista = novoArtista; 

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
            if (atual.nomeArtista.equalsIgnoreCase(nomeArtista)) {
                artistaParaRemover = atual;
                break;
            }
            atual = atual.proxArtista;
        } while (atual != headArtista);

        if (artistaParaRemover == null) {
            System.out.println("Playlist de '" + nomeArtista + "' não encontrada.");
            return;
        }

        if (ArtistaAtual == artistaParaRemover) {
            if (ArtistaAtual.proxArtista != ArtistaAtual) {
                ArtistaAtual = ArtistaAtual.proxArtista;
                musicaAtual = ArtistaAtual.primeiraMusica;
            }
            else {
                ArtistaAtual = null;
                musicaAtual = null;
            }
        }
        if (artistaParaRemover.proxArtista == artistaParaRemover) {
            headArtista = null;
        }
        else {

            artistaParaRemover.antArtista.proxArtista = artistaParaRemover.proxArtista;
            artistaParaRemover.proxArtista.antArtista = artistaParaRemover.antArtista;

            if (artistaParaRemover == headArtista) {
                headArtista = artistaParaRemover.proxArtista;
            }
        }

        System.out.println("Playlist de " + nomeArtista + " removida com sucesso!");
    }


}
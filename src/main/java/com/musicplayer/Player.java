public class Player {
    private Playlist headPlaylist;
    private Playlist playlistAtual;
    private NoMusica musicaAtual;

    public Player() {
        this.headPlaylist = null;
        this.playlistAtual = null;
        this.musicaAtual = null;
    }

    public void proximaPlaylist() {
        if (playlistAtual != null) {
            playlistAtual = playlistAtual.getProximaPlaylist();
            musicaAtual = playlistAtual.getUltimaMusica().getProxima();
            System.out.println("Playlist atual: " + playlistAtual.getNomePlaylist());
        } else {
            System.out.println("O player está vazio.");
        }
    }

    public void playlistAnterior() {
        if (playlistAtual != null) {
            playlistAtual = playlistAtual.getPlaylistAnterior();
            musicaAtual = playlistAtual.getUltimaMusica().getProxima();
            System.out.println("Voltou para a Playlist: " + playlistAtual.getNomePlaylist());       
        } else {
            System.out.println("O player está vazio.");
        }
    }

    public void proximaMusica() {
        if (musicaAtual != null) {
            musicaAtual = musicaAtual.getProxima();
            System.out.println("Tocando agora: " + musicaAtual.getNome());
        } else {
            System.out.println("Nenhuma música na playlist para avançar.");
        }
    }

    public void musicaAnterior() {
        if (musicaAtual != null) {
            musicaAtual = musicaAtual.getAnterior();
            System.out.println("Tocando agora: " + musicaAtual.getNome());
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
            musicaAtual = novaPlaylist.getUltimaMusica().getProxima();
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
        if (ArtistaAtual == null) {
            System.out.println("O player está vazio.");
            return;
        }

        System.out.println("Removendo a playlist: " + ArtistaAtual.getNomeArtista());

        if (ArtistaAtual.getProximaPlaylist() == ArtistaAtual) {
            headArtista = null;
            ArtistaAtual = null;
            musicaAtual = null;
        } 
        else {
            Playlist anterior = ArtistaAtual.getPlaylistAnterior();
            Playlist proxima = ArtistaAtual.getProximaPlaylist();

            anterior.setProximaPlaylist(proxima);
            proxima.setPlaylistAnterior(anterior);

            if (ArtistaAtual == headArtista) {
                headArtista = proxima;
            }
            ArtistaAtual = proxima;
            
            if (!ArtistaAtual.estaVazia()) {
                musicaAtual = ArtistaAtual.getUltimaMusica().getProxima();
            } 
            else {
                musicaAtual = null;
            }
        }
    }
    
    public void removerMusicaAtual() {
    if (ArtistaAtual == null || musicaAtual == null) {
        System.out.println("Nenhuma música tocando no momento.");
        return;
    }

    System.out.println("Removendo a música: " + musicaAtual.getNome());
    NoMusica proximaMusica = musicaAtual.getProxima();

    if (musicaAtual == proximaMusica) {
        ArtistaAtual.removerNoMusica(musicaAtual);
        musicaAtual = null;
    } else {
        ArtistaAtual.removerNoMusica(musicaAtual);
        musicaAtual = proximaMusica;
    }
}
}

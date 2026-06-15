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

    public void removerPlaylist(String nomePlaylist) {
        if (headPlaylist == null) {
            System.out.println("O player está vazio.");
            return;
        }
        Playlist playlistParaRemover = null;
        Playlist atual = headPlaylist;
        
        do {
            if (atual.getNomePlaylist().equalsIgnoreCase(nomePlaylist)) {
                playlistParaRemover = atual;
                break;
            }
            atual = atual.getProximaPlaylist();
        } while (atual != headPlaylist);

        if (playlistParaRemover == null) {
            System.out.println("Playlist de '" + nomePlaylist + "' não encontrada.");
            return;
        }

        if (playlistAtual == playlistParaRemover) {
            if (playlistAtual.getProximaPlaylist() != playlistAtual) {
                playlistAtual = playlistAtual.getProximaPlaylist();
                musicaAtual = playlistAtual.getUltimaMusica().getProxima();
            }
            else {
                playlistAtual = null;
                musicaAtual = null;
            }
        }
        
        if (playlistParaRemover.getProximaPlaylist() == playlistParaRemover) {
            headPlaylist = null;
        } 
        else {
            playlistParaRemover.getPlaylistAnterior().setProximaPlaylist(playlistParaRemover.getProximaPlaylist());
            playlistParaRemover.getProximaPlaylist().setPlaylistAnterior(playlistParaRemover.getPlaylistAnterior());

            if (playlistParaRemover == headPlaylist) {
                headPlaylist = playlistParaRemover.getProximaPlaylist();
            }
        }

        System.out.println("Playlist de " + nomePlaylist + " removida com sucesso!");
    }
}

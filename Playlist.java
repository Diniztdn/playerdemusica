public class Playlist {
    private String nomeArtista;
    private NoMusica ultimaMusica;

    public Playlist(String nomeArtista) {
        this.nomeArtista = nomeArtista;
        this.ultimaMusica = null;
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
            if (percorre.getNome() == nomeMusica) {
                encontrado = percorre;
                break;
            }
        } while (percorre != ultimaMusica.getProxima());
        if (ultimaMusica == ultimaMusica.getProxima() && encontrado != null) {
            ultimaMusica = null;
            return;
        }
        if (encontrado == ultimaMusica) {
            ultimaMusica = ultimaMusica.getAnterior();
            ultimaMusica.setAnterior(ultimaMusica.getAnterior().getAnterior());
            ultimaMusica.setProxima(ultimaMusica.getProxima().getProxima());
            return;    
        }
        if (encontrado != null) {
            encontrado.getAnterior().setProxima(encontrado.getProxima());
            encontrado.getProxima().setAnterior(encontrado.getAnterior());
            return;
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
    
}

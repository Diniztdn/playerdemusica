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
}

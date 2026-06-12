class NodeArtista {
    String nomeArtista;
    NodeMusica primeiraMusica;   
    NodeArtista proxArtista;
    NodeArtista antArtista;

    public NodeArtista(String nomeArtista) {
        this.nomeArtista = nomeArtista;
        this.primeiraMusica = null;
        this.proxArtista = null;
        this.antArtista = null;
    }
}

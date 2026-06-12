class NodeArtista {
    String nomeArtista;
    NodeMusica primeiraMusica;
    
    NodeCantor proxCantor;
    NodeCantor antCantor;

    public NodeArtista(String nomeArtista) {
        this.nomeArtista = nomeArtista;
        this.primeiraMusica = null;
        this.proxArtista = null;
        this.antArtista = null;
    }
}

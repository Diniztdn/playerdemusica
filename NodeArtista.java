class NodeArtista {
    String nomeArtista;
    NoMusica primeiraMusica;
    
    NodeCantor proxCantor;
    NodeCantor antCantor;

    public NodeArtista(String nomeArtista) {
        this.nomeArtista = nomeArtista;
        this.primeiraMusica = null;
        this.proxArtista = null;
        this.antArtista = null;
    }
}

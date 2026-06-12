class NoArtista {
    String nomeArtista;
    NoMusica primeiraMusica;   
    NoArtista proxArtista;
    NoArtista antArtista;

    public NoArtista(String nomeArtista) {
        this.nomeArtista = nomeArtista;
        this.primeiraMusica = null;
        this.proxArtista = null;
        this.antArtista = null;
    }

    public String getNomeArtista() {
        return nomeArtista;
    }

    public NoMusica getPrimeiraMusica() {
        return primeiraMusica;
    }

    public NoArtista getProxArtista() {
        return proxArtista;
    }

    public NoArtista getAntArtista() {
        return antArtista;
    }

    public void setNomeArtista(String nomeArtista) {
        this.nomeArtista = nomeArtista;
    }

    public void setPrimeiraMusica(NoMusica primeiraMusica) {
        this.primeiraMusica = primeiraMusica;
    }

    public void setProxArtista(NoArtista proxArtista) {
        this.proxArtista = proxArtista;
    }

    public void setAntArtista(NoArtista antArtista) {
        this.antArtista = antArtista;
    } 
}

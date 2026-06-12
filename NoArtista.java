public class NoArtista {
    private String nomeArtista;
    private NoMusica primeiraMusica;
    private NoMusica ultimaTocada;
    private NoArtista proxArtista;
    private NoArtista antArtista;

    public NoArtista(String nomeArtista, NoArtista proxArtista, NoArtista antArtista) {
        this.nomeArtista = nomeArtista;
        this.primeiraMusica = this.ultimaTocada = null;
        this.proxArtista = proxArtista;
        this.antArtista = antArtista;
    }

    public NoArtista(String nomeArtista) {
        this(nomeArtista, null, null);
    }
    
    public String getNomeArtista() {
        return nomeArtista;
    }

    public NoMusica getPrimeiraMusica() {
        return primeiraMusica;
    }

    public NoMusica getUltimaTocada() {
        return ultimaTocada;
    }

    public NoArtista getProxArtista() {
        return proxArtista;
    }

    public NoArtista getAntArtista() {
        return antArtista;
    }

    public void setPrimeiraMusica(NoMusica primeiraMusica) {
        this.primeiraMusica = primeiraMusica;
    }

    public void setUltimaTocada(NoMusica ultimaTocada) {
        this.ultimaTocada = ultimaTocada;
    }

    public void setProxArtista(NoArtista proxArtista) {
        this.proxArtista = proxArtista;
    }
    
    public void setAntArtista(NoArtista antArtista) {
        this.antArtista = antArtista;
    } 
}

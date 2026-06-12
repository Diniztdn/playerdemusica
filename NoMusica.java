public class NoMusica {
    private String nome;
    private int minutagem;
    private NoMusica anterior;
    private NoMusica proxima;

    public NoMusica(String nome, int minutagem, NoMusica anterior, NoMusica proxima){
        this.nome = nome;
        this.minutagem = minutagem;
        this.anterior = anterior;
        this.proxima = proxima;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getMinutagem() {
        return minutagem;
    }

    public void setMinutagem(int minutagem) {
        this.minutagem = minutagem;
    }

    public NoMusica getAnterior() {
        return anterior;
    }

    public void setAnterior(NoMusica anterior) {
        this.anterior = anterior;
    }

    public NoMusica getProxima() {
        return proxima;
    }

    public void setProxima(NoMusica proxima) {
        this.proxima = proxima;
    }
}

package com.musicplayer;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.util.Duration;
import java.util.Optional;

public class Controller{
    private Player player = new Player();
    /* Variáveis que serão usadas na regulação da barra de progressão. */
    private Timeline medidorTempo;
    private double tempoTotal, tempoAtual;
    /* Variável que será utilizada para determinar o estado de pausa. */
    private boolean pausado = false;

    /* Declarando os botões, labels e progressão com os mesmos ids dos componentes do Scene Builder. */
    @FXML
    private Button addplaylist, addmusic, deletemusic, deleteplay, proxplay, antplay, proxmusic, antmusic, parar;

    @FXML
    private Label nomeMusica, nomeArtista, nomePlaylist;

    @FXML
    private ProgressBar progressao;

    /* Método que realiza a determinação da velocidade de passagem da barra de progressão
    de acordo com a minutagem da música. */
    public void progressaoMusica(double minutagem){
        this.pausado = false;
        /* Mudando o símbolo do botão de pare/continue caso a música esteja pausada. */
        if (parar != null){
            parar.setText("⏸");
        }
        this.tempoAtual = 0.0;
        /* Convertendo a minutagem para segundos. */
        this.tempoTotal = minutagem * 60;
        /* Inicializando a barra de progresso para 0. */
        this.progressao.setProgress(0.0);
        if (medidorTempo != null){
            medidorTempo.stop();
        }
        /*Atualizando a progressão da barra a cada segundo. */
        medidorTempo = new Timeline(new KeyFrame(Duration.seconds(0.01), event ->{
            if (!pausado){
                if (tempoAtual < tempoTotal){
                    tempoAtual += 0.01;
                    /* Como o progresso é uma porcentagem, ele foi determinado com base na razão
                    entre o tempo atual e o tempo total da música em segundos. */
                    double progresso = tempoAtual / tempoTotal;
                    progressao.setProgress(progresso);
                    /* Quando a música chega ao final a contagem para e ocorre a passagem para a 
                    próxima música da playlist (Se for a única música da playlist, a barra apenas
                    reinicia). */
                } else if(tempoAtual >= tempoTotal){
                    medidorTempo.stop();
                    System.out.println("Música encerrada.");
                    player.proximaMusica();
                }
            }
        }));

        medidorTempo.setCycleCount(Timeline.INDEFINITE);
        medidorTempo.play();
    }

    @FXML 
    public void initialize(){
        /* Começando a tela com a barra de progressão em 0. */
        progressao.setProgress(0.0);

        /* Por meio de um listener que vai detectar as mudanças de música e playlist,
        os labels que exibem os nomes da música, artista e playlist são atualizados a cada
        detecção. */
        player.musicaAtualProperty().addListener((observable, valorAntigo, valorNovo) ->{
            if (valorNovo != null){
                nomeMusica.setText(valorNovo.getNome());
                nomeArtista.setText(valorNovo.getNomeArtista());
                if (player.getPlaylistAtual() != null){
                    nomePlaylist.setText(player.getPlaylistAtual().getNomePlaylist());
                } else {
                    nomePlaylist.setText("...");
                }
                progressao.setProgress(0.0);
                progressaoMusica(valorNovo.getMinutagem());
            } else {
                /* Se não houver música no player, esses simbolos serão exibidos: */
                nomeMusica.setText("???");
                nomeArtista.setText("...");
                nomePlaylist.setText("...");
                progressaoMusica(0.0);
            }
        });

        /* Inicializando o player com uma playlist já pré-definida. */
        player.adicionarPlaylist("Favoritas");
        player.inserirMusicaAtual("White Ferrari", "Frank Ocean", 4.08);
        player.inserirMusicaAtual("Karma Police", "Radiohead", 3.48);
        player.inserirMusicaAtual("Insista Em Mim", "Ana Frango Elétrico", 3.12);
        player.inserirMusicaAtual("cellophane", "FKA twigs", 2.42);

        if (player.getMusicaAtual() != null){
            nomeMusica.setText(player.getMusicaAtual().getNome());
            nomeArtista.setText(player.getMusicaAtual().getNomeArtista());
            nomePlaylist.setText(player.getPlaylistAtual().getNomePlaylist());
        }
    }

    /* Método que realiza uma ponte entre o método "proximaPlaylist" da classe player e
    a interface gráfica. */
    @FXML
    public void proximaPlaylist(){
        player.proximaPlaylist();
    }

    /* Método associado ao botão de parar/continuar que muda o estado de "pausado". */
    @FXML
    public void pausarOuRetomar(){
        if (medidorTempo == null) {
            return;
        }

        pausado = !pausado;
        if (pausado) {
            medidorTempo.pause();
            if (parar != null) {
                /* Mudando o símbolo exibido no botão (*). */
                parar.setText("▶");
            }
        } else {
            medidorTempo.play();
            if (parar != null) {
                /* (*). */
                parar.setText("⏸");
            }
        }
    }

    /* Realizando a ponte entre os métodos "playlistAnterior", "proximaMusica" e
    "musicaAnterior" da classe player e a interface gráfica.*/
    @FXML
    public void playlistAnterior(){
        player.playlistAnterior();
    }

    @FXML
    public void proximaMusica(){
        player.proximaMusica();
    }

    @FXML
    public void musicaAnterior(){
        player.musicaAnterior();
    }

    /* Método para criar e adicionar uma música na playlist. */
    @FXML
    public void abrirPopupAdicionarMusica(){
        /* Só é possível criar uma música se já houver que possa a receber. */
        if (player.getPlaylistAtual() == null) {
            System.out.println("Error: Create a playlist before adding songs.");
            return;
        }
        /* Configurando a janela de pop-up que abrirá um diálogo com o usuário para receber
        as informações da música que será adicionada. */
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("New Song");
        dialog.setHeaderText("Insert the details of the song:");
        ButtonType botaoConfirmarType = new ButtonType("Insert", ButtonData.OK_DONE);
        ButtonType botaoCancelarType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(botaoConfirmarType, botaoCancelarType);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);
        grid.setPadding(new Insets(20, 20, 20, 20));
        /* Explicitando a entrada requerida e fornecendo exemplos de entradas para guiar o usuário. */
        TextField txtNome = new TextField();
        txtNome.setPromptText("Ex: White Ferrari");
        TextField txtArtista = new TextField();
        txtArtista.setPromptText("Ex: Frank Ocean");
        TextField txtMinutagem = new TextField();
        txtMinutagem.setPromptText("Ex: 4.08");
        grid.add(new Label("Name:"), 0, 0);
        grid.add(txtNome, 1, 0);
        grid.add(new Label("Artist:"), 0, 1);
        grid.add(txtArtista, 1, 1);
        grid.add(new Label("Duration (minutes):"), 0, 2);
        grid.add(txtMinutagem, 1, 2);
        dialog.getDialogPane().setContent(grid);
        /* Decorando o pop-up de acordo com a paleta de cores do player.*/
        dialog.getDialogPane().setStyle("-fx-background-color: #1E293B;");
        grid.getChildren().forEach(node ->{
            if (node instanceof Label) {
                node.setStyle("-fx-text-fill: #94A3B8; -fx-font-weight: bold;");
            } else if (node instanceof TextField) {
                node.setStyle("-fx-background-color: #0F172A; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 5;");
            }
        });
        /* Recebendo e tratando as entradas do usuário. */
        Optional<ButtonType> resultado = dialog.showAndWait();
        if (resultado.isPresent() && resultado.get() == botaoConfirmarType){
            String nome = txtNome.getText().trim();
            String artista = txtArtista.getText().trim();
            String minutagemTexto = txtMinutagem.getText().trim();
            if (!nome.isEmpty() && !artista.isEmpty() && !minutagemTexto.isEmpty()){
                try{
                    double minutagem = Double.parseDouble(minutagemTexto);
                    player.inserirMusicaAtual(nome, artista, minutagem);
                    System.out.println("Song added succesfully!");
                } catch (NumberFormatException e) {
                    System.out.println("Error: Insert a valid value for the duration.");
                }
            }
        }
    }

    /* Utilizando a mesma lógica de adicionar música, foi desenvolvido um método de adicionar
    playlist. */
    @FXML
    public void abrirPopupAdicionarPlaylist(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("New Playlist");
        dialog.setHeaderText("Insert the details of the playlist:");
        ButtonType botaoConfirmarType = new ButtonType("Insert", ButtonData.OK_DONE);
        ButtonType botaoCancelarType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(botaoConfirmarType, botaoCancelarType);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);
        grid.setPadding(new Insets(20, 20, 20, 20));

        TextField txtNome = new TextField();
        txtNome.setPromptText("Ex: Saturday Nights");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(txtNome, 1, 0);

        dialog.getDialogPane().setContent(grid);

        dialog.getDialogPane().setStyle("-fx-background-color: #1E293B;");
        grid.getChildren().forEach(node ->{
            if (node instanceof Label) {
                node.setStyle("-fx-text-fill: #94A3B8; -fx-font-weight: bold;");
            } else if (node instanceof TextField) {
                node.setStyle("-fx-background-color: #0F172A; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 5;");
            }
        });

        Optional<ButtonType> resultado = dialog.showAndWait();

        if (resultado.isPresent() && resultado.get() == botaoConfirmarType){
            String nome = txtNome.getText().trim();
            if (!nome.isEmpty()){
                player.adicionarPlaylist(nome);
                System.out.println("Playlist criada com sucesso!");
            }
        }
    }

    /* Novamente, realizando uma ponte entre os métodos do player e a interface gráfica. */
    @FXML
    public void removerPlaylistatual(){
        player.removerPlaylist();
    }

    @FXML
    public void removerMusicaAtual(){
        player.removerMusicaAtual();
    }
}

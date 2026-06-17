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
    private Timeline medidorTempo;
    private double tempoTotal, tempoAtual;
    private boolean pausado = false;

    @FXML
    private Button addplaylist, addmusic, deletemusic, deleteplay, proxplay, antplay, proxmusic, antmusic, parar;

    @FXML
    private Label nomeMusica, nomeArtista, nomePlaylist;

    @FXML
    private ProgressBar progressao;

    public void progressaoMusica(double minutagem){
        this.pausado = false;
        if (parar != null){
            parar.setText("⏸");
        }
        this.tempoAtual = 0.0;
        this.tempoTotal = minutagem * 60;
        this.progressao.setProgress(0.0);
        if (medidorTempo != null){
            medidorTempo.stop();
        }
        medidorTempo = new Timeline(new KeyFrame(Duration.seconds(0.01), event ->{
            if (!pausado){
                if (tempoAtual < tempoTotal){
                    tempoAtual += 0.01;
                    double progresso = tempoAtual / tempoTotal;
                    progressao.setProgress(progresso);
                } else if(tempoAtual == tempoTotal){
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
        progressao.setProgress(0.0);
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
                nomeMusica.setText("???");
                nomeArtista.setText("...");
                nomePlaylist.setText("...");
                progressaoMusica(0.0);
            }
        });
    }

    @FXML
    public void proximaPlaylist(){
        player.proximaPlaylist();
    }

    @FXML
    public void pausarOuRetomar(){
        if (medidorTempo == null) {
            return;
        }

        pausado = !pausado;
        if (pausado) {
            medidorTempo.pause();
            if (parar != null) {
                parar.setText("▶");
            }
        } else {
            medidorTempo.play();
            if (parar != null) {
                parar.setText("⏸");
            }
        }
    }

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

    @FXML
    public void abrirPopupAdicionarMusica(){
        if (player.getPlaylistAtual() == null) {
            System.out.println("Erro: Crie uma playlist antes de adicionar músicas.");
            return;
        }
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Nova Música");
        dialog.setHeaderText("Insira os detalhes para adicionar à playlist:");
        ButtonType botaoConfirmarType = new ButtonType("Inserir", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(botaoConfirmarType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);
        grid.setPadding(new Insets(20, 20, 20, 20));
        TextField txtNome = new TextField();
        txtNome.setPromptText("Ex: White Ferrari");
        TextField txtArtista = new TextField();
        txtArtista.setPromptText("Ex: Frank Ocean");
        TextField txtMinutagem = new TextField();
        txtMinutagem.setPromptText("Ex: 4.08");
        grid.add(new Label("Nome da Música:"), 0, 0);
        grid.add(txtNome, 1, 0);
        grid.add(new Label("Artista:"), 0, 1);
        grid.add(txtArtista, 1, 1);
        grid.add(new Label("Duração (minutos):"), 0, 2);
        grid.add(txtMinutagem, 1, 2);
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
            String artista = txtArtista.getText().trim();
            String minutagemTexto = txtMinutagem.getText().trim();
            if (!nome.isEmpty() && !artista.isEmpty() && !minutagemTexto.isEmpty()){
                try{
                    double minutagem = Double.parseDouble(minutagemTexto);
                    player.inserirMusicaAtual(nome, artista, minutagem);
                    System.out.println("Música adicionada com sucesso!");
                } catch (NumberFormatException e) {
                    System.out.println("Erro: Digite um número válido para a minutagem.");
                }
            }
        }
    }

    @FXML
    public void abrirPopupAdicionarPlaylist(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Nova Música");
        dialog.setHeaderText("Insira os detalhes para adicionar à playlist:");

        ButtonType botaoConfirmarType = new ButtonType("Inserir", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(botaoConfirmarType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);
        grid.setPadding(new Insets(20, 20, 20, 20));

        TextField txtNome = new TextField();
        txtNome.setPromptText("Ex: Noite de Sábado");

        grid.add(new Label("Nome da Playlist:"), 0, 0);
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

    @FXML
    public void removerPlaylistatual(){
        player.removerPlaylist();
    }

    @FXML
    public void removerMusicaAtual(){
        player.removerMusicaAtual();
    }

}

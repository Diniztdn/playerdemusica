package com.musicplayer;

import java.io.IOException;
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
import java.util.Optional;

public class PrimaryController {
    private Player player = new Player();
    @FXML
    private Button addplaylist, addmusic, deletemusic, deleteplay, proxplay, antplay, proxmusic, antmusic, nomePlaylist;

    @FXML
    private Label nomeMusica, nomeArtista;

    @FXML
    private ProgressBar progressao;

    @FXML 
    public void initialize(){
        player.musicaAtualProperty().addListener((observable, valorAntigo, valorNovo) -> {
            if (player.getMusicaAtual() != null) {
                nomeMusica.setText(player.getMusicaAtual().getNome());
                nomeArtista.setText(player.getMusicaAtual().getNomeArtista());
                nomePlaylist.setText(player.getPlaylistAtual().getNomePlaylist());
            } else {
                nomeMusica.setText("Nenhuma música está sendo tocada.");
                nomeArtista.setText("...");
                nomePlaylist.setText("...");
            }
        });
    }

    @FXML
    public void proximaPlaylist(){
        player.proximaPlaylist();
    }

    @FXML
    public void playlistAnterior(){
        player.playlistAnterior();
    }

    @FXML
    public void abrirPopupAdicionarMusica() {
        // 1. Cria o Dialog principal
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Nova Música");
        dialog.setHeaderText("Insira os detalhes para adicionar à playlist:");

        // 2. Cria os botões de Confirmar e Cancelar
        ButtonType botaoConfirmarType = new ButtonType("Inserir", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(botaoConfirmarType, ButtonType.CANCEL);

        // 3. Monta o layout de grade (GridPane) para alinhar os campos
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);
        grid.setPadding(new Insets(20, 20, 20, 20));

        // 4. Cria os campos de entrada (Fields)
        TextField txtNome = new TextField();
        txtNome.setPromptText("Ex: Midnight City");
        
        TextField txtArtista = new TextField();
        txtArtista.setPromptText("Ex: M83");
        
        TextField txtMinutagem = new TextField();
        txtMinutagem.setPromptText("Ex: 4.03"); // Usar ponto para decimais

        // 5. Organiza as Labels e as Caixas de Texto na grade
        grid.add(new Label("Nome da Música:"), 0, 0);
        grid.add(txtNome, 1, 0);
        grid.add(new Label("Artista:"), 0, 1);
        grid.add(txtArtista, 1, 1);
        grid.add(new Label("Duração (minutos):"), 0, 2);
        grid.add(txtMinutagem, 1, 2);

        // 6. Coloca a grade dentro do popup
        dialog.getDialogPane().setContent(grid);

        // Estilização rápida para combinar com o seu tema escuro
        dialog.getDialogPane().setStyle("-fx-background-color: #1E293B;");
        grid.getChildren().forEach(node -> {
            if (node instanceof Label) {
                node.setStyle("-fx-text-fill: #94A3B8; -fx-font-weight: bold;");
            } else if (node instanceof TextField) {
                node.setStyle("-fx-background-color: #0F172A; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 5;");
            }
        });

        // 7. Abre a janelinha e espera a resposta
        Optional<ButtonType> resultado = dialog.showAndWait();

        // 8. Se clicou em Inserir, processa e envia para a sua lógica
        if (resultado.isPresent() && resultado.get() == botaoConfirmarType) {
            String nome = txtNome.getText().trim();
            String artista = txtArtista.getText().trim();
            String minutagemTexto = txtMinutagem.getText().trim();

            // Validação básica para não enviar campos vazios
            if (!nome.isEmpty() && !artista.isEmpty() && !minutagemTexto.isEmpty()) {
                try {
                    // Converte o texto da minutagem para double
                    double minutagem = Double.parseDouble(minutagemTexto);
                    
                    // CHAMA O SEU MÉTODO DE LÓGICA PASSA OS 3 PARAMETROS
                    player.getPlaylistAtual().inserirMusica(nome, artista, minutagem);
                    
                    System.out.println("Música adicionada com sucesso!");
                    
                } catch (NumberFormatException e) {
                    // Entra aqui se o usuário digitar letras no campo de duração
                    System.out.println("Erro: Digite um número válido para a minutagem (Ex: 4.44).");
                }
            }
        }
    }

    public void abrirPopupAdicionarPlaylist() {
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
        grid.getChildren().forEach(node -> {
            if (node instanceof Label) {
                node.setStyle("-fx-text-fill: #94A3B8; -fx-font-weight: bold;");
            } else if (node instanceof TextField) {
                node.setStyle("-fx-background-color: #0F172A; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 5;");
            }
        });

        Optional<ButtonType> resultado = dialog.showAndWait();

        if (resultado.isPresent() && resultado.get() == botaoConfirmarType) {
            String nome = txtNome.getText().trim();
            if (!nome.isEmpty()){
                player.adicionarPlaylist(nome);
                System.out.println("Playlist criada com sucesso!");
            }
        }
    }

    @FXML
    public void removerPlaylistatual(){
        player.removerPlaylist(player.getPlaylistAtual().getNomePlaylist());
    }

    @FXML
    public void removerMusicaAtual(){
        player.getPlaylistAtual().removerMusica(player.getMusicaAtual().getNome());
    }
}

package org.exemple.biblioteca.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;

public class MenusController {
    @FXML
    private MenuItem menuUsuarioInclui;
    @FXML
    private MenuItem menuLivroInclui;
    @FXML
    private MenuItem menuEmprestimoInclui;

    @FXML
    private ImageView imageView;

    @FXML
    public void initialize() {
        // Carregar a imagem
        Image image = new Image(getClass().getResourceAsStream("/org/exemple/biblioteca/view/image/biblioteca.jpg"));
        if (image != null) {
            imageView.setImage(image);
        } else {
            System.out.println("Imagem não encontrada!");
        }
    }

    @FXML
    void incluiUsuarioOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/exemple/biblioteca/view/IncluiUsuario.fxml"));
            Parent root = loader.load();
            Stage newStage = new Stage();
            Scene newScene = new Scene(root);
            newStage.setScene(newScene);
            newStage.setTitle("Incluir Usuário");
            newStage.setResizable(false);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void incluiLivroOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/exemple/biblioteca/view/IncluiLivro.fxml"));
            Parent root = loader.load();
            Stage newStage = new Stage();
            Scene newScene = new Scene(root);
            newStage.setScene(newScene);
            newStage.setTitle("Incluir Livro");
            newStage.setResizable(false);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void incluiEmprestimoOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/exemple/biblioteca/view/IncluiEmprestimo.fxml"));
            Parent root = loader.load();
            Stage newStage = new Stage();
            Scene newScene = new Scene(root);
            newStage.setScene(newScene);
            newStage.setTitle("Incluir Empréstimo");
            newStage.setResizable(false);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
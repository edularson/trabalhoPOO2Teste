package org.exemple.biblioteca.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.exemple.biblioteca.dao.LivroDAO;
import org.exemple.biblioteca.model.Livro;

import java.sql.SQLException;
import java.util.List;

public class IncluiLivroController {
    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtAutor;
    @FXML
    private Button btnConf;
    @FXML
    private TableView<Livro> tabelaLivros;
    @FXML
    private TableColumn<Livro, String> colunaTitulo;
    @FXML
    private TableColumn<Livro, String> colunaAutor;

    private LivroDAO livroDAO;
    private ObservableList<Livro> listaLivros;

    public IncluiLivroController() {
        livroDAO = new LivroDAO();
        listaLivros = FXCollections.observableArrayList();
    }

    @FXML
    void initialize() {
        colunaTitulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        colunaAutor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutor()));

        try {
            listaLivros.addAll(livroDAO.buscarTodos());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tabelaLivros.setItems(listaLivros);
    }

    @FXML
    void btnConfOnAction(ActionEvent event) {
        Livro livro = new Livro();
        livro.setTitulo(txtTitulo.getText());
        livro.setAutor(txtAutor.getText());

        try {
            livroDAO.inserir(livro);
            listaLivros.add(livro);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Livro cadastrado com sucesso!", ButtonType.OK);
            alert.setTitle("Cadastro de Livro");
            alert.setHeaderText("Informação");
            alert.show();
            txtTitulo.clear();
            txtAutor.clear();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao cadastrar livro!", ButtonType.OK);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro");
            alert.show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnVoltarOnAction() {
        Stage stage = (Stage) btnConf.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnExcluirOnAction(ActionEvent event) {
        Livro livroSelecionado = tabelaLivros.getSelectionModel().getSelectedItem();

        if (livroSelecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja excluir o livro " + livroSelecionado.getTitulo() + "?", ButtonType.YES , ButtonType.NO);
            alert.setTitle("Confirmação de Exclusão");
            alert.setHeaderText("Atenção!");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        livroDAO.deletar(livroSelecionado.getLivroID());
                        listaLivros.remove(livroSelecionado);
                        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION, "Livro excluído com sucesso!", ButtonType.OK);
                        infoAlert.setTitle("Exclusão de Livro");
                        infoAlert.setHeaderText("Informação");
                        infoAlert.show();
                    } catch (SQLException e) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Erro ao excluir livro!", ButtonType.OK);
                        errorAlert.setTitle("Erro");
                        errorAlert.setHeaderText("Erro");
                        errorAlert.show();
                        e.printStackTrace();
                    }
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecione um livro para excluir.", ButtonType.OK);
            alert.setTitle("Aviso");
            alert.setHeaderText("Nenhum livro selecionado");
            alert.show();
        }
    }
}
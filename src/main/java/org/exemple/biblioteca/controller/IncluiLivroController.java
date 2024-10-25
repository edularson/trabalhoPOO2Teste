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
import org.exemple.biblioteca.model.Livro;
import org.exemple.biblioteca.dao.LivroDAO;

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
    private TableView<Livro> tabelaLivros; // Adicione a TableView no seu FXML
    @FXML
    private TableColumn<Livro, String> colunaTitulo; // Coluna para o título
    @FXML
    private TableColumn<Livro, String> colunaAutor; // Coluna para o autor

    private LivroDAO livroDAO;
    private ObservableList<Livro> listaLivros;

    public IncluiLivroController() {
        livroDAO = new LivroDAO(); // Inicialize o DAO
        listaLivros = FXCollections.observableArrayList(); // Inicialize a lista
    }

    @FXML
    void initialize() {
        // Configuração das colunas
        colunaTitulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        colunaAutor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutor()));

        carregarLivros(); // Carrega os livros ao iniciar
        tabelaLivros.setItems(listaLivros); // Define a lista de livros na tabela
    }

    @FXML
    void btnConfOnAction(ActionEvent event) {
        Livro livro = new Livro();
        livro.setTitulo(txtTitulo.getText());
        livro.setAutor(txtAutor.getText());

        try {
            livroDAO.inserir(livro);
            listaLivros.add(livro); // Adiciona o livro à lista
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Livro cadastrado com sucesso!", ButtonType.OK);
            alert.setTitle("Cadastro de Livro");
            alert.setHeaderText("Informação");
            alert.show();
            txtTitulo.clear(); // Limpa o campo de título
            txtAutor.clear(); // Limpa o campo de autor
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao cadastrar livro!", ButtonType.OK);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro");
            alert.show();
            e.printStackTrace();
        }
    }

    private void carregarLivros() {
        try {
            listaLivros.clear(); // Limpa a lista antes de carregar
            List<Livro> livros = livroDAO.buscarTodos(); // Busca todos os livros
            listaLivros.addAll(livros); // Adiciona todos os livros à lista
        } catch (SQLException e) {
            e.printStackTrace(); // Trate o erro adequadamente
        }
    }

    @FXML
    void btnVoltarOnAction() {
        // Fechar a janela atual ou retornar à tela anterior
        Stage stage = (Stage) btnConf.getScene().getWindow();
        stage.close(); // ou você pode carregar a tela anterior
    }
}
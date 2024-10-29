package org.exemple.biblioteca.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.exemple.biblioteca.dao.LivroDAO;
import org.exemple.biblioteca.dao.UsuarioDAO;
import org.exemple.biblioteca.model.Livro;
import org.exemple.biblioteca.model.Usuario;

import java.sql.SQLException;
import java.util.List;

public class SearchController {
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Livro> tabelaLivros;
    @FXML
    private TableView<Usuario> tabelaUsuarios;
    @FXML
    private TableColumn<Livro, String> colunaTitulo;
    @FXML
    private TableColumn<Livro, String> colunaAutor;
    @FXML
    private TableColumn<Usuario, String> colunaNome;

    private LivroDAO livroDAO;
    private UsuarioDAO usuarioDAO;
    private ObservableList<Livro> listaLivros;
    private ObservableList<Usuario> listaUsuarios;

    public SearchController() {
        livroDAO = new LivroDAO();
        usuarioDAO = new UsuarioDAO();
        listaLivros = FXCollections.observableArrayList();
        listaUsuarios = FXCollections.observableArrayList();
    }

    @FXML
    void initialize() {
        colunaTitulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        colunaAutor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutor()));
        colunaNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));

        try {
            listaLivros.addAll(livroDAO.buscarTodos());
            listaUsuarios.addAll(usuarioDAO.buscarTodos());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tabelaLivros.setItems(listaLivros);
        tabelaUsuarios.setItems(listaUsuarios);
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String searchTerm = searchField.getText();
        try {
            listaLivros.clear();
            listaUsuarios.clear();
            if (!searchTerm.isEmpty()) {
                List<Livro> livros = livroDAO.searchByTitleOrAuthor(searchTerm);
                List<Usuario> usuarios = usuarioDAO.searchByName(searchTerm);
                listaLivros.addAll(livros);
                listaUsuarios.addAll(usuarios);
            } else {
                listaLivros.addAll(livroDAO.buscarTodos());
                listaUsuarios.addAll(usuarioDAO.buscarTodos());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabelaLivros.setItems(listaLivros);
        tabelaUsuarios.setItems(listaUsuarios);
    }
}
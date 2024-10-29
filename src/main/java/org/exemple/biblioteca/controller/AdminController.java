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

public class AdminController {
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
    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtAutor;
    @FXML
    private TextField txtNomeUsuario;

    private LivroDAO livroDAO;
    private UsuarioDAO usuarioDAO;
    private ObservableList<Livro> listaLivros;
    private ObservableList<Usuario> listaUsuarios;

    public AdminController() {
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
    void btnAddLivroOnAction(ActionEvent event) {
        Livro livro = new Livro();
        livro.setTitulo(txtTitulo.getText());
        livro.setAutor(txtAutor.getText());

        try {
            livroDAO.inserir(livro);
            listaLivros.add(livro);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnAddUsuarioOnAction(ActionEvent event) {
        Usuario usuario = new Usuario();
        usuario.setNome(txtNomeUsuario.getText());

        try {
            usuarioDAO.inserir(usuario);
            listaUsuarios.add(usuario);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
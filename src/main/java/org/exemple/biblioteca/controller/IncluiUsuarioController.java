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
import org.exemple.biblioteca.model.Usuario;
import org.exemple.biblioteca.dao.UsuarioDAO;

import java.sql.SQLException;
import java.util.List;

public class IncluiUsuarioController {
    @FXML
    private TextField txtNomeUsuario;
    @FXML
    private Button btnConf;
    @FXML
    private TableView<Usuario> tabelaUsuarios; // TableView para usuários
    @FXML
    private TableColumn<Usuario, String> colunaNome; // Coluna para o nome do usuário

    private UsuarioDAO usuarioDAO;
    private ObservableList<Usuario> listaUsuarios;

    public IncluiUsuarioController() {
        usuarioDAO = new UsuarioDAO(); // Inicializa o DAO
        listaUsuarios = FXCollections.observableArrayList(); // Inicializa a lista
    }

    @FXML
    void initialize() {
        // Configuração da coluna
        colunaNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));

        carregarUsuarios(); // Carrega os usuários ao iniciar
        tabelaUsuarios.setItems(listaUsuarios); // Define a lista de usuários na tabela
    }

    @FXML
    void btnConfOnAction(ActionEvent event) {
        Usuario usuario = new Usuario();
        usuario.setNome(txtNomeUsuario.getText());

        try {
            usuarioDAO.inserir(usuario);
            listaUsuarios.add(usuario); // Adiciona o usuário à lista
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Usuário cadastrado com sucesso!", ButtonType.OK);
            alert.setTitle("Cadastro de Usuário");
            alert.setHeaderText("Informação");
            alert.show();
            txtNomeUsuario.clear(); // Limpa o campo de nome
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao cadastrar usuário!", ButtonType.OK);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro");
            alert.show();
            e.printStackTrace();
        }
    }

    private void carregarUsuarios() {
        try {
            listaUsuarios.clear(); // Limpa a lista antes de carregar
            List<Usuario> usuarios = usuarioDAO.buscarTodos(); // Busca todos os usuários
            listaUsuarios.addAll(usuarios); // Adiciona todos os usuários à lista
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
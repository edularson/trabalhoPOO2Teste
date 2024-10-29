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
import org.exemple.biblioteca.dao.UsuarioDAO;
import org.exemple.biblioteca.model.Usuario;

import java.sql.SQLException;
import java.util.List;

public class IncluiUsuarioController {
    @FXML
    private TextField txtNomeUsuario;
    @FXML
    private Button btnConf;
    @FXML
    private TableView<Usuario> tabelaUsuarios;
    @FXML
    private TableColumn<Usuario, String> colunaNome;

    private UsuarioDAO usuarioDAO;
    private ObservableList<Usuario> listaUsuarios;

    public IncluiUsuarioController() {
        usuarioDAO = new UsuarioDAO();
        listaUsuarios = FXCollections.observableArrayList();
    }

    @FXML
    void initialize() {
        colunaNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));

        try {
            listaUsuarios.addAll(usuarioDAO.buscarTodos());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tabelaUsuarios.setItems(listaUsuarios);
    }

    @FXML
    void btnConfOnAction(ActionEvent event) {
        Usuario usuario = new Usuario();
        usuario.setNome(txtNomeUsuario.getText());

        try {
            usuarioDAO.inserir(usuario);
            listaUsuarios.add(usuario);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Usuário cadastrado com sucesso!", ButtonType.OK);
            alert.setTitle("Cadastro de Usuário");
            alert.setHeaderText("Informação");
            alert.show();
            txtNomeUsuario.clear();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao cadastrar usuário!", ButtonType.OK);
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
        Usuario usuarioSelecionado = tabelaUsuarios.getSelectionModel().getSelectedItem();

        if (usuarioSelecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja excluir o usuário " + usuarioSelecionado.getNome() + "?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Confirmação de Exclusão");
            alert.setHeaderText("Atenção!");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        usuarioDAO.deletar(usuarioSelecionado.getUsuarioID());
                        listaUsuarios.remove(usuarioSelecionado);
                        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION, "Usuário excluído com sucesso!", ButtonType.OK);
                        infoAlert.setTitle("Exclusão de Usuário");
                        infoAlert.setHeaderText("Informação");
                        infoAlert.show();
                    } catch (SQLException e) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Erro ao excluir usuário!", ButtonType.OK);
                        errorAlert.setTitle("Erro");
                        errorAlert.setHeaderText("Erro");
                        errorAlert.show();
                        e.printStackTrace();
                    }
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecione um usuário para excluir.", ButtonType.OK);
            alert.setTitle("Aviso");
            alert.setHeaderText("Nenhum usuário selecionado");
            alert.show();
        }
    }
}
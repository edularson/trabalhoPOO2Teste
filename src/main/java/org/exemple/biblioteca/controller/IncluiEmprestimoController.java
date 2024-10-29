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
import org.exemple.biblioteca.dao.EmprestimoDAO;
import org.exemple.biblioteca.dao.UsuarioDAO;
import org.exemple.biblioteca.model.Emprestimo;
import org.exemple.biblioteca.model.Livro;
import org.exemple.biblioteca.model.Usuario;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class IncluiEmprestimoController {
    @FXML
    private Button btnConf;
    @FXML
    private TableView<Emprestimo> tabelaEmprestimos;
    @FXML
    private TableColumn<Emprestimo, String> colunaUsuarioID;
    @FXML
    private TableColumn<Emprestimo, String> colunaUsuario;
    @FXML
    private TableColumn<Emprestimo, String> colunaLivroID;
    @FXML
    private TableColumn<Emprestimo, String> colunaLivro;
    @FXML
    private TableColumn<Emprestimo, String> colunaDataEmprestimo;
    @FXML
    private TableColumn<Emprestimo, String> colunaDataDevolucao;

    @FXML
    private TextField txtUsuarioID;
    @FXML
    private TextField txtLivroID;
    @FXML
    private TextField txtDataEmprestimo;
    @FXML
    private TextField txtDataDevolucao;

    private EmprestimoDAO emprestimoDAO;
    private ObservableList<Emprestimo> listaEmprestimos;
    private UsuarioDAO usuarioDAO;

    public IncluiEmprestimoController() {
        emprestimoDAO = new EmprestimoDAO();
        usuarioDAO = new UsuarioDAO();
        listaEmprestimos = FXCollections.observableArrayList();
    }

    @FXML
    void initialize() {
        colunaUsuarioID.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getUsuario().getUsuarioID())));
        colunaUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsuario().getNome()));
        colunaLivroID.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getLivro().getLivroID())));
        colunaLivro.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLivro().getTitulo()));
        colunaDataEmprestimo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDataEmprestimo().toString()));
        colunaDataDevolucao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDataDevolucao() != null ? cellData.getValue().getDataDevolucao().toString() : ""));

        carregarEmprestimos();
    }

    @FXML
    void btnConfOnAction(ActionEvent event) {
        verificarUsuarioEInserirEmprestimo(event);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void carregarEmprestimos() {
        try {
            listaEmprestimos.clear();
            listaEmprestimos.addAll(emprestimoDAO.buscarTodos());
            tabelaEmprestimos.setItems(listaEmprestimos);
        } catch (SQLException e) {
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
        Emprestimo emprestimoSelecionado = tabelaEmprestimos.getSelectionModel().getSelectedItem();

        if (emprestimoSelecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja excluir o empréstimo do livro " + emprestimoSelecionado.getLivro().getTitulo() + " para o usuário " + emprestimoSelecionado.getUsuario().getNome() + "?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Confirmação de Exclusão");
            alert.setHeaderText("Atenção!");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        emprestimoDAO.deletar(emprestimoSelecionado.getEmprestimoID());
                        listaEmprestimos.remove(emprestimoSelecionado);
                        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION, "Empréstimo excluído com sucesso!", ButtonType.OK);
                        infoAlert.setTitle("Exclusão de Empréstimo");
                        infoAlert.setHeaderText("Informação");
                        infoAlert.show();
                    } catch (SQLException e) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Erro ao excluir empréstimo!", ButtonType.OK);
                        errorAlert.setTitle("Erro");
                        errorAlert.setHeaderText("Erro");
                        errorAlert.show();
                        e.printStackTrace();
                    }
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecione um empréstimo para excluir.", ButtonType.OK);
            alert.setTitle("Aviso");
            alert.setHeaderText("Nenhum empréstimo selecionado");
            alert.show();
        }
    }

    @FXML
    void verificarUsuarioEInserirEmprestimo(ActionEvent event) {
        Emprestimo emprestimo = new Emprestimo();

        try {
            Usuario usuario = new Usuario();
            usuario.setUsuarioID(Integer.parseInt(txtUsuarioID.getText()));

            if (usuarioDAO.usuarioExiste(usuario.getUsuarioID())) {
                emprestimo.setUsuario(usuario);
            } else {
                showAlert("Erro", "Usuário não encontrado.");
                return;
            }

            Livro livro = new Livro();
            livro.setLivroID(Integer.parseInt(txtLivroID.getText()));
            emprestimo.setLivro(livro);

            emprestimo.setDataEmprestimo(LocalDate.parse(txtDataEmprestimo.getText()));
            if (!txtDataDevolucao.getText().isEmpty()) {
                emprestimo.setDataDevolucao(LocalDate.parse(txtDataDevolucao.getText()));
            } else {
                emprestimo.setDataDevolucao(null);
            }

            emprestimoDAO.inserir(emprestimo);
            listaEmprestimos.add(emprestimo);

            txtUsuarioID.clear();
            txtLivroID.clear();
            txtDataEmprestimo.clear();
            txtDataDevolucao.clear();
        } catch (NumberFormatException e) {
            showAlert("Erro", "Por favor, digite os dados corretamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
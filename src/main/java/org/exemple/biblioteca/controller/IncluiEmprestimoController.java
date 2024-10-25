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
import org.exemple.biblioteca.model.Emprestimo;
import org.exemple.biblioteca.dao.EmprestimoDAO;

import java.sql.SQLException;
import java.util.List;

public class IncluiEmprestimoController {
    @FXML
    private Button btnConf;
    @FXML
    private TableView<Emprestimo> tabelaEmprestimos; // TableView para empréstimos
    @FXML
    private TableColumn<Emprestimo, String> colunaUsuario; // Coluna para o usuário
    @FXML
    private TableColumn<Emprestimo, String> colunaLivro; // Coluna para o livro
    @FXML
    private TableColumn<Emprestimo, String> colunaDataEmprestimo; // Coluna para a data de empréstimo
    @FXML
    private TableColumn<Emprestimo, String> colunaDataDevolucao; // Coluna para a data de devolução

    private EmprestimoDAO emprestimoDAO;
    private ObservableList<Emprestimo> listaEmprestimos;

    public IncluiEmprestimoController() {
        emprestimoDAO = new EmprestimoDAO(); // Inicializa o DAO
        listaEmprestimos = FXCollections.observableArrayList(); // Inicializa a lista
    }

    @FXML
    void initialize() {
        // Configuração das colunas
        colunaUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsuario().getNome()));
        colunaLivro.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLivro().getTitulo()));
        colunaDataEmprestimo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDataEmprestimo().toString()));
        colunaDataDevolucao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDataDevolucao().toString()));

        carregarEmprestimos(); // Carrega os empréstimos ao iniciar
        tabelaEmprestimos.setItems(listaEmprestimos); // Define a lista de empréstimos na tabela
    }

    @FXML
    void btnConfOnAction(ActionEvent event) {
        // Implemente a lógica para inserir um novo empréstimo
    }

    private void carregarEmprestimos() {
        try {
            listaEmprestimos.clear(); // Limpa a lista antes de carregar
            List<Emprestimo> emprestimos = emprestimoDAO.buscarTodos(); // Busca todos os empréstimos
            listaEmprestimos.addAll(emprestimos); // Adiciona todos os empréstimos à lista
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
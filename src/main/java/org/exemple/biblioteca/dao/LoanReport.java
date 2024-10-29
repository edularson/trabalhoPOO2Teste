package org.exemple.biblioteca.dao;

import org.exemple.biblioteca.model.Emprestimo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanReport {
    private static final String URL = "jdbc:postgresql://localhost:5432/biblioteca";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public List<Emprestimo> generateReport() throws SQLException {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT e.*, u.nome AS usuario_nome, l.titulo AS livro_titulo " +
                "FROM emprestimo e " +
                "JOIN usuario u ON e.usuarioID = u.usuarioID " +
                "JOIN livro l ON e.livroID = l.livroID";
        try (Connection conexao = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Emprestimo emprestimo = new Emprestimo();
                emprestimo.setEmprestimoID(rs.getInt("emprestimoID"));
                // ...
                emprestimos.add(emprestimo);
            }
        }
        return emprestimos;
    }
}
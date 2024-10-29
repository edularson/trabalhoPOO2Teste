package org.exemple.biblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.exemple.biblioteca.model.Emprestimo;
import org.exemple.biblioteca.model.Livro;
import org.exemple.biblioteca.model.Usuario;

public class EmprestimoDAO implements IEmprestimo {
    private static final String URL = "jdbc:postgresql://localhost:5432/biblioteca";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    @Override
    public void inserir(Emprestimo emprestimo) throws SQLException {
        String sql = "INSERT INTO emprestimo (usuarioID, livroID, data_emprestimo, data_devolucao) VALUES (?, ?, ?, ?)";
        try (Connection conexao = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, emprestimo.getUsuario().getUsuarioID());
            pstmt.setInt(2, emprestimo.getLivro().getLivroID());
            pstmt.setDate(3, Date.valueOf(emprestimo.getDataEmprestimo()));
            pstmt.setDate(4, emprestimo.getDataDevolucao() != null ? Date.valueOf(emprestimo.getDataDevolucao()) : null);
            pstmt.executeUpdate();
        }
    }

    public List<Emprestimo> buscarTodos() throws SQLException {
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
                emprestimo.setDataEmprestimo(rs.getDate("data_emprestimo").toLocalDate());
                emprestimo.setDataDevolucao(rs.getDate("data_devolucao") != null ? rs.getDate("data_devolucao").toLocalDate() : null);

                Usuario usuario = new Usuario();
                usuario.setUsuarioID(rs.getInt("usuarioID"));
                usuario.setNome(rs.getString("usuario_nome"));
                emprestimo.setUsuario(usuario);

                Livro livro = new Livro();
                livro.setLivroID(rs.getInt("livroID"));
                livro.setTitulo(rs.getString("livro_titulo"));
                emprestimo.setLivro(livro);

                emprestimos.add(emprestimo);
            }
        }
        return emprestimos;
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM emprestimo WHERE emprestimoID = ?";

        try (Connection conexao = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
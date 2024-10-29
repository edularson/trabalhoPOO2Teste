package org.exemple.biblioteca.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.exemple.biblioteca.model.Livro;

public class LivroDAO implements ILivro {
    private static final String URL = "jdbc:postgresql://localhost:5432/biblioteca";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    @Override
    public void inserir(Livro livro) throws SQLException {
        String sql = "INSERT INTO livro (titulo, autor) VALUES (?, ?)";
        try (Connection conexao = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, livro.getTitulo());
            pstmt.setString(2, livro.getAutor());
            pstmt.executeUpdate();
        }
    }

    public List<Livro> buscarTodos() throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livro";

        try (Connection conexao = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Livro livro = new Livro();
                livro.setLivroID(rs.getInt("livroID"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livros.add(livro);
            }
        }
        return livros;
    }

    public void deletar(int livroID) throws SQLException {
        String sql = "DELETE FROM livro WHERE livroID = ?";

        try (Connection conexao = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, livroID);
            pstmt.executeUpdate();
        }
    }

    public List<Livro> searchByTitleOrAuthor(String searchTerm) throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livro WHERE titulo ILIKE ? OR autor ILIKE ?";

        try (Connection conexao = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, "%" + searchTerm + "%");
            pstmt.setString(2, "%" + searchTerm + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Livro livro = new Livro();
                livro.setLivroID(rs.getInt("livroID"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livros.add(livro);
            }
        }
        return livros;
    }
}
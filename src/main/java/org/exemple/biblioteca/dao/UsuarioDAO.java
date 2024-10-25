package org.exemple.biblioteca.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.exemple.biblioteca.model.Usuario;

public class UsuarioDAO implements IUsuario {
    private static final String URL = "jdbc:postgresql://localhost:5432/biblioteca";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    @Override
    public void inserir(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nome) VALUES (?)";
        try (Connection conexao = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getNome());
            pstmt.executeUpdate();
        }
    }

    public List<Usuario> buscarTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario"; // Certifique-se de que o nome da tabela está correto

        try (Connection conexao = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setUsuarioID(rs.getInt("usuarioID")); // Supondo que você tenha um campo ID na tabela
                usuario.setNome(rs.getString("nome"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }
}
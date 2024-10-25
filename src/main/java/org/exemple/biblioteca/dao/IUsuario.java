package org.exemple.biblioteca.dao;


import java.sql.SQLException;
import org.exemple.biblioteca.model.Usuario;

public interface IUsuario {
    void inserir(Usuario usuario) throws SQLException;
}
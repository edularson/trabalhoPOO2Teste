package org.exemple.biblioteca.dao;

import java.sql.SQLException;
import org.exemple.biblioteca.model.Livro;

public interface ILivro {
    void inserir(Livro livro) throws SQLException;
}
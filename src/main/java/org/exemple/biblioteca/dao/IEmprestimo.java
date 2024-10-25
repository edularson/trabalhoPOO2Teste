package org.exemple.biblioteca.dao;

import java.sql.SQLException;
import org.exemple.biblioteca.model.Emprestimo;

public interface IEmprestimo {
    void inserir(Emprestimo emprestimo) throws SQLException;
}

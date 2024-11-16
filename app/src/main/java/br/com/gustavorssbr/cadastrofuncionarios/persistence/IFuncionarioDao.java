package br.com.gustavorssbr.cadastrofuncionarios.persistence;

import java.sql.SQLException;

public interface IFuncionarioDao {
    FuncionarioDao open() throws SQLException;
    void close() throws SQLException;
}

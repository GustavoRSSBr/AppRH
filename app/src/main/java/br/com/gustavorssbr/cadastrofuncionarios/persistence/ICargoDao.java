package br.com.gustavorssbr.cadastrofuncionarios.persistence;

import java.sql.SQLException;

public interface ICargoDao {
    CargoDao open() throws SQLException;
    void close() throws SQLException;
}

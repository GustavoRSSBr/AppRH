package br.com.gustavorssbr.cadastrofuncionarios.controller;

import java.sql.SQLException;
import java.util.List;

import br.com.gustavorssbr.cadastrofuncionarios.model.Funcionario;
import br.com.gustavorssbr.cadastrofuncionarios.persistence.FuncionarioDao;

public class FuncionarioController implements IController<Funcionario>{
    private final FuncionarioDao fDao;

    public FuncionarioController(FuncionarioDao fDao) {
        this.fDao = fDao;
    }

    @Override
    public void insert(Funcionario funcionario) throws SQLException {
        if(fDao.open() == null){
            fDao.open();
        }

        fDao.insert(funcionario);

        fDao.close();
    }

    @Override
    public void update(Funcionario funcionario) throws SQLException {
        if(fDao.open() == null){
            fDao.open();
        }

        fDao.update(funcionario);

        fDao.close();
    }

    @Override
    public void delete(Funcionario funcionario) throws SQLException {
        if(fDao.open() == null){
            fDao.open();
        }

        fDao.delete(funcionario);

        fDao.close();
    }

    @Override
    public Funcionario findOne(Funcionario funcionario) throws SQLException {
        if(fDao.open() == null){
            fDao.open();
        }
        return fDao.findOne(funcionario);
    }

    @Override
    public List<Funcionario> findAll() throws SQLException {
        if(fDao.open() == null){
            fDao.open();
        }

        return fDao.findAll();
    }
}

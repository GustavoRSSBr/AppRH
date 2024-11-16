package br.com.gustavorssbr.cadastrofuncionarios.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import br.com.gustavorssbr.cadastrofuncionarios.model.Cargo;
import br.com.gustavorssbr.cadastrofuncionarios.persistence.CargoDao;

public class CargoController implements IController<Cargo>{
    private final CargoDao cDao;

    public CargoController(CargoDao cDao) {
        this.cDao = cDao;
    }

    @Override
    public void insert(Cargo cargo) throws SQLException {
        if(cDao.open() == null){
            cDao.open();
        }

        cDao.insert(cargo);

        cDao.close();
    }

    @Override
    public void update(Cargo cargo) throws SQLException {
        if(cDao.open() == null){
            cDao.open();
        }

        cDao.update(cargo);

        cDao.close();
    }

    @Override
    public void delete(Cargo cargo) throws SQLException {
        if(cDao.open() == null){
            cDao.open();
        }

        cDao.delete(cargo);

        cDao.close();
    }

    @Override
    public Cargo findOne(Cargo cargo) throws SQLException {
        if(cDao.open() == null){
            cDao.open();
        }

        return cDao.findOne(cargo);
    }

    @Override
    public List<Cargo> findAll() throws SQLException {
        if(cDao.open() == null){
            cDao.open();
        }
        return cDao.findAll();
    }
}

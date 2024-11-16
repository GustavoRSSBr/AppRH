package br.com.gustavorssbr.cadastrofuncionarios.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.gustavorssbr.cadastrofuncionarios.model.Cargo;

public class CargoDao implements ICargoDao, ICRUDDao<Cargo>{
    private final Context context;
    private GenericDao gdao;
    private SQLiteDatabase database;

    public CargoDao(Context context){
        this.context = context;
    }

    @Override
    public CargoDao open() throws SQLException {
        gdao = new GenericDao(context);
        database = gdao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() throws SQLException {
        gdao.close();
    }

    @Override
    public void insert(Cargo cargo) throws SQLException {
        ContentValues contentValues = getContentValues(cargo);
        database.insert("cargo", null, contentValues);
    }



    @Override
    public int update(Cargo cargo) throws SQLException {
        ContentValues contentValues = getContentValues(cargo);
        return database.update("cargo", contentValues, "codigo = "
                +cargo.getCodigo(), null);
    }

    @Override
    public void delete(Cargo cargo) throws SQLException {
        database.delete("cargo", "codigo = "
                +cargo.getCodigo(), null);
    }

    @SuppressLint("Range")
    @Override
    public Cargo findOne(Cargo cargo) throws SQLException {
        String sql = "SELECT codigo, nome FROM cargo where codigo = " + cargo.getCodigo();
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        if(!cursor.isAfterLast()){
            cargo.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            cargo.setNome(cursor.getString(cursor.getColumnIndex("nome")));
        }

        cursor.close();

        return cargo;
    }

    @SuppressLint("Range")
    @Override
    public List<Cargo> findAll() throws SQLException {
        List<Cargo> cargos = new ArrayList<>();
        String sql = "SELECT codigo, nome FROM cargo";
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()){
            Cargo cargo = new Cargo();
            cargo.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            cargo.setNome(cursor.getString(cursor.getColumnIndex("nome")));

            cargos.add(cargo);
            cursor.moveToNext();
        }

        cursor.close();

        return cargos;
    }

    private static ContentValues getContentValues(Cargo cargo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("codigo", cargo.getCodigo());
        contentValues.put("nome", cargo.getNome());

        return contentValues;
    }
}

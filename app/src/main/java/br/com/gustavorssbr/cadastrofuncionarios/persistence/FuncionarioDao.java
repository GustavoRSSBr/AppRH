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
import br.com.gustavorssbr.cadastrofuncionarios.model.Clt;
import br.com.gustavorssbr.cadastrofuncionarios.model.Funcionario;
import br.com.gustavorssbr.cadastrofuncionarios.model.Pj;

public class FuncionarioDao implements IFuncionarioDao, ICRUDDao<Funcionario> {
    private final Context context;
    private GenericDao gdao;
    private SQLiteDatabase database;

    public FuncionarioDao(Context context) {
        this.context = context;
    }

    @Override
    public FuncionarioDao open() throws SQLException {
        gdao = new GenericDao(context);
        database = gdao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() throws SQLException {
        gdao.close();
    }

    @Override
    public void insert(Funcionario funcionario) throws SQLException {
        ContentValues contentValues = getContentValues(funcionario);
        database.insert("funcionario", null, contentValues);
    }

    @Override
    public int update(Funcionario funcionario) throws SQLException {
        ContentValues contentValues = getContentValues(funcionario);
        return database.update("funcionario", contentValues, "codigo = "
                + funcionario.getCodigo(), null);
    }

    @Override
    public void delete(Funcionario funcionario) throws SQLException {
        database.delete("funcionario", "codigo = "
                +funcionario.getCodigo(), null);
    }

    @Override
    @SuppressLint("Range")
    public Funcionario findOne(Funcionario funcionario) throws SQLException {
        String sql = "SELECT c.codigo AS codigo_cargo, c.nome AS nome_cargo, " +
                "f.codigo, f.nome, f.documento, f.salario " +
                "FROM cargo c " +
                "INNER JOIN funcionario f ON c.codigo = f.codigo_cargo " +
                "WHERE f.codigo = " + funcionario.getCodigo();

        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Funcionario novoFuncionario = null;

        if (!cursor.isAfterLast()) {
            String documento = cursor.getString(cursor.getColumnIndex("documento"));
            if (documento.length() > 12) {
                novoFuncionario = new Pj();
                ((Pj) novoFuncionario).setCnpj(documento);
            } else {
                novoFuncionario = new Clt();
                ((Clt) novoFuncionario).setCpf(documento);
            }

            int codigo = cursor.getInt(cursor.getColumnIndex("codigo"));
            String nome = cursor.getString(cursor.getColumnIndex("nome"));
            double salario = cursor.getDouble(cursor.getColumnIndex("salario"));
            int codigoCargo = cursor.getInt(cursor.getColumnIndex("codigo_cargo"));
            String nomeCargo = cursor.getString(cursor.getColumnIndex("nome_cargo"));

            Cargo cargo = new Cargo();
            cargo.setCodigo(codigoCargo);
            cargo.setNome(nomeCargo);

            novoFuncionario.setCodigo(codigo);
            novoFuncionario.setNome(nome);
            novoFuncionario.setSalario(salario);
            novoFuncionario.setCargo(cargo);
        }
        return novoFuncionario;
    }


    @Override
    @SuppressLint("Range")
    public List<Funcionario> findAll() throws SQLException {
        String sql = "SELECT c.codigo AS codigo_cargo, c.nome AS nome_cargo, " +
                "f.codigo, f.nome, f.documento, f.salario " +
                "FROM cargo c " +
                "INNER JOIN funcionario f ON c.codigo = f.codigo_cargo";

        Cursor cursor = database.rawQuery(sql, null);
        List<Funcionario> funcionarios = new ArrayList<>();

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {
            Funcionario novoFuncionario;
            String documento = cursor.getString(cursor.getColumnIndex("documento"));
            if (documento.length() > 12) {
                novoFuncionario = new Pj();
                ((Pj) novoFuncionario).setCnpj(documento);
            } else {
                novoFuncionario = new Clt();
                ((Clt) novoFuncionario).setCpf(documento);
            }

            int codigo = cursor.getInt(cursor.getColumnIndex("codigo"));
            String nome = cursor.getString(cursor.getColumnIndex("nome"));
            double salario = cursor.getDouble(cursor.getColumnIndex("salario"));
            int codigoCargo = cursor.getInt(cursor.getColumnIndex("codigo_cargo"));
            String nomeCargo = cursor.getString(cursor.getColumnIndex("nome_cargo"));

            Cargo cargo = new Cargo();
            cargo.setCodigo(codigoCargo);
            cargo.setNome(nomeCargo);

            novoFuncionario.setCodigo(codigo);
            novoFuncionario.setNome(nome);
            novoFuncionario.setSalario(salario);
            novoFuncionario.setCargo(cargo);

            funcionarios.add(novoFuncionario);
            cursor.moveToNext();
        }

        cursor.close();

        return funcionarios;
    }


    private static ContentValues getContentValues(Funcionario funcionario) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("codigo", funcionario.getCodigo());
        contentValues.put("nome", funcionario.getNome());
        contentValues.put("salario", funcionario.getSalario());
        contentValues.put("codigo_cargo", funcionario.getCargo().getCodigo());

        if (funcionario instanceof Pj) {
            Pj pj = (Pj) funcionario;
            contentValues.put("documento", pj.getCnpj());
        } else if (funcionario instanceof Clt) {
            Clt clt = (Clt) funcionario;
            contentValues.put("documento", clt.getCpf());
        } else {
            throw new RuntimeException("documento inválido");
        }

        return contentValues;
    }
}

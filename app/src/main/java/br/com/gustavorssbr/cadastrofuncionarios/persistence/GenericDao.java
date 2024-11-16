package br.com.gustavorssbr.cadastrofuncionarios.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GenericDao extends SQLiteOpenHelper {
    private static final String DATABASE = "RH.DB";
    private static final int DATABASE_VER = 1;
    private static final String CREATE_TABLE_CARGO =
            "CREATE TABLE cargo ( " +
                    "codigo INT NOT NULL PRIMARY KEY, " +
                    "nome VARCHAR(100) NOT NULL );";

    private static final String CREATE_TABLE_FUNC =
            "CREATE TABLE funcionario ( " +
                    "codigo INT NOT NULL PRIMARY KEY, " +
                    "nome VARCHAR(100) NOT NULL, " +
                    "documento VARCHAR(18) NOT NULL, " +
                    "salario REAL NOT NULL, " +
                    "codigo_cargo INT NOT NULL, " +
                    "FOREIGN KEY (codigo_cargo) REFERENCES cargo(codigo));";
    ;


    public GenericDao(Context context ){
        super(context, DATABASE, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_CARGO);
        sqLiteDatabase.execSQL(CREATE_TABLE_FUNC);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int antigaVersao, int novaVersao) {
        if(novaVersao > antigaVersao){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS func");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS cargo");
            onCreate(sqLiteDatabase);
        }
    }
}

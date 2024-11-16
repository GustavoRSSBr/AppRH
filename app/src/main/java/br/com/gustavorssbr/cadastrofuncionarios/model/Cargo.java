package br.com.gustavorssbr.cadastrofuncionarios.model;

import androidx.annotation.NonNull;

public class Cargo {
    int codigo;
    String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @Override
    @NonNull
    public String toString() {
        return codigo + "-" + nome;
    }
}

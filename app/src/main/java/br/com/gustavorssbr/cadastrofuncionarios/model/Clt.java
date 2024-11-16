package br.com.gustavorssbr.cadastrofuncionarios.model;

import androidx.annotation.NonNull;

public class Clt extends Funcionario{
    String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @NonNull
    @Override
    public String toString() {
        return super.getCodigo() + " - " + super.getNome() + " - " + super.getCargo().getNome() + " - " + super.getSalario() + " - " + getCpf();
    }


}

package br.com.gustavorssbr.cadastrofuncionarios.model;

import androidx.annotation.NonNull;

public class Pj extends Funcionario{
    String cnpj;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @NonNull
    @Override
    public String toString() {
        return super.getCodigo() + " - " + super.getNome() + " - " + super.getCargo().getNome() + " - " + super.getSalario() + " - " + getCnpj();
    }
}

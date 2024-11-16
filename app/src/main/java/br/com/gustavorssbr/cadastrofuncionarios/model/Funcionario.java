package br.com.gustavorssbr.cadastrofuncionarios.model;

import androidx.annotation.NonNull;

public abstract class Funcionario {
    private int codigo;
    private String nome;
    private Cargo cargo;
    private double salario;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    @NonNull
    @Override
    public String toString() {
        return "Funcionario{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", cargo=" + cargo +
                ", salario=" + salario +
                '}';
    }
}

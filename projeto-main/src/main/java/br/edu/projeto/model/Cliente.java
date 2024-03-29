package br.edu.projeto.model;

import java.time.LocalDate;

public class Cliente {

    private String nome;
    private String cpf;
    private String sobrenome;
    private LocalDate data_nascimento;
    private String email;
    private String funcionario_cpf; 

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFuncionario_cpf() {
        return funcionario_cpf;
    }

    public void setFuncionario_cpf(String funcionario_cpf) {
        this.funcionario_cpf = funcionario_cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(LocalDate data_nascimento) {
        this.data_nascimento = data_nascimento;
    }
}

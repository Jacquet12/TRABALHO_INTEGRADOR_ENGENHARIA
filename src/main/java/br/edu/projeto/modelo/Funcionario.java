package br.edu.projeto.modelo;

public class Funcionario {
    private String nome_completo;
    private String cpf;
    private String email;
    private String senha;
    private String confirmacao_senha;
    private String data_cadastro;
    private String administrador_cpf;

    
    public String getNome_completo() {
        return nome_completo;
    }
    public void setNome_completo(String nome_completo) {
        this.nome_completo = nome_completo;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getConfirmacao_senha() {
        return confirmacao_senha;
    }
    public void setConfirmacao_senha(String confirmacao_senha) {
        this.confirmacao_senha = confirmacao_senha;
    }
    public String getData_cadastro() {
        return data_cadastro;
    }
    public void setData_cadastro(String data_cadastro) {
        this.data_cadastro = data_cadastro;
    }
    public String getAdministrador_cpf() {
        return administrador_cpf;
    }
    public void setAdministrador_cpf(String administrador_cpf) {
        this.administrador_cpf = administrador_cpf;
    }

}

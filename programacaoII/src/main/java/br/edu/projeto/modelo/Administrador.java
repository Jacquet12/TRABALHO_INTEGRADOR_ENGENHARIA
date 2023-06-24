package br.edu.projeto.modelo;

public class Administrador {
    private String cpf;
    private String nome;
    private String sobre_nome;
    private String email;
    private String senha;


    
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getSobre_nome() {
        return sobre_nome;
    }
    public void setSobre_nome(String sobre_nome) {
        this.sobre_nome = sobre_nome;
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
}

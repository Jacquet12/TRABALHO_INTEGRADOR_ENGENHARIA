package br.edu.projeto.modelo;

public class Funcionario {
    private String nome;
    private String sobre_nome;
    private String tipo_foncionario;
    private String cpf;
    private String email;
    private String senha;
    private String confirmacao_senha;
    private String administrador_cpf;

    
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

    public String getTipo_foncionario() {
        return tipo_foncionario;
    }
    public void setTipo_foncionario(String tipo_foncionario) {
        this.tipo_foncionario = tipo_foncionario;
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
    
    public String getAdministrador_cpf() {
        return administrador_cpf;
    }
    public void setAdministrador_cpf(String administrador_cpf) {
        this.administrador_cpf = administrador_cpf;
    }

}

package br.edu.projeto.model;

public class Funcionario {
    private String nome;
    private String sobre_nome;
    
    private String tipo_funcionario;
    private String cpf;
    private String email;
    private String senha;
    private String phone_number;
    private String confirmacao_senha;



    public String getPhone_number() {
        return phone_number;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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
    public String getTipo_funcionario() {
        return tipo_funcionario;
    }
    public void setTipo_funcionario(String tipo_funcionario) {
        this.tipo_funcionario = tipo_funcionario;
    }

}

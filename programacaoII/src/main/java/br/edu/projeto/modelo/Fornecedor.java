package br.edu.projeto.modelo;

public class Fornecedor {
    private String cnpj;
    private String nome_fornecedor;
    private String tipo_produtos;
    private String telefone;
    private String pais;
    private String administrador_cpf;


    
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public String getNome_fornecedor() {
        return nome_fornecedor;
    }
    public void setNome_fornecedor(String nome_fornecedor) {
        this.nome_fornecedor = nome_fornecedor;
    }
    public String getTipo_produtos() {
        return tipo_produtos;
    }
    public void setTipo_produtos(String tipo_produtos) {
        this.tipo_produtos = tipo_produtos;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getPais() {
        return pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }
    public String getAdministrador_cpf() {
        return administrador_cpf;
    }
    public void setAdministrador_cpf(String administrador_cpf) {
        this.administrador_cpf = administrador_cpf;
    }
    
}

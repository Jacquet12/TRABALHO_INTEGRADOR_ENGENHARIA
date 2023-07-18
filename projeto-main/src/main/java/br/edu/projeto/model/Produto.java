package br.edu.projeto.model;

import java.math.BigDecimal;

public class Produto {

    private int quantidadeEstoque = 0;
    private String funcionarioCpf;
    private String codProduto;
    private String categoria;
    private String descricao;
    private String nome;
    private BigDecimal preco;
    private String fornecedorCnpj;

    public BigDecimal getPreco() {
        return preco;
    }
    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
   

    
    public String getCodProduto() {
        return codProduto;
    }
    public void setCodProduto(String codProduto) {
        this.codProduto = codProduto;
    }
   
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

   
    public String getFornecedorCnpj() {
        return fornecedorCnpj;
    }
    public void setFornecedorCnpj(String fornecedorCnpj) {
        this.fornecedorCnpj = fornecedorCnpj;
    }

  
    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

 
    public String getFuncionarioCpf() {
        return funcionarioCpf;
    }
    public void setFuncionarioCpf(String funcionarioCpf) {
        this.funcionarioCpf = funcionarioCpf;
    }
   
}

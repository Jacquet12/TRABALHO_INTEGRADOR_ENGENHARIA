package br.edu.projeto.model;

import java.math.BigDecimal;
import java.sql.Date;

import java.time.LocalDate;

public class CupomFiscal {
    private int id;
    private String cpfFuncionario;
    private String cnpjFornecedor;
    private String codProduto;
    private int quantidadeAdquirida;
   

    private LocalDate dataHoraCompra;
  

    private double totalCompra;
    private String formaPagamento;
    private String observacoes;

    // Getters e Setters (ou propriedades, dependendo da IDE ou versão do Java)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpfFuncionario() {
        return cpfFuncionario;
    }

    public void setCpfFuncionario(String cpfFuncionario) {
        this.cpfFuncionario = cpfFuncionario;
    }

    public String getCnpjFornecedor() {
        return cnpjFornecedor;
    }

    public void setCnpjFornecedor(String cnpjFornecedor) {
        this.cnpjFornecedor = cnpjFornecedor;
    }

    public String getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(String codProduto) {
        this.codProduto = codProduto;
    }

    public int getQuantidadeAdquirida() {
        return quantidadeAdquirida;
    }

    public void setQuantidadeAdquirida(int quantidadeAdquirida) {
        this.quantidadeAdquirida = quantidadeAdquirida;
    }

     public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

     public LocalDate getDataHoraCompra() {
        return dataHoraCompra;
    }

    public void setDataHoraCompra(LocalDate dataHoraCompra) {
        this.dataHoraCompra = dataHoraCompra;
    }

   
}


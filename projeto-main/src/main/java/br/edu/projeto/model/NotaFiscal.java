package br.edu.projeto.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class NotaFiscal {
    private String codNotaFiscal;
  

    private LocalDate dataHoraCompra;
    private String funcionarioCpf;
    private String clienteCpf;
    private String produtoCodProduto;
    private int quantidadeComprada;
    private BigDecimal precoTotal;

    public String getCodNotaFiscal() {
        return codNotaFiscal;
    }

    public void setCodNotaFiscal(String codNotaFiscal) {
        this.codNotaFiscal = codNotaFiscal;
    }

     public LocalDate getDataHoraCompra() {
        return dataHoraCompra;
    }

    public void setDataHoraCompra(LocalDate dataHoraCompra) {
        this.dataHoraCompra = dataHoraCompra;
    }

    public String getFuncionarioCpf() {
        return funcionarioCpf;
    }

    public void setFuncionarioCpf(String funcionarioCpf) {
        this.funcionarioCpf = funcionarioCpf;
    }

    public String getClienteCpf() {
        return clienteCpf;
    }

    public void setClienteCpf(String clienteCpf) {
        this.clienteCpf = clienteCpf;
    }

    public String getProdutoCodProduto() {
        return produtoCodProduto;
    }

    public void setProdutoCodProduto(String produtoCodProduto) {
        this.produtoCodProduto = produtoCodProduto;
    }

    public int getQuantidadeComprada() {
        return quantidadeComprada;
    }

    public void setQuantidadeComprada(int quantidadeComprada) {
        this.quantidadeComprada = quantidadeComprada;
    }

    public BigDecimal getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(BigDecimal precoTotal) {
        this.precoTotal = precoTotal;
    }
}


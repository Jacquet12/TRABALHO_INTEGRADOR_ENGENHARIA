package br.edu.projeto.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.projeto.dao.ProdutoDAO;
import br.edu.projeto.dao.NotafiscalDAO;
import br.edu.projeto.model.NotaFiscal;
import br.edu.projeto.model.Produto;

@ViewScoped
@Named
public class NotaFiscalController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ProdutoDAO produtoDAO;

    @Inject
    private NotafiscalDAO notafiscalDAO;

    private List<NotaFiscal> listaNotaFiscais;

    private NotaFiscal notaFiscal = new NotaFiscal();

    @PostConstruct
    public void init() {
        listaNotaFiscais = notafiscalDAO.listAll();
    }

    public void venderProduto() {
        try {
            realizarVenda(notaFiscal.getCodNotaFiscal(), notaFiscal.getDataHoraCompra(), notaFiscal.getFuncionarioCpf(), 
                          notaFiscal.getClienteCpf(), notaFiscal.getProdutoCodProduto(), 
                          notaFiscal.getQuantidadeComprada());
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao realizar a venda.", null));
            e.printStackTrace();
        }
    }

    private void realizarVenda(String codNotaFiscal, LocalDate dataHoraCompra, String funcionarioCpf, 
                String clienteCpf, String produtoCodProduto, int quantidadeComprada) throws SQLException {
            Produto produto = produtoDAO.buscarPorCodigo(produtoCodProduto);

            if (produto != null) {
            // Check if the nota fiscal code provided by the user already exists
            if (notafiscalDAO.buscarNotaFiscalPorCodigo(codNotaFiscal) != null) {
                FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Venda não pode ser realizada. Código de Nota Fiscal já existe.", null));
                return;
            }

            // Check if the product has sufficient stock
            if (produto.getQuantidadeEstoque() < quantidadeComprada) {
                FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Estoque insuficiente para realizar a venda.", null));
                return;
            }

            // Create a new NotaFiscal object for the current transaction
            NotaFiscal notaFiscal = new NotaFiscal();
            notaFiscal.setCodNotaFiscal(codNotaFiscal);
            notaFiscal.setClienteCpf(clienteCpf);
            notaFiscal.setDataHoraCompra(dataHoraCompra);
            notaFiscal.setFuncionarioCpf(funcionarioCpf);
            notaFiscal.setQuantidadeComprada(quantidadeComprada);
            notaFiscal.setProdutoCodProduto(produtoCodProduto);

            // Calculate the total purchase amount
            BigDecimal precoProduto = produto.getPreco();
            BigDecimal quantidadeBigDecimal = BigDecimal.valueOf(quantidadeComprada);
            BigDecimal totalCompra = precoProduto.multiply(quantidadeBigDecimal);
            notaFiscal.setPrecoTotal(totalCompra);

            // Deduct the purchased quantity from the product's stock
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidadeComprada);
            produtoDAO.update(produto); // Assuming you have an update method in your ProdutoDAO

            // Insert the nota fiscal into the database
            notafiscalDAO.insert(notaFiscal);

            // Clear the notaFiscal object for a new transaction
            this.notaFiscal = new NotaFiscal();

            // Update the list of notas fiscais after the sale
            listaNotaFiscais = notafiscalDAO.listAll();

            FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Venda realizada com sucesso!", null));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Produto não encontrado. Não é possível efetuar a venda.", null));
        }
    }

    // Getters and setters for listaNotaFiscais and notaFiscal

    public List<NotaFiscal> getListaNotaFiscais() {
        return listaNotaFiscais;
    }

    public void setListaNotaFiscais(List<NotaFiscal> listaNotaFiscais) {
        this.listaNotaFiscais = listaNotaFiscais;
    }

    public NotaFiscal getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(NotaFiscal notaFiscal) {
        this.notaFiscal = notaFiscal;
    }
}

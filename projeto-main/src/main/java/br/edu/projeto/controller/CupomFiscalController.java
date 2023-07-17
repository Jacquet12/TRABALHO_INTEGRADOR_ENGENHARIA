package br.edu.projeto.controller;

import java.io.Serializable;
import java.math.BigDecimal;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import java.time.LocalDate; // Importe a classe LocalDate

import br.edu.projeto.dao.ProdutoDAO;
import br.edu.projeto.dao.CupomFiscalDAO;
import br.edu.projeto.model.CupomFiscal;
import br.edu.projeto.model.Fornecedor;
import br.edu.projeto.model.Produto;

@ViewScoped
@Named
public class CupomFiscalController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private FacesContext facesContext;

    private List<CupomFiscal> listaCupomFiscals;
    private CupomFiscal cupomFiscal;

    @PostConstruct
    public void init() {
        novoCadastro();
    }

    // Chamado pelo botão novo
    public void novoCadastro() {
        this.cupomFiscal = new CupomFiscal();
    }

    private ProdutoDAO produtoDAO = new ProdutoDAO();
    private CupomFiscalDAO cupomFiscalDAO = new CupomFiscalDAO();

    public void realizarCompra(String cpfFuncionario, String cnpjFornecedor, String codProduto, int quantidadeAdquirida,
                               String formaPagamento, String observacoes) {
        // Verifica se o produto existe na tabela "produto"
        Produto produto = produtoDAO.buscarPorCodigo(codProduto);

        if (produto != null) {
            // Calcula o total de compra multiplicando o preço do produto pela quantidade adquirida
            BigDecimal precoProduto = produto.getPreco();
            BigDecimal quantidadeBigDecimal = BigDecimal.valueOf(quantidadeAdquirida);
            BigDecimal totalCompra = precoProduto.multiply(quantidadeBigDecimal);

            // Abastece a quantidade de produto em estoque
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + quantidadeAdquirida);
            produtoDAO.atualizarQuantidadeEstoque(codProduto, produto.getQuantidadeEstoque());

            // Atribui os valores calculados ao objeto cupomFiscal antes de inserir no banco
            cupomFiscal.setQuantidadeAdquirida(quantidadeAdquirida);
            cupomFiscal.setDataHoraCompra(LocalDate.now()); // Utilize a data atual, ou passe a data desejada em LocalDate
            cupomFiscal.setTotalCompra(totalCompra.doubleValue());
            cupomFiscal.setCpfFuncionario(cpfFuncionario);
            cupomFiscal.setCnpjFornecedor(cnpjFornecedor);
            cupomFiscal.setCodProduto(codProduto);
            cupomFiscal.setFormaPagamento(formaPagamento);
            cupomFiscal.setObservacoes(observacoes);

            // Insere o cupom fiscal no banco de dados
            cupomFiscalDAO.insert(cupomFiscal);

            System.out.println("Compra realizada com sucesso!");
        } else {
            System.out.println("Produto não encontrado. Não é possível efetuar a compra.");
        }
    }

    public CupomFiscal getCupomFiscal() {
        return cupomFiscal;
    }

    public void setCupomFiscal(CupomFiscal cupomFiscal) {
        this.cupomFiscal = cupomFiscal;
    }

    public List<CupomFiscal> getListaCupomFiscals() {
        return listaCupomFiscals;
    }

    public void setListaCupomFiscals(List<CupomFiscal> listaCupomFiscals) {
        this.listaCupomFiscals = listaCupomFiscals;
    }
}

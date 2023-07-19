package br.edu.projeto.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.projeto.dao.ProdutoDAO;
import br.edu.projeto.dao.CupomFiscalDAO;
import br.edu.projeto.model.CupomFiscal;
import br.edu.projeto.model.Produto;

@ViewScoped
@Named
public class CupomFiscalController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ProdutoDAO produtoDAO; // Injeção de dependência para ProdutoDAO

    @Inject
    private CupomFiscalDAO cupomFiscalDAO; // Injeção de dependência para CupomFiscalDAO

    private List<CupomFiscal> listaCupomFiscals;
    private CupomFiscal cupomFiscal = new CupomFiscal();

    @PostConstruct
    public void init() {
        listaCupomFiscals = cupomFiscalDAO.listAll();
    }

    public void comprarProduto() throws SQLException {
        realizarCompra(cupomFiscal.getCpfFuncionario(), cupomFiscal.getCnpjFornecedor(), cupomFiscal.getCodProduto(), cupomFiscal.getQuantidadeAdquirida(), cupomFiscal.getFormaPagamento(), cupomFiscal.getObservacoes(), cupomFiscal.getDataHoraCompra());
    }

    public void realizarCompra(String cpfFuncionario, String cnpjFornecedor, String codProduto, int quantidadeAdquirida,
                           String formaPagamento, String observacoes, LocalDate dataHoraCompra) throws SQLException {

        // Verifica se o produto existe na tabela "produto"
        Produto produto = produtoDAO.buscarPorCodigo(codProduto);

        if (produto != null) {
            // Calcula o total de compra multiplicando o preço do produto pela quantidade adquirida
            BigDecimal precoProduto = produto.getPreco();
            BigDecimal quantidadeBigDecimal = BigDecimal.valueOf(quantidadeAdquirida);
            BigDecimal totalCompra = precoProduto.multiply(quantidadeBigDecimal);

            // Abastece a quantidade de produto em estoque
            int novaQuantidadeEstoque = produto.getQuantidadeEstoque() + quantidadeAdquirida;
            produto.setQuantidadeEstoque(novaQuantidadeEstoque);
            produtoDAO.atualizarQuantidadeEstoque(codProduto, novaQuantidadeEstoque);

            // Atribui os valores calculados ao objeto cupomFiscal antes de inserir no banco
            cupomFiscal.setDataHoraCompra(dataHoraCompra);
            cupomFiscal.setTotalCompra(totalCompra.doubleValue());
            cupomFiscal.setCpfFuncionario(cpfFuncionario);
            cupomFiscal.setCnpjFornecedor(cnpjFornecedor);
            cupomFiscal.setCodProduto(codProduto);
            cupomFiscal.setQuantidadeAdquirida(quantidadeAdquirida);
            cupomFiscal.setFormaPagamento(formaPagamento);
            cupomFiscal.setObservacoes(observacoes);

            // Insere o cupom fiscal no banco de dados
            cupomFiscalDAO.insert(cupomFiscal);

            // Limpa o objeto cupomFiscal para uma nova compra
            cupomFiscal = new CupomFiscal();

            // Atualiza a lista de cupons fiscais após a compra
            listaCupomFiscals = cupomFiscalDAO.listAll();

            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Compra realizada com sucesso!", null));
        } else {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Produto não encontrado. Não é possível efetuar a compra.", null));
        }
}

    public CupomFiscal getCupomFiscal() {
        return cupomFiscal;
    }

    public List<CupomFiscal> getListaCupomFiscals() {
        return listaCupomFiscals;
    }
}

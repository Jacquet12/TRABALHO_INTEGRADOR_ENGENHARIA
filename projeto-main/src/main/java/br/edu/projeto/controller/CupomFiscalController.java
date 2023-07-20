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
import br.edu.projeto.dao.CupomFiscalDAO;
import br.edu.projeto.dao.FornecedorDAO;
import br.edu.projeto.dao.FuncionarioDAO;
import br.edu.projeto.model.CupomFiscal;
import br.edu.projeto.model.Fornecedor;
import br.edu.projeto.model.Funcionario;
import br.edu.projeto.model.Produto;

@ViewScoped
@Named
public class CupomFiscalController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ProdutoDAO produtoDAO;

    @Inject
    private FuncionarioDAO funcionarioDAO;

    @Inject
    private FornecedorDAO fornecedorDAO;

    @Inject
    private CupomFiscalDAO cupomFiscalDAO;

    private List<CupomFiscal> listaCupomFiscals;

    private CupomFiscal cupomFiscal = new CupomFiscal();
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    private Funcionario funcionario = new Funcionario();

    @PostConstruct
    public void init() {
        listaCupomFiscals = cupomFiscalDAO.listAll();
    }

    public void comprarProduto() throws SQLException {
        realizarCompra(cupomFiscal.getId(), cupomFiscal.getCpfFuncionario(), cupomFiscal.getCnpjFornecedor(),
                cupomFiscal.getCodProduto(), cupomFiscal.getQuantidadeAdquirida(), cupomFiscal.getFormaPagamento(),
                cupomFiscal.getObservacoes(), cupomFiscal.getDataHoraCompra());
    }

    public void realizarCompra(int id, String cpfFuncionario, String cnpjFornecedor, String codProduto,
            int quantidadeAdquirida, String formaPagamento, String observacoes, LocalDate dataHoraCompra)
            throws SQLException {

            // Verifica se o produto existe na tabela "produto"
            Produto produto = produtoDAO.buscarPorCodigo(codProduto);

            if (produto != null) {
            // Verifica se o funcionário existe na tabela "funcionario"
            Funcionario funcionario = funcionarioDAO.buscarPorCPF(cpfFuncionario);
                if (funcionario == null) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Funcionário não encontrado. Não é possível efetuar a compra.", null));
                return;
            }

            // Verifica se o fornecedor existe na tabela "fornecedor"
            Fornecedor fornecedor = fornecedorDAO.buscarPorCNPJ(cnpjFornecedor);
                if (fornecedor == null) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Fornecedor não encontrado. Não é possível efetuar a compra.", null));
                return;
            }

            // Restante do código para a compra...

            // Calcula o total de compra multiplicando o preço do produto pela quantidade adquirida
            BigDecimal precoProduto = produto.getPreco();
            BigDecimal quantidadeBigDecimal = BigDecimal.valueOf(quantidadeAdquirida);
            BigDecimal totalCompra = precoProduto.multiply(quantidadeBigDecimal);

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

            // Atualiza o estoque do produto após a compra
            int estoqueAtual = produto.getQuantidadeEstoque();
            int novoEstoque = estoqueAtual + quantidadeAdquirida;
            produto.setQuantidadeEstoque(novoEstoque);
            produtoDAO.update(produto);

            // Limpa o objeto cupomFiscal para uma nova compra
            cupomFiscal = new CupomFiscal();

            // Atualiza a lista de cupons fiscais após a compra
            listaCupomFiscals = cupomFiscalDAO.listAll();

            facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Compra realizada com sucesso!", null));
            } else {
                facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Produto não encontrado. Não é possível efetuar a compra.",
            null));
            }
        }


    public CupomFiscal getCupomFiscal() {
        return cupomFiscal;
    }

    public List<CupomFiscal> getListaCupomFiscals() {
        return listaCupomFiscals;
    }
}

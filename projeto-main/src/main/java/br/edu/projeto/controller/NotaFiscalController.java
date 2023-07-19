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
import br.edu.projeto.dao.NotafiscalDAO;
import br.edu.projeto.model.CupomFiscal;
import br.edu.projeto.model.NotaFiscal;
import br.edu.projeto.model.Produto;

@ViewScoped
@Named
public class NotaFiscalController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ProdutoDAO produtoDAO; // Injeção de dependência para ProdutoDAO

    @Inject
    private NotafiscalDAO notafiscalDAO; // Injeção de dependência para CupomFiscalDAO

    private List<NotaFiscal> listaNotaFiscals;
   

    private NotaFiscal notaFiscal = new NotaFiscal();

    @PostConstruct
    public void init() {
        listaNotaFiscals = notafiscalDAO.listAll();
    }

    public void venderProduto() throws SQLException{
        realizarVenda(notaFiscal.getDataHoraCompra(), notaFiscal.getFuncionarioCpf(), notaFiscal.getClienteCpf(), notaFiscal.getProdutoCodProduto(), notaFiscal.getQuantidadeComprada());
    }


    public void realizarVenda(LocalDate dataHoraCompra,String funcionarioCpf, String clienteCpf,
                           String produtoCodProduto,int quantidadeComprada) throws SQLException {

        // Verifica se o produto existe na tabela "produto"
        Produto produto = produtoDAO.buscarPorCodigo(produtoCodProduto);

        if (produto != null) {
            // Calcula o total de compra multiplicando o preço do produto pela quantidade adquirida
            BigDecimal precoProduto = produto.getPreco();
            BigDecimal quantidadeBigDecimal = BigDecimal.valueOf(quantidadeComprada);
            BigDecimal totalCompra = precoProduto.multiply(quantidadeBigDecimal);

            // Abastece a quantidade de produto em estoque
            int novaQuantidadeEstoque = produto.getQuantidadeEstoque() - quantidadeComprada;
            produto.setQuantidadeEstoque(novaQuantidadeEstoque);
            produtoDAO.atualizarQuantidadeEstoque(produtoCodProduto, novaQuantidadeEstoque);

            // Atribui os valores calculados ao objeto cupomFiscal antes de inserir no banco
            notaFiscal.setClienteCpf(clienteCpf);
            notaFiscal.setDataHoraCompra(dataHoraCompra);
            notaFiscal.setFuncionarioCpf(funcionarioCpf);
            notaFiscal.setQuantidadeComprada(quantidadeComprada);
            notaFiscal.setProdutoCodProduto(produtoCodProduto);
            notaFiscal.setPrecoTotal(totalCompra);
            // Insere o cupom fiscal no banco de dados
            notafiscalDAO.insert(notaFiscal);

            // Limpa o objeto cupomFiscal para uma nova compra
            notaFiscal = new NotaFiscal();
            // Atualiza a lista de cupons fiscais após a compra
            listaNotaFiscals = notafiscalDAO.listAll();

            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Venda realizada com sucesso!", null));
        } else {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Produto não encontrado. Não é possível efetuar a Venda.", null));
        }
    }

       public List<NotaFiscal> getListaNotaFiscals() {
        return listaNotaFiscals;
    }

    public void setListaNotaFiscals(List<NotaFiscal> listaNotaFiscals) {
        this.listaNotaFiscals = listaNotaFiscals;
    }

    public NotaFiscal getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(NotaFiscal notaFiscal) {
        this.notaFiscal = notaFiscal;
    }


   
}


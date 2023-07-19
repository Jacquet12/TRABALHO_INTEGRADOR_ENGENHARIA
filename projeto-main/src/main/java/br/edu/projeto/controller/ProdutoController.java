package br.edu.projeto.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import br.edu.projeto.dao.CupomFiscalDAO;
import br.edu.projeto.dao.NotafiscalDAO;
import br.edu.projeto.dao.ProdutoDAO;
import br.edu.projeto.model.NotaFiscal;
import br.edu.projeto.model.Produto;

@ViewScoped
@Named
public class ProdutoController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ProdutoDAO produtoDAO;

    @Inject
    private NotafiscalDAO notafiscalDAO; // Add this injection

    @Inject
    private Produto produto;
    private List<Produto> listaProdutos;
    private String codigoProdutoParaExclusao;

 

    // Método chamado após a construção da classe
    @PostConstruct
    public void init() {
        novoCadastro();
        try {
            this.listaProdutos = produtoDAO.listAll();
        } catch (SQLException e) {
            // Handle the exception (e.g., log it)
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao carregar a lista de produtos.", null));
        }
    }
    
    // Chamado pelo botão novo
    public void novoCadastro() {
        this.produto = new Produto();
    }


    public void salvarAlteracao() {
        if (this.produtoDAO.update(this.produto)) {
            PrimeFaces.current().executeScript("PF('fornecedorDialog').hide()");
            PrimeFaces.current().ajax().update("form:dt-produto");
            this.facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "produto Atualizado com sucesso", null));
        } else {
            this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Falha ao Atualizar Produto", null));
        }
    }

    public void salvarNovo() {
        if (this.produtoDAO.insert(this.produto)) {
            this.listaProdutos.add(this.produto);
            PrimeFaces.current().executeScript("PF('produtoDialog').hide()");
            PrimeFaces.current().ajax().update("form:dt-produto");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Produto Cadastrado com sucesso!!!", null));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Cadastrar Produto", null));
        }
    }

    

    public void remover() throws SQLException {
        String codigoProduto = this.codigoProdutoParaExclusao;
        Produto produtoParaExcluir = produtoDAO.buscarPorCodigo(codigoProduto);

        if (produtoParaExcluir == null) {
            // Produto não encontrado com o código informado
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Produto não encontrado com o código informado.", null));
        } else {
            try {
                if (this.produtoDAO.delete(produtoParaExcluir)) {
                    this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Produto Removido com sucesso!!!", null));
                    this.listaProdutos.remove(produtoParaExcluir);
                } else {
                    this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Falha ao Remover Produto, talvez esteja em uso!", null));
                }
            } catch (Exception e) {
                // Captura a mensagem de erro da exceção
                errorMessage = e.getMessage();
            }
        }

        PrimeFaces.current().ajax().update("form:messages", "form:dt-produto", "form:error");
    }

  

    private String errorMessage;

    // Getter e Setter da mensagem de erro
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public String getCodigoProdutoParaExclusao() {
        return codigoProdutoParaExclusao;
    }

    public void setCodigoProdutoParaExclusao(String codigoProdutoParaExclusao) {
        this.codigoProdutoParaExclusao = codigoProdutoParaExclusao;
    }


       // Getters e setters
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<Produto> getListaProdutos() {
        return listaProdutos;
    }

    public void setListaProdutos(List<Produto> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    public ProdutoDAO getProdutoDAO() {
        return produtoDAO;
    }

    public void setProdutoDAO(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    public NotafiscalDAO getNotafiscalDAO() {
        return notafiscalDAO;
    }

    public void setNotafiscalDAO(NotafiscalDAO notafiscalDAO) {
        this.notafiscalDAO = notafiscalDAO;
    }

   


}


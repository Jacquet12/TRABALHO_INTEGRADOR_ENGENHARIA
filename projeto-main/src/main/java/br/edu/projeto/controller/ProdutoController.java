package br.edu.projeto.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import br.edu.projeto.dao.ProdutoDAO;
import br.edu.projeto.model.Produto;

@ViewScoped
@Named
public class ProdutoController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ProdutoDAO produtoDAO;

    private Produto produto;
    private List<Produto> listaProdutos;

    // Método chamado após a construção da classe
    @PostConstruct
    public void init() {
        novoCadastro();
        this.listaProdutos = produtoDAO.listAll();
    }

    // Chamado pelo botão novo
    public void novoCadastro() {
        this.produto = new Produto();
    }

    // Chamado pelo botão remover da tabela
    public void remover() {
        if (this.produtoDAO.delete(this.produto)) {
            this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Produto Removido com sucesso!!!", null));
            this.listaProdutos.remove(this.produto);
        } else
            this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Falha ao Remover Produto", null));
        this.listaProdutos = produtoDAO.listAll();
        this.produto = null;
        PrimeFaces.current().ajax().update("form:messages", "form:dt-produto");
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
}

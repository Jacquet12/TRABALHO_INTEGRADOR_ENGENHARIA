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

import br.edu.projeto.dao.FornecedorDAO;
import br.edu.projeto.dao.FuncionarioDAO;
import br.edu.projeto.model.Fornecedor;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@ViewScoped
@Named
public class FornecedorController  implements Serializable{
    private static final long serialVersionUID = 1L;
    

    @Inject
	private FacesContext facesContext;
	
	@Inject
    private FornecedorDAO fornecedorDAO;
	
	
    private Fornecedor fornecedor;


  

    private List<Fornecedor> listaFornecedores;
	
	//Anotação que força execução do método após o construtor da classe ser executado
    @PostConstruct
    public void init() {
    	//Inicializa elementos importantes
        novoCadastro();
    	this.listaFornecedores = fornecedorDAO.listAll();
    }

        //Chamado pelo botão novo
	public void novoCadastro() {
        this.fornecedor = new Fornecedor();
    }
	
	//Chamado pelo botão remover da tabela
	public void remover() {
		if (this.fornecedorDAO.delete(this.fornecedor)) {
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fornecedor Removido com sucesso!!!", null));
			this.listaFornecedores.remove(this.fornecedor);
		} else 
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Remover fornecedor", null));
		//Após excluir usuário é necessário recarregar lista que popula tabela com os novos dados
		this.listaFornecedores = fornecedorDAO.listAll();
        //Limpa seleção de usuário
		this.fornecedor = null;
        PrimeFaces.current().ajax().update("form:messages", "form:dt-fornecedor");
	}	

    public void salvarAlteracao() {
		if (this.fornecedorDAO.update(this.fornecedor)) {
				PrimeFaces.current().executeScript("PF('fornecedorDialog').hide()");
				PrimeFaces.current().ajax().update("form:dt-fornecedor");
				this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fornecedor Atualizado com sucesso", null));
		} else{
        	this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Atualizar Fornecedor", null));
        }
	}

    public void salvarNovo() {
        if (this.fornecedorDAO.insert(this.fornecedor)) {
            this.listaFornecedores.add(this.fornecedor);
            PrimeFaces.current().executeScript("PF('fornecedorDialog').hide()");
            PrimeFaces.current().ajax().update("form:dt-fornecedor");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fornecedor Cadastrado com sucesso!!!", null));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Cadastrar Fornecedor", null));
        }
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public List<Fornecedor> getListaFornecedores() {
        return listaFornecedores;
    }

    public void setListaFornecedores(List<Fornecedor> listaFornecedores) {
        this.listaFornecedores = listaFornecedores;
    }
   
}

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

import br.edu.projeto.dao.ClienteDAO;
import br.edu.projeto.model.Cliente;


@ViewScoped
@Named
public class ClienteController  implements Serializable{
    private static final long serialVersionUID = 1L;
    

    @Inject
	private FacesContext facesContext;
	
	@Inject
    private ClienteDAO clienteDAO;
	
    private Cliente cliente;
    private List<Cliente> listaClientes;
	


    //Anotação que força execução do método após o construtor da classe ser executado
    @PostConstruct
    public void init() {
    	//Inicializa elementos importantes
        novoCadastro();
    	this.listaClientes = clienteDAO.listAll();
    }

        //Chamado pelo botão novo
	public void novoCadastro() {
        this.cliente = new Cliente();
    }
	
	//Chamado pelo botão remover da tabela
	public void remover() {
		if (this.clienteDAO.delete(this.cliente)) {
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente Removido com sucesso!!!", null));
			this.listaClientes.remove(this.cliente);
		} else 
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Remover cliente", null));
		//Após excluir usuário é necessário recarregar lista que popula tabela com os novos dados
		this.listaClientes = clienteDAO.listAll();
        //Limpa seleção de usuário
		this.cliente = null;
        PrimeFaces.current().ajax().update("form:messages", "form:dt-cliente");
	}	

    public void salvarAlteracao() {
		if (this.clienteDAO.update(this.cliente)) {
				PrimeFaces.current().executeScript("PF('clienteDialog').hide()");
				PrimeFaces.current().ajax().update("form:dt-cliente");
				this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente Atualizado com sucesso", null));
		} else{
        	this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Atualizar Cliente", null));
        }
	}

    public void salvarNovo() {
        if (this.clienteDAO.insert(this.cliente)) {
            this.listaClientes.add(this.cliente);
            PrimeFaces.current().executeScript("PF('clienteDialog').hide()");
            PrimeFaces.current().ajax().update("form:dt-cliente");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente Cadastrado com sucesso!!!", null));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Cadastrar Cliente", null));
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    	
    public ClienteDAO getClienteDAO() {
        return clienteDAO;
    }

    public void setClienteDAO(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }
}


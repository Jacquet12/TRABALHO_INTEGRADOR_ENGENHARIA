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

import br.edu.projeto.dao.ClienteDAO;
import br.edu.projeto.model.Cliente;
import br.edu.projeto.model.Produto;

@ViewScoped
@Named
public class ClienteController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ClienteDAO clienteDAO;

    private Cliente cliente;
    private List<Cliente> listaClientes;
    public String getCpfClienteParaExclusao() {
        return cpfClienteParaExclusao;
    }

    public void setCpfClienteParaExclusao(String cpfClienteParaExclusao) {
        this.cpfClienteParaExclusao = cpfClienteParaExclusao;
    }

    private String cpfClienteParaExclusao;

    @PostConstruct
    public void init() {
        novoCadastro();
        this.listaClientes = clienteDAO.listAll();
    }

    public void novoCadastro() {
        this.cliente = new Cliente();
    }

    private String errorMessage;

    // Getter e Setter da mensagem de erro
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void remover() throws SQLException {
        String cpfCliente = this.cpfClienteParaExclusao;
        Cliente clienteParaExcluir = clienteDAO.buscarPorCPF(cpfCliente);

        if (clienteParaExcluir == null) {
            // Produto não encontrado com o código informado
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Cliente não encontrado com o cpf informado.", null));
        } else {
            try {
                if (this.clienteDAO.delete(clienteParaExcluir)) {
                    this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Cliente Removido com sucesso!!!", null));
                    this.listaClientes.remove(clienteParaExcluir);
                } else {
                    this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Falha ao Remover Cliente!", null));
                }
            } catch (Exception e) {
                // Captura a mensagem de erro da exceção
                errorMessage = e.getMessage();
            }
        }

        PrimeFaces.current().ajax().update("form:messages", "form:dt-cliente", "form:error");

    }


    public void salvarAlteracao() {
        if (this.clienteDAO.update(this.cliente)) {
            PrimeFaces.current().executeScript("PF('clienteDialog').hide()");
            PrimeFaces.current().ajax().update("form:dt-cliente");
            this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente Atualizado com sucesso", null));
        } else {
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

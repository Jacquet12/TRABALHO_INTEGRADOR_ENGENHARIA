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

import br.edu.projeto.dao.FuncionarioDAO;
import br.edu.projeto.model.Funcionario;

//Escopo do objeto da classe (Bean)
//ApplicationScoped é usado quando o objeto é único na aplicação (compartilhado entre usuários), permanece ativo enquanto a aplicação estiver ativa
//SessionScoped é usado quando o objeto é único por usuário, permanece ativo enquanto a sessão for ativa
//ViewScoped é usado quando o objeto permanece ativo enquanto não houver um redirect (acesso a nova página)
//RequestScoped é usado quando o objeto só existe naquela requisição específica
//Quanto maior o escopo, maior o uso de memória no lado do servidor (objeto permanece ativo por mais tempo)
//Escopos maiores que Request exigem que classes sejam serializáveis assim como todos os seus atributos (recurso de segurança)
@ViewScoped
//Torna classe disponível na camada de visão (html) - são chamados de Beans ou ManagedBeans (gerenciados pelo JSF/EJB)
@Named
public class FuncionarioController implements Serializable {
	private static final long serialVersionUID = 1L;

	//Anotação que marca atributo para ser gerenciado pelo CDI
	//O CDI criará uma instância do objeto automaticamente quando necessário
	@Inject
	private FacesContext facesContext;
	
	@Inject
    private FuncionarioDAO funcionarioDAO;
	
	
    private Funcionario funcionario;
    private List<Funcionario> listaFuncionarios;
	
	//Anotação que força execução do método após o construtor da classe ser executado
    @PostConstruct
    public void init() {
    	//Inicializa elementos importantes
    	this.listaFuncionarios = funcionarioDAO.listAll();
    }
	
    //Chamado pelo botão novo
	public void novoCadastro() {
        this.funcionario = new Funcionario();
    }
	
	//Chamado pelo botão remover da tabela
	// public void remover() {
	// 	if (this.funcionarioDAO.delete(this.funcionario)) {
	// 		this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente Removido com sucesso!!!", null));
	// 		this.listaCliente.remove(this.cliente);
	// 	} else 
			//this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Remover cliente", null));
		//Após excluir usuário é necessário recarregar lista que popula tabela com os novos dados
		//this.listaCamiseta = camisetaDAO.listAll();
        //Limpa seleção de usuário
	// 	this.cliente = null;
    //     PrimeFaces.current().ajax().update("form:messages", "form:dt-clientes");
	// }	
	
	//Chamado ao salvar cadastro de usuário (novo)
	public void salvarNovo() {
		if (this.funcionarioDAO.insert(this.funcionario)) {
            this.listaFuncionarios.add(this.funcionario);
            PrimeFaces.current().executeScript("PF('funcionarioDialog').hide()");
            PrimeFaces.current().ajax().update("form:dt-funcionario");
            this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Funcionario Criado com sucesso!!!", null));
		} else{
        	this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Criar Funcioario", null));
        }
	}
	
	
	public void salvarAlteracao() {
		if (this.funcionarioDAO.update(this.funcionario)) {
				PrimeFaces.current().executeScript("PF('clienteDialog').hide()");
				PrimeFaces.current().ajax().update("form:dt-cliente");
				this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente Atualizado com sucesso", null));
		} else{
        	this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Atualizar Cliente", null));
        }
	}
	
	//GETs e SETs
	//GETs são necessários para elementos visíveis em tela
	//SETs são necessários para elementos que serão editados em tela
	public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

	public List<Funcionario> getListaFuncionarios() {
        return listaFuncionarios;
    }

    public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
        this.listaFuncionarios = listaFuncionarios;
    }
}
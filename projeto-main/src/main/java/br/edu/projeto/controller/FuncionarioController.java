package br.edu.projeto.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
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
	

	private String senha;
	private String email;
	
    private Funcionario funcionario;
    private List<Funcionario> listaFuncionarios;
	
	//Anotação que força execução do método após o construtor da classe ser executado
    @PostConstruct
    public void init() {
    	//Inicializa elementos importantes
    	this.listaFuncionarios = funcionarioDAO.listAll();
		funcionario = new Funcionario();
    }
	
    //Chamado pelo botão novo
	public void novoCadastro() {
        this.funcionario = new Funcionario();
    }
	
	//Chamado pelo botão remover da tabela
	public void remover() {
		if (this.funcionarioDAO.delete(this.funcionario)) {
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Funcionario Removido com sucesso!!!", null));
			this.listaFuncionarios.remove(this.funcionario);
		} else 
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Remover funcionario", null));
		//Após excluir usuário é necessário recarregar lista que popula tabela com os novos dados
		this.listaFuncionarios = funcionarioDAO.listAll();
        //Limpa seleção de usuário
		this.funcionario = null;
        PrimeFaces.current().ajax().update("form:messages", "form:dt-funcionario");
	}	
	
	//Chamado ao salvar cadastro de usuário (novo)
	public void salvarNovo() {
		if (this.funcionarioDAO.insert(this.funcionario)) {
			this.listaFuncionarios.add(this.funcionario);
			PrimeFaces.current().executeScript("PF('funcionarioDialog').hide()");
			PrimeFaces.current().ajax().update("form:dt-funcionario");
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Funcionario Criado com sucesso!!!", null));

			// Redireciona o usuário para a página de login
			try {
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				externalContext.redirect("login_funcionario.xhtml"); // Substitua "pagina_de_login.xhtml" pelo nome correto da sua página de login
			} catch (IOException e) {
				// Trate qualquer exceção que possa ocorrer ao redirecionar o usuário
				e.printStackTrace();
			}
		} else {
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Falha ao Criar Funcioario", null));
		}
	}
	
	
	public void salvarAlteracao() {
		if (this.funcionarioDAO.update(this.funcionario)) {
				PrimeFaces.current().executeScript("PF('funcionarioDialog').hide()");
				PrimeFaces.current().ajax().update("form:dt-funcionario");
				this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Funcionario Atualizado com sucesso", null));
		} else{
        	this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Atualizar Funcionario", null));
        }
	}

	public void fazerLogin() {
		Funcionario funcionarioLogado = funcionarioDAO.buscarFuncionarioPorEmailSenha(email, senha);
		if (funcionarioLogado != null) {
			
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("DashboardAdm.xhtml");
			} catch (IOException e) {
				// Tratar qualquer exceção de redirecionamento
				e.printStackTrace();
			}
		} else {
			// Login inválido
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "O seu login esta Inválido", "Usuário ou senha incorretos");
			FacesContext.getCurrentInstance().addMessage(null, message);
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
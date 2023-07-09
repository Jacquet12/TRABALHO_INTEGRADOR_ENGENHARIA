package br.edu.projeto.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.projeto.model.Funcionario;

@Named
@RequestScoped
public class LoginController {

    @Inject
    private javax.faces.context.FacesContext facesContext;

    @Inject
    private SecurityContext securityContext;

    private Funcionario funcionario;

	@PostConstruct
    public void init() {
    	//Inicializa elementos importantes
        this.funcionario = new Funcionario();
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public void login() throws IOException {
        if (facesContext.getExternalContext().getAuthType() != null) {
            facesContext.addMessage(null, new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "Existe um usuário autenticado! Use a opção logout primeiro.", ""));
        } else {
            Credential credential = new UsernamePasswordCredential(funcionario.getEmail(), new Password(funcionario.getSenha()));
            AuthenticationStatus status = securityContext.authenticate(
                    (HttpServletRequest) facesContext.getExternalContext().getRequest(),
                    (HttpServletResponse) facesContext.getExternalContext().getResponse(),
                    AuthenticationParameters.withParams().credential(credential));
            if (status.equals(AuthenticationStatus.SUCCESS)) {
                facesContext.getExternalContext().redirect("cadastro_funcionario.xhtml");
            } else if (status.equals(AuthenticationStatus.SEND_FAILURE)) {
                funcionario = new Funcionario();
                facesContext.addMessage(null, new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR, "Login Inválido!", "Usuário ou senha incorretos."));
            }
        }
    }

    public void logout() throws IOException {
        facesContext.getExternalContext().invalidateSession();
        facesContext.getExternalContext().redirect("logout.xhtml");
    }

}

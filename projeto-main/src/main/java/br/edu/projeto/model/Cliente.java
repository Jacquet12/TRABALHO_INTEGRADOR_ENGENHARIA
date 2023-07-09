package br.edu.projeto.model;
import java.util.Date;

public class Cliente {
    private String nome;
    private String sobrenome;
    private Date dataColuna;
    private String email;
    private String funcionario_cpf; 

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getSobrenome(){
        return sobrenome;
    }
    public void setSobrenome(String sobrenome){
        this. sobrenome=sobrenome;
    }

    public Date dataColuna(){
        return dataColuna;
    }
    public void setDataColuna(Date dataColuna){
        this.dataColuna=dataColuna;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public String getFuncionario_cpf(){
        return funcionario_cpf;
    }
    public void setFuncionario_cpf(String funcionario_cpf){
        this.funcionario_cpf=funcionario_cpf;
    }


}

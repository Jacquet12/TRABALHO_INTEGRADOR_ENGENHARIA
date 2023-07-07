package br.edu.projeto.model;

public class Fornecedor {
    private String cnpj;
    private String nomeFornecedor;
    private String tipoDeFornecedor;
    private String contato;
    private String pais;
    private String funcionarioCpf;
    private String tipoFuncionario;


    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public String getNomeFornecedor() {
        return nomeFornecedor;
    }
    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }
    public String getTipoDeFornecedor() {
        return tipoDeFornecedor;
    }
    public void setTipoDeFornecedor(String tipoDeFornecedor) {
        this.tipoDeFornecedor = tipoDeFornecedor;
    }
    public String getContato() {
        return contato;
    }
    public void setContato(String contato) {
        this.contato = contato;
    }
    public String getPais() {
        return pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }
    public String getFuncionarioCpf() {
        return funcionarioCpf;
    }
    public void setFuncionarioCpf(String funcionarioCpf) {
        this.funcionarioCpf = funcionarioCpf;
    }
    public String getTipoFuncionario() {
        return tipoFuncionario;
    }
    public void setTipoFuncionario(String tipoFuncionario) {
        this.tipoFuncionario = tipoFuncionario;
    }
    public void setEmail(String string) {
    }
}

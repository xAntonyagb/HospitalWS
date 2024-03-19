package br.unipar.hospitalws.models;

public class PacienteModel {
    private String nome;
    private String gmail;
    private String telefone;
    private EnderecoModel endereco;
    private String cpf;

    public PacienteModel(String nome, String gmail, String telefone, EnderecoModel endereco, String cpf) {
        this.nome = nome;
        this.gmail = gmail;
        this.telefone = telefone;
        this.endereco = endereco;
        this.cpf = cpf;
    }

    public PacienteModel() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public EnderecoModel getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoModel endereco) {
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "PacienteModel{" + "nome=" + nome + ", gmail=" + gmail + ", telefone=" + telefone + ", endereco=" + endereco + ", cpf=" + cpf + '}';
    }
    
}

package br.unipar.hospitalws.models;

import br.unipar.hospitalws.enums.EspecialidadeEnum;

public class MedicoModel {
    private String nome;
    private String gmail;
    private String telefone;
    private int CRM;
    private EspecialidadeEnum especialidade;
    private EnderecoModel endereco;
    private String cpf;

    public MedicoModel() {
    }

    public MedicoModel(String nome, String gmail, String telefone, int CRM, EspecialidadeEnum especialidade, EnderecoModel endereco, String cpf) {
        this.nome = nome;
        this.gmail = gmail;
        this.telefone = telefone;
        this.CRM = CRM;
        this.especialidade = especialidade;
        this.endereco = endereco;
        this.cpf = cpf;
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

    public int getCRM() {
        return CRM;
    }

    public void setCRM(int CRM) {
        this.CRM = CRM;
    }

    public EspecialidadeEnum getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(EspecialidadeEnum especialidade) {
        this.especialidade = especialidade;
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
        return "MedicoModel{" + "nome=" + nome + ", gmail=" + gmail + ", telefone=" + telefone + ", CRM=" + CRM + ", especialidade=" + especialidade + ", endereco=" + endereco + ", cpf=" + cpf + '}';
    }

    
}

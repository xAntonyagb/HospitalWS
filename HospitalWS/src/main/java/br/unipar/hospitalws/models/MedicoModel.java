package br.unipar.hospitalws.models;

import br.unipar.hospitalws.enums.EspecialidadeEnum;

public class MedicoModel extends PessoaModel{
    private int medicoId;
    private String CRM;
    private EspecialidadeEnum especialidade;
    private boolean ativo;

    public MedicoModel() {
    }

    public MedicoModel(int medicoId, String CRM, EspecialidadeEnum especialidade, boolean ativo, String nome, String gmail, String telefone, EnderecoModel endereco, String cpf, int pessoaId) {
        super(nome, gmail, telefone, endereco, cpf, pessoaId);
        this.medicoId = medicoId;
        this.CRM = CRM;
        this.especialidade = especialidade;
        this.ativo = ativo;
    }
    
    public MedicoModel(int medicoId) {
        this.medicoId = medicoId;
    }

    public String getCRM() {
        return CRM;
    }

    public void setCRM(String CRM) {
        this.CRM = CRM;
    }

    public EspecialidadeEnum getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(EspecialidadeEnum especialidade) {
        this.especialidade = especialidade;
    }

    public int getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(int medicoId) {
        this.medicoId = medicoId;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "MedicoModel{" + "medicoId=" + medicoId + ", CRM=" + CRM + ", especialidade=" + especialidade + ", ativo=" + ativo + '}';
    }

}

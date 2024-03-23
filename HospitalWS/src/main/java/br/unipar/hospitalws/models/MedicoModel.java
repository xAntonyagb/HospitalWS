package br.unipar.hospitalws.models;

import br.unipar.hospitalws.enums.EspecialidadeEnum;

public class MedicoModel extends PessoaModel{
    private int medicoId;
    private int CRM;
    private EspecialidadeEnum especialidade;
    private boolean ativo;

    public MedicoModel() {
    }

    public MedicoModel(int medicoId, int CRM, EspecialidadeEnum especialidade, boolean ativo, String nome, String gmail, String telefone, EnderecoModel endereco, String cpf, int pessoaId) {
        super(nome, gmail, telefone, endereco, cpf, pessoaId);
        this.medicoId = medicoId;
        this.CRM = CRM;
        this.especialidade = especialidade;
        this.ativo = ativo;
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

    public int getMedicoId() {
        return medicoId;
    }

    public void setmedicoId(int medicoId) {
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

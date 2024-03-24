package br.unipar.hospitalws.models;

public class PacienteModel extends PessoaModel{
    
    private int pacienteId;
    private boolean ativo;

    public PacienteModel() { }

    public PacienteModel(int pacienteId, String nome, String gmail, String telefone, EnderecoModel endereco, String cpf, int pessoaId, boolean ativo) {
        super(nome, gmail, telefone, endereco, cpf, pessoaId);
        this.pacienteId = pessoaId;
        this.ativo = ativo;
    }
    
    public PacienteModel(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public int getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "PacienteModel{" + "pacienteId=" + pacienteId + ", ativo=" + ativo + super.toString() + '}';
    }

}

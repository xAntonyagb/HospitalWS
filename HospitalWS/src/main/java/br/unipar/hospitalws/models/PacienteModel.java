package br.unipar.hospitalws.models;

public class PacienteModel extends PessoaModel{
    
    private int pacienteId;

    public PacienteModel(int pacienteId, String nome, String gmail, String telefone, EnderecoModel endereco, String cpf, int pessoaId) {
        super(nome, gmail, telefone, endereco, cpf, pessoaId);
        this.pacienteId = pessoaId;
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

    @Override
    public String toString() {
        return "PacienteModel{" + "pacienteId=" + pacienteId + super.toString() + '}';
    }

}

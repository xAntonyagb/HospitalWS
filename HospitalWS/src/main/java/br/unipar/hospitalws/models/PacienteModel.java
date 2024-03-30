package br.unipar.hospitalws.models;

import br.unipar.hospitalws.DTO.PacienteDTO;

public class PacienteModel extends PessoaModel{
    
    private int idPaciente;
    private boolean ativo;

    public PacienteModel() { }

    public PacienteModel(int idPaciente, String nome, String gmail, String telefone, EnderecoModel endereco, String cpf, int pessoaId, boolean ativo) {
        super(nome, gmail, telefone, endereco, cpf, pessoaId);
        this.idPaciente = idPaciente;
        this.ativo = ativo;
    }
    
    public static PacienteModel pacienteModelMapper(PacienteDTO pacienteDTO) {
        PacienteModel pacienteModel = new PacienteModel();
        EnderecoModel endereco  = new EnderecoModel();
        
        endereco.setBairro(pacienteDTO.getBairro());
        endereco.setCEP(pacienteDTO.getCEP());
        endereco.setUF(pacienteDTO.getUF());
        endereco.setNumero(pacienteDTO.getNumero());
        endereco.setLogradouro(pacienteDTO.getLogradouro());
        endereco.setComplemento(pacienteDTO.getComplemento());
        endereco.setCidade(pacienteDTO.getCidade());
        pacienteModel.setEndereco(endereco);
        
        pacienteModel.setIdPaciente(pacienteDTO.getId());
        pacienteModel.setAtivo(pacienteDTO.isAtivo());
        pacienteModel.setCpf(pacienteDTO.getCpf());
        pacienteModel.setGmail(pacienteDTO.getGmail());
        pacienteModel.setNome(pacienteDTO.getNome());
        pacienteModel.setTelefone(pacienteDTO.getTelefone());
        
        return pacienteModel;
    }
    
    //Getters e setters
    public PacienteModel(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "PacienteModel{" + "idPaciente=" + idPaciente + ", ativo=" + ativo + super.toString() + '}';
    }

}

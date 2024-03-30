package br.unipar.hospitalws.models;

import br.unipar.hospitalws.DTO.MedicoDTO;
import br.unipar.hospitalws.enums.EspecialidadeEnum;

public class MedicoModel extends PessoaModel{
    private int idMedico;
    private String CRM;
    private EspecialidadeEnum especialidade;
    private boolean ativo;

    public MedicoModel() {
    }

    public MedicoModel(int idMedico, String CRM, EspecialidadeEnum especialidade, boolean ativo, String nome, String gmail, String telefone, EnderecoModel endereco, String cpf, int pessoaId) {
        super(nome, gmail, telefone, endereco, cpf, pessoaId);
        this.idMedico = idMedico;
        this.CRM = CRM;
        this.especialidade = especialidade;
        this.ativo = ativo;
    }
    
    public static MedicoModel medicoModelMapper(MedicoDTO medicoDTO) {
        MedicoModel medicoModel = new MedicoModel();
        EnderecoModel endereco = new EnderecoModel();
        
        endereco.setBairro(medicoDTO.getBairro());
        endereco.setCEP(medicoDTO.getCEP());
        endereco.setUF(medicoDTO.getUF());
        endereco.setNumero(medicoDTO.getNumero());
        endereco.setLogradouro(medicoDTO.getLogradouro());
        endereco.setComplemento(medicoDTO.getComplemento());
        endereco.setCidade(medicoDTO.getCidade());
        medicoModel.setEndereco(endereco);
        
        medicoModel.setIdMedico(medicoDTO.getId());
        medicoModel.setAtivo(medicoDTO.isAtivo());
        medicoModel.setCRM(medicoDTO.getCRM());
        medicoModel.setEspecialidade(medicoDTO.getEspecialidade());
        medicoModel.setCpf(medicoDTO.getCpf());
        medicoModel.setGmail(medicoDTO.getGmail());
        medicoModel.setNome(medicoDTO.getNome());
        medicoModel.setTelefone(medicoDTO.getTelefone());
        
        return medicoModel;
    }
    
    //Getters e setters
    public MedicoModel(int idMedico) {
        this.idMedico = idMedico;
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

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "MedicoModel{" + "idMedico=" + idMedico + ", CRM=" + CRM + ", especialidade=" + especialidade + ", ativo=" + ativo + '}';
    }

}

package br.unipar.hospitalws.DTO;

import br.unipar.hospitalws.models.EnderecoModel;
import br.unipar.hospitalws.models.PacienteModel;

public class PacienteDTO {
    private int id;
    private boolean ativo;
    
    private String nome;
    private String gmail;
    private String telefone;
    private String cpf;
    
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String UF;
    private String CEP;

    public PacienteDTO(int id, boolean ativo, String nome, String gmail, String telefone, String cpf, String logradouro, String numero, String complemento, String bairro, String cidade, String UF, String CEP) {
        this.id = id;
        this.ativo = ativo;
        this.nome = nome;
        this.gmail = gmail;
        this.telefone = telefone;
        this.cpf = cpf;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.UF = UF;
        this.CEP = CEP;
    }

    public static PacienteDTO pacienteDTOMapper(PacienteModel pacienteModel) {
        if (pacienteModel == null) {
            return null;
        }

        PacienteDTO pacienteDTO = new PacienteDTO();
        EnderecoModel endereco = pacienteModel.getEndereco();

        if(endereco != null) {
            pacienteDTO.setBairro(endereco.getBairro());
            pacienteDTO.setCEP(endereco.getCEP());
            pacienteDTO.setUF(endereco.getUF());
            pacienteDTO.setNumero(endereco.getNumero());
            pacienteDTO.setLogradouro(endereco.getLogradouro());
            pacienteDTO.setComplemento(endereco.getComplemento());
            pacienteDTO.setCidade(endereco.getCidade());
        }

        pacienteDTO.setId(pacienteModel.getIdPaciente());
        pacienteDTO.setAtivo(pacienteModel.isAtivo());
        pacienteDTO.setCpf(pacienteModel.getCpf());
        pacienteDTO.setGmail(pacienteModel.getGmail());
        pacienteDTO.setNome(pacienteModel.getNome());
        pacienteDTO.setTelefone(pacienteModel.getTelefone());

        return pacienteDTO;
    }
    
    //Getters e setters
    public PacienteDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUF() {
        return UF;
    }

    public void setUF(String UF) {
        this.UF = UF;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }
    
}

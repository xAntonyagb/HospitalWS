package br.unipar.hospitalws.DTO;

import br.unipar.hospitalws.enums.EspecialidadeEnum;
import br.unipar.hospitalws.models.EnderecoModel;
import br.unipar.hospitalws.models.MedicoModel;

public class MedicoDTO {
    private int id;
    private String CRM;
    private EspecialidadeEnum especialidade;
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

    public MedicoDTO(int id, String CRM, EspecialidadeEnum especialidade, boolean ativo, String nome, String gmail, String telefone, String cpf, String logradouro, String numero, String complemento, String bairro, String cidade, String UF, String CEP) {
        this.id = id;
        this.CRM = CRM;
        this.especialidade = especialidade;
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

    public MedicoDTO() {
    }

    
    public static MedicoDTO medicoDTOMapper(MedicoModel medicoModel) {
        if (medicoModel == null) {
            return null;
        }

        MedicoDTO medicoDTO = new MedicoDTO();
        EnderecoModel endereco = medicoModel.getEndereco();
        
        if(endereco != null) {
            medicoDTO.setBairro(endereco.getBairro());
            medicoDTO.setCEP(endereco.getCEP());
            medicoDTO.setUF(endereco.getUF());
            medicoDTO.setNumero(endereco.getNumero());
            medicoDTO.setLogradouro(endereco.getLogradouro());
            medicoDTO.setComplemento(endereco.getComplemento());
            medicoDTO.setCidade(endereco.getCidade());
        }
        
        medicoDTO.setId(medicoModel.getIdMedico());
        medicoDTO.setAtivo(medicoModel.isAtivo());
        medicoDTO.setCRM(medicoModel.getCRM());
        medicoDTO.setEspecialidade(medicoModel.getEspecialidade());
        medicoDTO.setCpf(medicoModel.getCpf());
        medicoDTO.setGmail(medicoModel.getGmail());
        medicoDTO.setNome(medicoModel.getNome());
        medicoDTO.setTelefone(medicoModel.getTelefone());

        return medicoDTO;
    }
    
    //Getter e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

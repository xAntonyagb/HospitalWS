package br.unipar.hospitalws.DTO;

import br.unipar.hospitalws.models.EnderecoModel;

public class EnderecoDTO {
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String UF;
    private String CEP;
    private int id;

    public EnderecoDTO() {
    }

    public EnderecoDTO(String logradouro, String numero, String complemento, String bairro, String cidade, String UF, String CEP, int id) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.UF = UF;
        this.CEP = CEP;
        this.id = id;
    }
    
    
    public static EnderecoDTO enderecoDTOMapper(EnderecoModel enderecoModel) {
        if (enderecoModel == null) {
            return null;
        }
        
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setBairro(enderecoModel.getBairro());
        enderecoDTO.setCEP(enderecoModel.getCEP());
        enderecoDTO.setCidade(enderecoModel.getCidade());
        enderecoDTO.setComplemento(enderecoModel.getComplemento());
        enderecoDTO.setId(enderecoModel.getIdEndereco());
        enderecoDTO.setLogradouro(enderecoModel.getLogradouro());
        enderecoDTO.setNumero(enderecoModel.getNumero());
        enderecoDTO.setUF(enderecoModel.getUF());
        
        return enderecoDTO;
    }
    
    //Getters e setters
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "EnderecoDTO{" + "logradouro=" + logradouro + ", numero=" + numero + ", complemento=" + complemento + ", bairro=" + bairro + ", cidade=" + cidade + ", UF=" + UF + ", CEP=" + CEP + ", id=" + id + '}';
    }
    
}

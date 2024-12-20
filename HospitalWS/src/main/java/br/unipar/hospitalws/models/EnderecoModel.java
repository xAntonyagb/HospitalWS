package br.unipar.hospitalws.models;

import br.unipar.hospitalws.DTO.EnderecoDTO;

public class EnderecoModel {
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String UF;
    private String CEP;
    private int idEndereco;
    
    public EnderecoModel() { }

    public EnderecoModel(String logradouro, String numero, String complemento, String bairro, String cidade, String UF, String CEP, int idEndereco) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.UF = UF;
        this.CEP = CEP;
        this.idEndereco = idEndereco;
    }
    
     public static EnderecoModel enderecoModelMapper(EnderecoDTO enderecoDTO) {
         if (enderecoDTO == null) {
            return null;
        }
         
        EnderecoModel enderecoModel = new EnderecoModel();
        enderecoModel.setBairro(enderecoDTO.getBairro());
        enderecoModel.setCEP(enderecoDTO.getCEP());
        enderecoModel.setCidade(enderecoDTO.getCidade());
        enderecoModel.setComplemento(enderecoDTO.getComplemento());
        enderecoModel.setIdEndereco(enderecoDTO.getId());
        enderecoModel.setLogradouro(enderecoDTO.getLogradouro());
        enderecoModel.setNumero(enderecoDTO.getNumero());
        enderecoModel.setUF(enderecoDTO.getUF());
        
        return enderecoModel;
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

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    @Override
    public String toString() {
        return "EnderecoModel{" + "logradouro=" + logradouro + ", numero=" + numero + ", complemento=" + complemento + ", bairro=" + bairro + ", cidade=" + cidade + ", UF=" + UF + ", CEP=" + CEP + ", idEndereco=" + idEndereco + '}';
    }

    
}

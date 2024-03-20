package br.unipar.hospitalws.services;

import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.infrastructure.ConstructionFactory;
import br.unipar.hospitalws.models.EnderecoModel;
import br.unipar.hospitalws.repositories.EnderecoRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class EnderecoService {
    
    private final Connection connection = new ConstructionFactory().getConnection();
    private final EnderecoRepository pacienteRepository = new EnderecoRepository(connection);
    
    public static void validaEndereco(EnderecoModel enderecoModel) {
        if(enderecoModel.getBairro().length() < 0
                || enderecoModel.getBairro().isEmpty()
                || enderecoModel.getBairro() == null){
            throw new ValidationException("Bairro inválido! Porfavor informe algum bairro");
        }
        if(enderecoModel.getComplemento().length() < 0
                || enderecoModel.getComplemento().isEmpty()
                || enderecoModel.getComplemento() == null){
            throw new ValidationException("Complemento inválido! Porfavor informe algum complemento");
        }
        if(enderecoModel.getLogradouro().length() < 0
                || enderecoModel.getLogradouro().isEmpty()
                || enderecoModel.getLogradouro() == null){
            throw new ValidationException("Logradouro inválido! Porfavor informe algum logradouro");
        }
        if(enderecoModel.getUF().length() < 1
                || enderecoModel.getUF().isEmpty()
                || enderecoModel.getUF() == null){
            throw new ValidationException("UF inválida! Porfavor informe alguma UF");
        }
        if(enderecoModel.getNumero() == 0){
            throw new ValidationException("Número inválido! Porfavor informe algum número");
        }
        if(enderecoModel.getCidade().length() < 0
                || enderecoModel.getCidade().isEmpty()
                || enderecoModel.getCidade() == null){
            throw new ValidationException("Cidade inválida! Porfavor informe alguma cidade");
        }
        if(enderecoModel.getCEP().length() < 0
                || enderecoModel.getCEP().isEmpty()
                || enderecoModel.getCEP() == null){
            throw new ValidationException("CEP inválido! Porfavor informe algum CEP");
        }
    }
    
    
    public EnderecoModel insertEndereco(EnderecoModel EnderecoModel) throws SQLException {
        return pacienteRepository.insertEndereco(EnderecoModel);
    }
    
    public EnderecoModel getEnderecoById(int id) throws SQLException{
        return pacienteRepository.getEnderecoById(id);
    }
    
    public ArrayList<EnderecoModel> getAllEnderecos() {
        return pacienteRepository.getAllEnderecos();
    }
    
    public EnderecoModel updateEndereco(EnderecoModel EnderecoModel) throws SQLException{
        return pacienteRepository.updateEndereco(EnderecoModel);
    }
    
    public void deleteEnderecoById(int id) throws SQLException{
        pacienteRepository.deleteEnderecoById(id);
    }
}

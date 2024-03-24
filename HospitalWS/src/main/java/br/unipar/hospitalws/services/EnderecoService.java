package br.unipar.hospitalws.services;

import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.infrastructure.ConstructionFactory;
import br.unipar.hospitalws.models.EnderecoModel;
import br.unipar.hospitalws.repositories.EnderecoRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class EnderecoService {
    
    private ConstructionFactory constructionFactory = new ConstructionFactory();
    private Connection connection = null;
    private EnderecoRepository enderecoRepository = null;
    
    public static void validaEndereco(EnderecoModel enderecoModel) {
        if(enderecoModel.getBairro().length() < 0
                || enderecoModel.getBairro().isEmpty()
                || enderecoModel.getBairro() == null){
            throw new ValidationException("Bairro inválido! Porfavor informe algum bairro");
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
    
    
    public EnderecoModel insertEndereco(EnderecoModel enderecoModel) {
        validaEndereco(enderecoModel);
        EnderecoModel retorno = new EnderecoModel();
        
        try {
        connection = constructionFactory.getConnection();
        enderecoRepository = new EnderecoRepository(connection);
        
        retorno = enderecoRepository.insertEndereco(enderecoModel);
        connection.close();
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        
        return retorno;
    }
    
    public EnderecoModel getEnderecoById(int id) {
        EnderecoModel retorno = new EnderecoModel();
        
        try {
            connection = constructionFactory.getConnection();
            enderecoRepository = new EnderecoRepository(connection);

            retorno = enderecoRepository.getEnderecoById(id);
            connection.close();
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        
        return retorno;
    }
    
    public ArrayList<EnderecoModel> getAllEnderecos() {
        ArrayList<EnderecoModel> retorno = new ArrayList<EnderecoModel>();
        
        try {
            connection = constructionFactory.getConnection();
            enderecoRepository = new EnderecoRepository(connection);

            retorno = enderecoRepository.getAllEnderecos();
            connection.close();
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        
        return retorno;
    }
    
    public EnderecoModel updateEndereco(EnderecoModel enderecoModel) {
        validaEndereco(enderecoModel);
        EnderecoModel retorno = new EnderecoModel();
        
        try {
            connection = constructionFactory.getConnection();
            enderecoRepository = new EnderecoRepository(connection);

            retorno = enderecoRepository.updateEndereco(enderecoModel);
            connection.close();
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        
        return retorno;
    }
    
    public void deleteEnderecoById(int id) {
        try {
            connection = constructionFactory.getConnection();
            enderecoRepository = new EnderecoRepository(connection);

            enderecoRepository.deleteEnderecoById(id);
            connection.close();
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
    }
}

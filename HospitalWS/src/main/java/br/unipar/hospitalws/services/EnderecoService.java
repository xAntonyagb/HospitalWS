package br.unipar.hospitalws.services;

import br.unipar.hospitalws.DTO.EnderecoDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.infrastructure.ConnectionFactory;
import br.unipar.hospitalws.models.EnderecoModel;
import br.unipar.hospitalws.repositories.EnderecoRepository;
import br.unipar.hospitalws.utils.StringFormatterUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class EnderecoService {
    
    private ConnectionFactory connectionFactory = new ConnectionFactory();
    private Connection connection = null;
    private EnderecoRepository enderecoRepository = null;
    
    public static void validaEndereco(EnderecoModel enderecoModel) {
        if(enderecoModel.getBairro() == null){
            throw new ValidationException("Bairro inválido! Porfavor informe algum bairro");
        }
        if(enderecoModel.getLogradouro() == null){
            throw new ValidationException("Logradouro inválido! Porfavor informe algum logradouro");
        }
        if(enderecoModel.getUF() == null){
            throw new ValidationException("UF inválida! Porfavor informe alguma UF");
        }
        if(enderecoModel.getCidade() == null){
            throw new ValidationException("Cidade inválida! Porfavor informe alguma cidade");
        }
        if(enderecoModel.getCEP() == null){
            throw new ValidationException("CEP inválido! Porfavor informe algum CEP");
        }
        if(enderecoModel.getCEP().length() > 10) {
            throw new ValidationException("CEP inválido! Informe um CEP de no máximo 10 digitos");
        }
    }
    
    private EnderecoModel ajustaEndereco(EnderecoDTO endereco) {
        endereco.setBairro(StringFormatterUtil.ajustaNormalInput(endereco.getBairro()));
        endereco.setLogradouro(StringFormatterUtil.ajustaNormalInput(endereco.getLogradouro()));
        endereco.setUF(StringFormatterUtil.ajustaNormalInput(endereco.getUF()));
        endereco.setCidade(StringFormatterUtil.ajustaNormalInput(endereco.getCidade()));
        endereco.setCEP(StringFormatterUtil.ajustaNormalInput(endereco.getCEP()));
        endereco.setComplemento(StringFormatterUtil.ajustaNormalInput(endereco.getComplemento()));
        endereco.setNumero(StringFormatterUtil.ajustaNumberInput(endereco.getNumero()));
        
        return EnderecoModel.enderecoModelMapper(endereco);
    }
    
    
    public EnderecoDTO insertEndereco(EnderecoDTO enderecoDTO) {
        EnderecoModel enderecoModel = ajustaEndereco(enderecoDTO);
        validaEndereco(ajustaEndereco(enderecoDTO));
        
        try {
        connection = connectionFactory.getConnection();
            connection.setAutoCommit(false);
        enderecoRepository = new EnderecoRepository(connection);
        
        enderecoModel = enderecoRepository.insertEndereco(enderecoModel);
        } 
        catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        finally {
            ConnectionFactory.closeConnection();
        }
        
        return EnderecoDTO.enderecoDTOMapper(enderecoModel);
    }
    
    public EnderecoDTO getEnderecoById(int id) {
        EnderecoModel retorno = new EnderecoModel();
        
        try {
            connection = connectionFactory.getConnection();
            connection.setAutoCommit(false);
            enderecoRepository = new EnderecoRepository(connection);

            retorno = enderecoRepository.getEnderecoById(id);
        } 
        catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        } 
        finally {
            if(connection != null)
                ConnectionFactory.closeConnection();
        }
        
        return EnderecoDTO.enderecoDTOMapper(retorno);
    }
    
    public ArrayList<EnderecoDTO> getAllEnderecos() {
        ArrayList<EnderecoModel> consulta = new ArrayList<EnderecoModel>();
        ArrayList<EnderecoDTO> retorno = new ArrayList<EnderecoDTO>();
        
        try {
            connection = connectionFactory.getConnection();
            connection.setAutoCommit(false);
            enderecoRepository = new EnderecoRepository(connection);

            consulta = enderecoRepository.getAllEnderecos();
            
            for(EnderecoModel enderecoModel : consulta) {
                retorno.add(EnderecoDTO.enderecoDTOMapper(enderecoModel));
            }
            
        } 
        catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        } 
        finally {
            ConnectionFactory.closeConnection();
        }
        
        return retorno;
    }
    
    public EnderecoDTO updateEndereco(EnderecoDTO enderecoDTO) {
        EnderecoModel enderecoModel = ajustaEndereco(enderecoDTO);
        validaEndereco(enderecoModel);
        
        try {
            connection = connectionFactory.getConnection();
            connection.setAutoCommit(false);
            enderecoRepository = new EnderecoRepository(connection);

            enderecoModel = enderecoRepository.updateEndereco(enderecoModel);
        } 
        catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        } 
        finally {
            ConnectionFactory.closeConnection();
        }
        
        return EnderecoDTO.enderecoDTOMapper(enderecoModel);
    }
    
    public EnderecoDTO deleteEndereco(int id) {
        EnderecoDTO retorno = new EnderecoDTO();
        
        
        try {
            connection = connectionFactory.getConnection();
            connection.setAutoCommit(false);
            enderecoRepository = new EnderecoRepository(connection);
            
            int retornoConsulta = enderecoRepository.deleteEndereco(id);
            
            if(retornoConsulta == 0) {
                throw new ValidationException("Erro ao deletar: Não foi possivel encontrar esse endereço");
            }
            
            retorno.setId(id);
        } 
        catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        } 
        finally {
            ConnectionFactory.closeConnection();
        }
        
        return retorno;
    }
}

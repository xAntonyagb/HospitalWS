package br.unipar.hospitalws.services;

import br.unipar.hospitalws.DTO.EnderecoDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.InternalException;
import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.infrastructure.ConnectionFactory;
import br.unipar.hospitalws.models.EnderecoModel;
import br.unipar.hospitalws.repositories.EnderecoRepository;
import br.unipar.hospitalws.utils.StringFormatterUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnderecoService {
    
    private Connection connection = null;
    private EnderecoRepository enderecoRepository = null;
    
    private final Logger logger = Logger.getLogger("EnderecoService");

    public EnderecoDTO insertEndereco(EnderecoDTO enderecoDTO) {
        try {
            EnderecoModel enderecoModel = ajustaEndereco(enderecoDTO);
            validaEndereco(ajustaEndereco(enderecoDTO));
        
            this.connection = ConnectionFactory.getConnection();
            this.enderecoRepository = new EnderecoRepository();

            enderecoModel = this.enderecoRepository.insertEndereco(enderecoModel);
            ConnectionFactory.commit();
            return EnderecoDTO.enderecoDTOMapper(enderecoModel);
        } 
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(insertEndereco) "+ ex.getMessage());
            throw new DataBaseException("erro ao inserir o endereco.");
        }
        catch(ValidationException ex) {
            logger.log(Level.INFO, "(insertEndereco) Requisição foi rejeitada pelo processo de validação "+ ex.getMessage());
            throw ex;
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(insertEndereco) Um erro inesperado aconteceu: Nao foi possivel finalizar a execucao desse metodo. "+ ex.getMessage());
            throw new InternalException("insertEndereco");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
    }
    
    public EnderecoDTO getEnderecoById(int id) {
        try {
            this.connection = ConnectionFactory.getConnection();
            this.enderecoRepository = new EnderecoRepository();

            EnderecoModel retorno = this.enderecoRepository.getEnderecoById(id);
            ConnectionFactory.commit();
            return EnderecoDTO.enderecoDTOMapper(retorno);
        } 
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(getEnderecoById) "+ ex.getMessage());
            throw new DataBaseException("erro ao pesquisar endereco.");
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(getEnderecoById) Um erro inesperado aconteceu: Nao foi possivel finalizar a execucao desse metodo. "+ ex.getMessage());
            throw new InternalException("getEnderecoById");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
    }
    
    public ArrayList<EnderecoDTO> getAllEnderecos() {
        try {
            this.connection = ConnectionFactory.getConnection();
            this.enderecoRepository = new EnderecoRepository();
            
            ArrayList<EnderecoModel> consulta = new ArrayList<EnderecoModel>();
            ArrayList<EnderecoDTO> retorno = new ArrayList<EnderecoDTO>();
        
            consulta = this.enderecoRepository.getAllEnderecos();
            for(EnderecoModel enderecoModel : consulta) {
                retorno.add(EnderecoDTO.enderecoDTOMapper(enderecoModel));
            }
            
            ConnectionFactory.commit();
            return retorno;
        } 
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(getAllEnderecos) "+ ex.getMessage());
            throw new DataBaseException("erro ao pesquisar enderecos.");
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(getAllEnderecos) Um erro inesperado aconteceu: Nao foi possivel finalizar a execucao desse metodo. "+ ex.getMessage());
            throw new InternalException("getAllEnderecos");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
        
    }
    
    public EnderecoDTO updateEndereco(EnderecoDTO enderecoDTO) {
        try {
            EnderecoModel enderecoModel = ajustaEndereco(enderecoDTO);
            validaEndereco(enderecoModel);
            
            this.connection = ConnectionFactory.getConnection();
            this.enderecoRepository = new EnderecoRepository();

            enderecoModel = this.enderecoRepository.updateEndereco(enderecoModel);
            ConnectionFactory.commit();
            
            return EnderecoDTO.enderecoDTOMapper(enderecoModel);
        } 
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(updateEndereco) "+ ex.getMessage());
            throw new DataBaseException("erro ao atualizar o endereco.");
        }
        catch(ValidationException ex) {
            logger.log(Level.INFO, "(updateEndereco) Requisição foi rejeitada pelo processo de validação "+ ex.getMessage());
            throw ex;
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(updateEndereco) Um erro inesperado aconteceu: Nao foi possivel finalizar a execucao desse metodo. "+ ex.getMessage());
            throw new InternalException("updateEndereco");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
        
    }
    
    public EnderecoDTO deleteEndereco(int id) {
        try {
            this.connection = ConnectionFactory.getConnection();
            this.enderecoRepository = new EnderecoRepository();
            
            int retornoConsulta = this.enderecoRepository.deleteEndereco(id);
            
            if(retornoConsulta == 0) {
                throw new ValidationException("Erro ao deletar: Não foi possivel encontrar esse endereço");
            }
            
            EnderecoDTO retorno = new EnderecoDTO();
            retorno.setId(id);
            
            ConnectionFactory.commit();
            return retorno;
        } 
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(deleteEndereco) "+ ex.getMessage());
            throw new DataBaseException("erro ao deletar endereco.");
        }
        catch(ValidationException ex) {
            logger.log(Level.INFO, "(deleteEndereco) Requisição foi rejeitada pelo processo de validação "+ ex.getMessage());
            throw ex;
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(deleteEndereco) Um erro inesperado aconteceu: Nao foi possivel finalizar a execucao desse metodo. "+ ex.getMessage());
            throw new InternalException("deleteEndereco");
        }
        finally {
            ConnectionFactory.closeConnection();
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
}

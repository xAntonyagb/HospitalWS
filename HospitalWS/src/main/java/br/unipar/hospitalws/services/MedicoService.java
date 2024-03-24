package br.unipar.hospitalws.services;

import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.infrastructure.ConstructionFactory;
import br.unipar.hospitalws.models.EnderecoModel;
import br.unipar.hospitalws.models.MedicoModel;
import br.unipar.hospitalws.repositories.MedicoRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedicoService {
    
    private ConstructionFactory constructionFactory = new ConstructionFactory();
    private Connection connection = null;
    private MedicoRepository medicoRepository = null;
    private PessoaService pessoaService = new PessoaService();;
    private EnderecoService enderecoService = new EnderecoService();;
    
    public MedicoModel insertMedico(MedicoModel medicoModel) {
        MedicoModel retorno = new MedicoModel();
        
        try {
            EnderecoModel enderecoRetorno = enderecoService.insertEndereco(medicoModel.getEndereco());
            medicoModel.setEndereco(enderecoRetorno);

            int idPessoa = pessoaService.insertPessoa(medicoModel, true)
                    .getPessoaId();
            medicoModel.setPessoaId(idPessoa);

            
            if(Integer.toString(medicoModel.getCRM()).length() != 12) {
                throw new ValidationException("CRM inválido! Informe um CRM com 12 digitos");
            }
            if(medicoModel.getEspecialidade() == null){
                throw new ValidationException("Especialidade inválida! Porfavor informe um tipo de especialidade válida");
            }
            
            connection = constructionFactory.getConnection();
            medicoRepository = new MedicoRepository(connection);

            retorno = medicoRepository.insertMedico(medicoModel);
            connection.commit();
        } 
        catch (SQLException ex) {
            constructionFactory.rollback(connection);
            throw new DataBaseException(ex.getMessage());
        } 
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
                
            } catch (SQLException ex) {
                throw new DataBaseException(ex.getMessage());
            }
        }
        
        return retorno;
    }
    
    public MedicoModel getMedicoById(int id) {
        MedicoModel retorno = new MedicoModel();
        
        try {
            connection = constructionFactory.getConnection();
            medicoRepository = new MedicoRepository(connection);

            retorno = medicoRepository.getMedicoById(id);
            connection.commit();
        } 
        catch (SQLException ex) {
            constructionFactory.rollback(connection);
            throw new DataBaseException(ex.getMessage());
        } 
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
                
            } catch (SQLException ex) {
                throw new DataBaseException(ex.getMessage());
            }
        }
        
        return retorno;
    }
    
    public ArrayList<MedicoModel> getAllMedicos() {
        ArrayList<MedicoModel> retorno = new ArrayList<MedicoModel>();
        
        try {
            connection = constructionFactory.getConnection();
            medicoRepository = new MedicoRepository(connection);

            retorno = medicoRepository.getAllMedicos();
            connection.commit();
        } 
        catch (SQLException ex) {
            constructionFactory.rollback(connection);
            throw new DataBaseException(ex.getMessage());
        } 
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
                
            } catch (SQLException ex) {
                throw new DataBaseException(ex.getMessage());
            }
        }
        
        return retorno;
    }
    
    public MedicoModel updateMedico(MedicoModel medicoModel) {
        MedicoModel retorno = new MedicoModel();
        
        try {
            EnderecoModel enderecoRetorno = enderecoService.insertEndereco(medicoModel.getEndereco());
            medicoModel.setEndereco(enderecoRetorno);

            if(medicoModel.getGmail() != null) {
                throw new ValidationException("Você não pode atualizar o email de um médico!");
            }
            if(medicoModel.getEspecialidade()!= null) {
                throw new ValidationException("Você não pode atualizar a especialidade de um médico!");
            }
            if(medicoModel.getCRM() != 0) {
                throw new ValidationException("Você não pode atualizar o CRM de um médico!");
            }
            
            if(medicoModel.getCpf().length() != 11) {
                throw new ValidationException("CPF inválido! Informe um CPF com 11 digitos");
            }
            if(medicoModel.getNome().length() < 0
                    || medicoModel.getNome().isEmpty()
                    || medicoModel.getNome() == null){
                throw new ValidationException("Nome inválido! Porfavor informe algum nome");
            }
            if(medicoModel.getTelefone().length() < 0
                    || medicoModel.getTelefone().length() < 9
                    || medicoModel.getTelefone().isEmpty()
                    || medicoModel.getTelefone() == null){
                throw new ValidationException("Telefone inválido! Porfavor informe um telefone válido");
            }
            
            int idPessoa = pessoaService.insertPessoa(medicoModel, false)
                    .getPessoaId();
            medicoModel.setPessoaId(idPessoa);
            
            connection = constructionFactory.getConnection();
            medicoRepository = new MedicoRepository(connection);

            retorno = medicoRepository.updateMedico(medicoModel);
            connection.commit();
        } 
        catch (SQLException ex) {
            constructionFactory.rollback(connection);
            throw new DataBaseException(ex.getMessage());
        } 
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
                
            } catch (SQLException ex) {
                throw new DataBaseException(ex.getMessage());
            }
        }
        
        return retorno;
    }
    
    public void deleteMedicoById(int id) {
        try {
            connection = constructionFactory.getConnection();
            medicoRepository = new MedicoRepository(connection);

            medicoRepository.deleteMedicoById(id);
            connection.commit();
        } 
        catch (SQLException ex) {
            constructionFactory.rollback(connection);
            throw new DataBaseException(ex.getMessage());
        } 
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
                
            } catch (SQLException ex) {
                throw new DataBaseException(ex.getMessage());
            }
        }
    }
    
}

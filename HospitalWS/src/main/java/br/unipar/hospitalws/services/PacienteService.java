package br.unipar.hospitalws.services;

import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.infrastructure.ConnectionFactory;
import br.unipar.hospitalws.models.EnderecoModel;
import br.unipar.hospitalws.models.PacienteModel;
import br.unipar.hospitalws.repositories.PacienteRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PacienteService {
    
    private ConnectionFactory connectionFactory = new ConnectionFactory();
    private Connection connection = null;
    private PacienteRepository pacienteRepository = null;
    private PessoaService pessoaService = new PessoaService();
    private EnderecoService enderecoService = new EnderecoService();
    
    public PacienteModel insertPaciente(PacienteModel pacienteModel) {
        PacienteModel retorno = new PacienteModel();
        
        try {
            EnderecoModel enderecoRetorno = enderecoService.insertEndereco(pacienteModel.getEndereco());
            pacienteModel.setEndereco(enderecoRetorno);

            int idPessoa = pessoaService.insertPessoa(pacienteModel, true)
                    .getPessoaId();
            pacienteModel.setPessoaId(idPessoa);

            connection = connectionFactory.getConnection();
            pacienteRepository = new PacienteRepository(connection);

            retorno = pacienteRepository.insertPaciente(pacienteModel);
            connection.commit();
        } 
        catch (SQLException ex) {
            connectionFactory.rollback(connection);
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
    
    public PacienteModel getPacienteById(int id) {
        PacienteModel retorno = new PacienteModel();
        
        try {
            connection = connectionFactory.getConnection();
            pacienteRepository = new PacienteRepository(connection);

            retorno = pacienteRepository.getPacienteById(id);
            connection.commit();
            
        } catch (SQLException ex) {
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
    
    public ArrayList<PacienteModel> getAllPacientes() {
        ArrayList<PacienteModel> retorno = new ArrayList<PacienteModel>();
        
        try {
            connection = connectionFactory.getConnection();
            pacienteRepository = new PacienteRepository(connection);

            retorno = pacienteRepository.getAllPacientes();
            connection.commit();
            
        } catch (SQLException ex) {
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
    
    public PacienteModel updatePaciente(PacienteModel pacienteModel) {
        PacienteModel retorno = new PacienteModel();
        
        try {
            EnderecoModel enderecoRetorno = enderecoService.updateEndereco(pacienteModel.getEndereco());
            pacienteModel.setEndereco(enderecoRetorno);

            if(!pacienteModel.getCpf().equals("?") && !pacienteModel.getCpf().isEmpty()) {
                throw new ValidationException("Não se pode atualizar o cpf de um paciente!");
            }
            if(!pacienteModel.getGmail().equals("?") && !pacienteModel.getGmail().isEmpty()){
                throw new ValidationException("Não se pode atualizar o e-mail de um paciente!");
            }

            if(pacienteModel.getNome().length() < 0
                    || pacienteModel.getNome().isEmpty()
                    || pacienteModel.getNome() == null){
                throw new ValidationException("Nome inválido! Porfavor informe algum nome");
            }
            if(pacienteModel.getTelefone().length() < 0
                    || pacienteModel.getTelefone().length() < 9
                    || pacienteModel.getTelefone().isEmpty()
                    || pacienteModel.getTelefone() == null){
                throw new ValidationException("Telefone inválido! Porfavor informe um telefone válido");
            }

            int idPessoa = pessoaService.updatePessoa(pacienteModel, false)
                    .getPessoaId();
            pacienteModel.setPessoaId(idPessoa);

            connection = connectionFactory.getConnection();
            pacienteRepository = new PacienteRepository(connection);

            retorno = pacienteRepository.updatePaciente(pacienteModel);
            connection.commit();
        } 
        catch (SQLException ex) {
            connectionFactory.rollback(connection);
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
    
    public void desativaPaciente(int id) {
         try {
            connection = connectionFactory.getConnection();
            pacienteRepository = new PacienteRepository(connection);

            pacienteRepository.desativaPaciente(id);
            
        } catch (SQLException ex) {
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

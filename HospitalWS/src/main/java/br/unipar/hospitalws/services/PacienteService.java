package br.unipar.hospitalws.services;

import br.unipar.hospitalws.DTO.EnderecoDTO;
import br.unipar.hospitalws.DTO.PacienteDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.infrastructure.ConnectionFactory;
import br.unipar.hospitalws.models.EnderecoModel;
import br.unipar.hospitalws.models.PacienteModel;
import br.unipar.hospitalws.repositories.PacienteRepository;
import br.unipar.hospitalws.utils.StringFormatterUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PacienteService {
    
    private ConnectionFactory connectionFactory = new ConnectionFactory();
    private Connection connection = null;
    private PacienteRepository pacienteRepository = null;
    private PessoaService pessoaService = new PessoaService();
    private EnderecoService enderecoService = new EnderecoService();
    
    private PacienteModel ajustaPaciente(PacienteDTO paciente) {
        paciente.setBairro(StringFormatterUtil.ajustaNormalInput(paciente.getBairro()));
        paciente.setCEP(StringFormatterUtil.ajustaNormalInput(paciente.getCEP()));
        paciente.setUF(StringFormatterUtil.ajustaNormalInput(paciente.getUF()));
        paciente.setCidade(StringFormatterUtil.ajustaNormalInput(paciente.getCidade()));
        paciente.setComplemento(StringFormatterUtil.ajustaNormalInput(paciente.getComplemento()));
        paciente.setLogradouro(StringFormatterUtil.ajustaNormalInput(paciente.getLogradouro()));
        paciente.setNome(StringFormatterUtil.ajustaNormalInput(paciente.getNome()));
        paciente.setGmail(StringFormatterUtil.ajustaNormalInput(paciente.getGmail()));
        paciente.setCpf(StringFormatterUtil.ajustaNumberInput(paciente.getCpf()));
        paciente.setNumero(StringFormatterUtil.ajustaNumberInput(paciente.getNumero()));
        paciente.setTelefone(StringFormatterUtil.ajustaNumberInput(paciente.getTelefone()));
        
        return PacienteModel.pacienteModelMapper(paciente);
    }
    
    public PacienteDTO insertPaciente(PacienteDTO pacienteDTO) {
        PacienteModel pacienteModel = ajustaPaciente(pacienteDTO);
        
        try {
            EnderecoModel enderecoRetorno = EnderecoModel.enderecoModelMapper( //Converte para Model 
                    enderecoService.insertEndereco( //Exige e retorna DTO 
                            EnderecoDTO.enderecoDTOMapper(pacienteModel.getEndereco() //Converte para DTO 
                            )));
            
            pacienteModel.setEndereco(enderecoRetorno);

            int idPessoa = pessoaService.insertPessoa(pacienteModel, true)
                    .getIdPessoa();
            pacienteModel.setIdPessoa(idPessoa);

            connection = connectionFactory.getConnection();
            connection.setAutoCommit(false);
            pacienteRepository = new PacienteRepository(connection);

            pacienteModel = pacienteRepository.insertPaciente(pacienteModel);
            connection.commit();
        } 
        catch (SQLException ex) {
            connectionFactory.rollback(connection);
            throw new DataBaseException(ex.getMessage());
        } 
        finally {
            if(connection != null)
                connectionFactory.closeConnection(connection);
        }
        
        return PacienteDTO.pacienteDTOMapper(pacienteModel);
    }
    
    public PacienteDTO getPacienteById(int id) {
        PacienteModel retorno = new PacienteModel();
        
        try {
            connection = connectionFactory.getConnection();
            connection.setAutoCommit(false);
            pacienteRepository = new PacienteRepository(connection);

            retorno = pacienteRepository.getPacienteById(id);
            connection.commit();
            
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        finally {
            if(connection != null)
                connectionFactory.closeConnection(connection);
        }
        
        return PacienteDTO.pacienteDTOMapper(retorno);
    }
    
    public ArrayList<PacienteDTO> getAllPacientes() {
        ArrayList<PacienteModel> consulta = new ArrayList<PacienteModel>();
        ArrayList<PacienteDTO> retorno = new ArrayList<PacienteDTO>();
        
        try {
            connection = connectionFactory.getConnection();
            connection.setAutoCommit(false);
            pacienteRepository = new PacienteRepository(connection);

            consulta = pacienteRepository.getAllPacientes();
            
            for(PacienteModel pacienteModel : consulta) {
                retorno.add(PacienteDTO.pacienteDTOMapper(pacienteModel));
            }
            
            connection.commit();
            
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        finally {
            if(connection != null)
                connectionFactory.closeConnection(connection);
        }
        
        return retorno;
    }
    
    public PacienteDTO updatePaciente(PacienteDTO pacienteDTO) {
        PacienteModel pacienteModel = ajustaPaciente(pacienteDTO);
        
        try {
            EnderecoModel enderecoRetorno = EnderecoModel.enderecoModelMapper( //Converte para Model 
                    enderecoService.insertEndereco( //Exige e retorna DTO 
                            EnderecoDTO.enderecoDTOMapper(pacienteModel.getEndereco() //Converte para DTO 
                            )));
            
            pacienteModel.setEndereco(enderecoRetorno);

            if(pacienteModel.getCpf() != null) {
                throw new ValidationException("Não se pode atualizar o cpf de um paciente!");
            }
            if(pacienteModel.getGmail() != null ){
                throw new ValidationException("Não se pode atualizar o e-mail de um paciente!");
            }
            
            if(pacienteModel.getNome() == null){
                throw new ValidationException("Nome inválido! Porfavor informe algum nome");
            }
            if(pacienteModel.getTelefone() == null){
                throw new ValidationException("Telefone inválido! Porfavor informe um telefone válido");
            }

            int idPessoa = pessoaService.updatePessoa(pacienteModel, false)
                    .getIdPessoa();
            pacienteModel.setIdPessoa(idPessoa);
            
            connection = connectionFactory.getConnection();
            connection.setAutoCommit(false);
            pacienteRepository = new PacienteRepository(connection);

            pacienteModel = pacienteRepository.updatePaciente(pacienteModel);
            connection.commit();
        } 
        catch (SQLException ex) {
            connectionFactory.rollback(connection);
            throw new DataBaseException(ex.getMessage());
        } 
        finally {
            if(connection != null)
                connectionFactory.closeConnection(connection);
        }
        
        return PacienteDTO.pacienteDTOMapper(pacienteModel);
    }
    
    public PacienteDTO desativaPaciente(int id) {
        PacienteDTO retorno = new PacienteDTO();
        
         try {
            connection = connectionFactory.getConnection();
            connection.setAutoCommit(false);
            pacienteRepository = new PacienteRepository(connection);

            int retornoConsulta = pacienteRepository.desativaPaciente(id);
            if(retornoConsulta == 0) {
                throw new ValidationException("Erro ao deletar: Não foi possivel encontrar esse paciente");
            }
            
            retorno.setId(id);
            retorno.setAtivo(false);
            
            connection.commit();
            
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        finally {
            if(connection != null)
                connectionFactory.closeConnection(connection);
        }
        
        return retorno;
    }
    
}

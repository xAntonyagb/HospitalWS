package br.unipar.hospitalws.services;

import br.unipar.hospitalws.DTO.EnderecoDTO;
import br.unipar.hospitalws.DTO.PacienteDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.InternalException;
import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.infrastructure.ConnectionFactory;
import br.unipar.hospitalws.models.EnderecoModel;
import br.unipar.hospitalws.models.PacienteModel;
import br.unipar.hospitalws.repositories.ConsultaRepository;
import br.unipar.hospitalws.repositories.EnderecoRepository;
import br.unipar.hospitalws.repositories.PacienteRepository;
import br.unipar.hospitalws.repositories.PessoaRepository;
import br.unipar.hospitalws.utils.StringFormatterUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PacienteService {
    
    private Connection connection = null;
    private PacienteRepository pacienteRepository = null;
    private PessoaRepository pessoaRepository = null;
    private EnderecoRepository enderecoRepository = null;
    private ConsultaRepository consultaRepository = null;
    
    private final Logger logger = Logger.getLogger("PacienteService");
    
    
    public PacienteDTO insertPaciente(PacienteDTO pacienteDTO) {
        try {
            PacienteModel pacienteModel = ajustaPaciente(pacienteDTO);
            PessoaService.validaInsertPessoa(pacienteModel);
        
            this.connection = ConnectionFactory.getConnection();
            this.enderecoRepository = new EnderecoRepository();
            this.pessoaRepository = new PessoaRepository();
            this.pacienteRepository = new PacienteRepository();
            
            EnderecoModel enderecoRetorno = this.enderecoRepository.insertEndereco(pacienteModel.getEndereco());
            pacienteModel.setEndereco(enderecoRetorno);
            
            int idPessoa = this.pessoaRepository.insertPessoa(pacienteModel).getIdPessoa();
            pacienteModel.setIdPessoa(idPessoa);

            int idPaciente = this.pacienteRepository.insertPaciente(pacienteModel);
            pacienteModel.setIdPaciente(idPaciente);
            
            ConnectionFactory.commit();
            return PacienteDTO.pacienteDTOMapper(pacienteModel);
        } 
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(insertPaciente) "+ ex.getMessage());
            throw new DataBaseException("erro ao inserir um novo paciente.");
        }
        catch(ValidationException ex) {
            logger.log(Level.INFO, "(insertPaciente) Requisicao foi rejeitada pelo processo de validacao "+ ex.getMessage());
            throw ex;
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(insertPaciente) Um erro inesperado aconteceu: Nao foi possivel finalizar a execução desse metodo. "+ ex.getMessage());
            throw new InternalException("insertPaciente");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
    }
    
    public PacienteDTO getPacienteById(int id) {
        try {
            this.connection = ConnectionFactory.getConnection();
            this.pacienteRepository = new PacienteRepository();
            
            PacienteModel retorno = this.pacienteRepository.getPacienteById(id);
            ConnectionFactory.commit();
            
            return PacienteDTO.pacienteDTOMapper(retorno);
        } 
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(getPacienteById) "+ ex.getMessage());
            throw new DataBaseException("erro ao pesquisar por paciente.");
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(getPacienteById) Um erro inesperado aconteceu: Nao foi possivel finalizar a execução desse metodo. "+ ex.getMessage());
            throw new InternalException("getPacienteById");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
    }
    
    public List<PacienteDTO> getAllPacientes() {
        try {
            this.connection = ConnectionFactory.getConnection();
            this.pacienteRepository = new PacienteRepository();
            
            List<PacienteDTO> retorno = new ArrayList<PacienteDTO>();
            List<PacienteModel> retornoConsulta = new ArrayList<PacienteModel>();

            retornoConsulta = this.pacienteRepository.getAllPacientes();
            
            for(PacienteModel pacienteModel : retornoConsulta) {
                pacienteModel = (PacienteModel) this.pessoaRepository.getPessoaById(pacienteModel);
                retorno.add(PacienteDTO.pacienteDTOMapper(pacienteModel));
            }
            
            ConnectionFactory.commit();
            return retorno;
        } 
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(getAllPacientes) "+ ex.getMessage());
            throw new DataBaseException("erro ao pesquisar por pacientes.");
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(getAllPacientes) Um erro inesperado aconteceu: Nao foi possivel finalizar a execução desse metodo. "+ ex.getMessage());
            throw new InternalException("getAllPacientes");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
    }
    
    public PacienteDTO updatePaciente(PacienteDTO pacienteDTO) {
        try {
            PacienteModel pacienteModel = ajustaPaciente(pacienteDTO);
            PessoaService.validaUpdatePessoa(pacienteModel);
            
            this.connection = ConnectionFactory.getConnection();
            this.pessoaRepository = new PessoaRepository();
            this.pacienteRepository = new PacienteRepository();

            PacienteModel pessoaRetorno = (PacienteModel) this.pessoaRepository.updatePessoa(pacienteModel);
            pacienteModel.setIdPessoa(pessoaRetorno.getIdPessoa());
            pacienteModel.setEndereco(pessoaRetorno.getEndereco());
            pacienteModel.setNome(pessoaRetorno.getNome());
            pacienteModel.setTelefone(pessoaRetorno.getTelefone());

            pacienteModel = this.pacienteRepository.updatePaciente(pacienteModel);
            
            ConnectionFactory.commit();
            return PacienteDTO.pacienteDTOMapper(pacienteModel);
        } 
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(updatePaciente) "+ ex.getMessage());
            throw new DataBaseException("erro ao atualizar cadastro de paciente.");
        }
        catch(ValidationException ex) {
            logger.log(Level.INFO, "(updatePaciente) Requisicao foi rejeitada pelo processo de validacao "+ ex.getMessage());
            throw ex;
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(updatePaciente) Um erro inesperado aconteceu: Nao foi possivel finalizar a execução desse metodo. "+ ex.getMessage());
            throw new InternalException("updatePaciente");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
    }
    
    public PacienteDTO desativaPaciente(int id) {
         try {
            this.connection = ConnectionFactory.getConnection();
            this.pacienteRepository = new PacienteRepository();
            this.consultaRepository = new ConsultaRepository();

            boolean isDesativado = this.pacienteRepository.desativaPaciente(id);
            if(!isDesativado) {
                throw new ValidationException("Erro ao desativar: Não foi possivel encontrar esse paciente");
            }
            
            PacienteDTO retorno = new PacienteDTO();
            retorno.setId(id);
            retorno.setAtivo(false);
            
            this.consultaRepository.cancelarConsultaByIdPaciente(id);
            ConnectionFactory.commit();
            
            return retorno;
        } 
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(desativaPaciente) "+ ex.getMessage());
            throw new DataBaseException("erro ao desativar paciente.");
        }
        catch(ValidationException ex) {
            logger.log(Level.INFO, "(desativaPaciente) Requisicao foi rejeitada pelo processo de validacao "+ ex.getMessage());
            throw ex;
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(desativaPaciente) Um erro inesperado aconteceu: Nao foi possivel finalizar a execução desse metodo. "+ ex.getMessage());
            throw new InternalException("desativaPaciente");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
    }
    
    
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
    
}

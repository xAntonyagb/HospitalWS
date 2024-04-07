package br.unipar.hospitalws.services;

import br.unipar.hospitalws.DTO.MedicoDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.InternalException;
import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.infrastructure.ConnectionFactory;
import br.unipar.hospitalws.models.EnderecoModel;
import br.unipar.hospitalws.models.MedicoModel;
import br.unipar.hospitalws.repositories.ConsultaRepository;
import br.unipar.hospitalws.repositories.EnderecoRepository;
import br.unipar.hospitalws.repositories.MedicoRepository;
import br.unipar.hospitalws.repositories.PessoaRepository;
import br.unipar.hospitalws.utils.StringFormatterUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MedicoService {
    
    private Connection connection = null;
    private MedicoRepository medicoRepository = null;
    private PessoaRepository pessoaRepository = null;
    private EnderecoRepository enderecoRepository = null;
    private ConsultaRepository consultaRepository = null;

    private final Logger logger = Logger.getLogger("MedicoService");
    
    
    public MedicoDTO insertMedico(MedicoDTO medicoDTO) {
        try {
            MedicoModel medicoModel = ajustaMedico(medicoDTO);
            validaInsertMedico(medicoModel);
            
            this.connection = ConnectionFactory.getConnection();
            this.enderecoRepository = new EnderecoRepository();
            this.pessoaRepository = new PessoaRepository();
            this.medicoRepository = new MedicoRepository();
            
            EnderecoModel enderecoRetorno = this.enderecoRepository.insertEndereco(medicoModel.getEndereco());
            medicoModel.setEndereco(enderecoRetorno);

            int idPessoa = this.pessoaRepository.insertPessoa(medicoModel).getIdPessoa();
            medicoModel.setIdPessoa(idPessoa);
            
            int idMedico = this.medicoRepository.insertMedico(medicoModel);
            medicoModel.setIdMedico(idMedico);
            
            ConnectionFactory.commit();
            return MedicoDTO.medicoDTOMapper(medicoModel);
        }
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(insertMedico) "+ ex.getMessage());
            throw new DataBaseException("erro ao inserir um novo médico.");
        }
        catch(ValidationException ex) {
            logger.log(Level.INFO, "(insertMedico) Requisicao foi rejeitada pelo processo de validacao "+ ex.getMessage());
            throw ex;
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(insertMedico) Um erro inesperado aconteceu: Nao foi possivel finalizar a execução desse metodo. "+ ex.getMessage());
            throw new InternalException("insertMedico");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
    }
    
    public MedicoDTO getMedicoById(int id) {
        try {
            this.connection = ConnectionFactory.getConnection();
            this.medicoRepository = new MedicoRepository();
            
            MedicoModel retorno = this.medicoRepository.getMedicoById(id);
            ConnectionFactory.commit();
            return MedicoDTO.medicoDTOMapper(retorno);
        } 
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(getMedicoById) "+ ex.getMessage());
            throw new DataBaseException("erro ao pesquisar por médico.");
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(getMedicoById) Um erro inesperado aconteceu: Nao foi possivel finalizar a execução desse metodo. "+ ex.getMessage());
            throw new InternalException("getMedicoById");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
    }
    
    public List<MedicoDTO> getAllMedicos() {
        try {
            this.connection = ConnectionFactory.getConnection();
            this.medicoRepository = new MedicoRepository();
            
            List<MedicoDTO> retorno = new ArrayList<MedicoDTO>();
            List<MedicoModel> retornoConsulta = new ArrayList<MedicoModel>();

            retornoConsulta = this.medicoRepository.getAllMedicos();
            
            for(MedicoModel medicoModel : retornoConsulta) {
                retorno.add(MedicoDTO.medicoDTOMapper(medicoModel));
            }
            
            ConnectionFactory.commit();
            return retorno;
        } 
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(getAllMedicos) "+ ex.getMessage());
            throw new DataBaseException("erro ao pesquisar por médicos.");
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(getAllMedicos) Um erro inesperado aconteceu: Nao foi possivel finalizar a execução desse metodo. "+ ex.getMessage());
            throw new InternalException("getAllMedicos");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
    }
    
    public MedicoDTO updateMedico(MedicoDTO medicoDTO) {
        try {
            MedicoModel medicoModel = ajustaMedico(medicoDTO);
            validaUpdateMedico(medicoModel);
            
            this.connection = ConnectionFactory.getConnection();
            this.pessoaRepository = new PessoaRepository();
            this.enderecoRepository = new EnderecoRepository();
            this.medicoRepository = new MedicoRepository();

            medicoModel = this.medicoRepository.updateMedico(medicoModel);
            
            ConnectionFactory.commit();
            return MedicoDTO.medicoDTOMapper(medicoModel);
        } 
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(updateMedico) "+ ex.getMessage());
            throw new DataBaseException("erro ao atualizar cadastro de médico.");
        }
        catch(ValidationException ex) {
            logger.log(Level.INFO, "(updateMedico) Requisicao foi rejeitada pelo processo de validacao "+ ex.getMessage());
            throw ex;
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(updateMedico) Um erro inesperado aconteceu: Nao foi possivel finalizar a execução desse metodo. "+ ex.getMessage());
            throw new InternalException("updateMedico");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
    }
    
    public MedicoDTO desativaMedico(int id) {
        try {
            this.connection = ConnectionFactory.getConnection();
            this.consultaRepository = new ConsultaRepository();
            this.medicoRepository = new MedicoRepository();
            
            boolean isDesativado = this.medicoRepository.desativaMedico(id);
            if(!isDesativado) {
                throw new ValidationException("Erro ao desativar: Não foi possivel encontrar esse médico");
            }
                
            MedicoDTO retorno = new MedicoDTO();
            retorno.setId(id);
            retorno.setAtivo(false);
            
            this.consultaRepository.cancelarConsultaByIdMedico(id);
            ConnectionFactory.commit();
            
            return retorno;
        } 
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(desativaMedico) "+ ex.getMessage());
            throw new DataBaseException("erro ao desativar médico.");
        }
        catch(ValidationException ex) {
            logger.log(Level.INFO, "(updateMedico) Requisicao foi rejeitada pelo processo de validacao "+ ex.getMessage());
            throw ex;
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(desativaMedico) Um erro inesperado aconteceu: Nao foi possivel finalizar a execução desse metodo. "+ ex.getMessage());
            throw new InternalException("desativaMedico");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
    }
    
    private MedicoModel ajustaMedico(MedicoDTO medico) {
        medico.setBairro(StringFormatterUtil.ajustaNormalInput(medico.getBairro()));
        medico.setCEP(StringFormatterUtil.ajustaNormalInput(medico.getCEP()));
        medico.setCRM(StringFormatterUtil.ajustaNormalInput(medico.getCRM()));
        medico.setUF(StringFormatterUtil.ajustaNormalInput(medico.getUF()));
        medico.setCidade(StringFormatterUtil.ajustaNormalInput(medico.getCidade()));
        medico.setComplemento(StringFormatterUtil.ajustaNormalInput(medico.getComplemento()));
        medico.setLogradouro(StringFormatterUtil.ajustaNormalInput(medico.getLogradouro()));
        medico.setNome(StringFormatterUtil.ajustaNormalInput(medico.getNome()));
        medico.setGmail(StringFormatterUtil.ajustaNormalInput(medico.getGmail()));
        medico.setCpf(StringFormatterUtil.ajustaNumberInput(medico.getCpf()));
        medico.setNumero(StringFormatterUtil.ajustaNumberInput(medico.getNumero()));
        medico.setTelefone(StringFormatterUtil.ajustaNumberInput(medico.getTelefone()));
        
        return MedicoModel.medicoModelMapper(medico);
    }

    private void validaInsertMedico(MedicoModel medicoModel) {
        PessoaService.validaInsertPessoa(medicoModel);
            
        if(medicoModel.getCRM() == null || medicoModel.getCRM().length() != 12) {
            throw new ValidationException("CRM inválido! Informe um CRM com 12 digitos");
        }
        if(medicoModel.getEspecialidade() == null){
            throw new ValidationException("Especialidade inválida! Porfavor informe um tipo de especialidade válida (DERMATOLOGIA, ORTOPEDIA, CARDIOLOGIA, GINECOLOGIA)");
        }
    }

    private void validaUpdateMedico(MedicoModel medicoModel) {
        PessoaService.validaUpdatePessoa(medicoModel);
            
        if(medicoModel.getEspecialidade() != null) {
            throw new ValidationException("Você não pode atualizar a especialidade de um médico!");
        }
        if(medicoModel.getCRM() != null) {
            throw new ValidationException("Você não pode atualizar o CRM de um médico!");
        }
    }
    
}

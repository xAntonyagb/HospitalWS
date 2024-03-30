package br.unipar.hospitalws.services;

import br.unipar.hospitalws.DTO.EnderecoDTO;
import br.unipar.hospitalws.DTO.MedicoDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.infrastructure.ConnectionFactory;
import br.unipar.hospitalws.models.EnderecoModel;
import br.unipar.hospitalws.models.MedicoModel;
import br.unipar.hospitalws.repositories.MedicoRepository;
import br.unipar.hospitalws.utils.StringValidatorUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedicoService {
    
    private ConnectionFactory connectionFactory = new ConnectionFactory();
    private Connection connection = null;
    private MedicoRepository medicoRepository = null;
    private PessoaService pessoaService = new PessoaService();;
    private EnderecoService enderecoService = new EnderecoService();;
    
    private MedicoModel ajustaMedico(MedicoDTO medico) {
        medico.setBairro(StringValidatorUtil.ajustaNormalInput(medico.getBairro()));
        medico.setCEP(StringValidatorUtil.ajustaNormalInput(medico.getCEP()));
        medico.setCRM(StringValidatorUtil.ajustaNormalInput(medico.getCRM()));
        medico.setUF(StringValidatorUtil.ajustaNormalInput(medico.getUF()));
        medico.setCidade(StringValidatorUtil.ajustaNormalInput(medico.getCidade()));
        medico.setComplemento(StringValidatorUtil.ajustaNormalInput(medico.getComplemento()));
        medico.setLogradouro(StringValidatorUtil.ajustaNormalInput(medico.getLogradouro()));
        medico.setNome(StringValidatorUtil.ajustaNormalInput(medico.getNome()));
        medico.setGmail(StringValidatorUtil.ajustaNormalInput(medico.getGmail()));
        medico.setCpf(StringValidatorUtil.ajustaNumberInput(medico.getCpf()));
        medico.setNumero(StringValidatorUtil.ajustaNumberInput(medico.getNumero()));
        medico.setTelefone(StringValidatorUtil.ajustaNumberInput(medico.getTelefone()));
        
        return MedicoModel.medicoModelMapper(medico);
    }
    
    public MedicoDTO insertMedico(MedicoDTO medicoDTO) {
        MedicoModel medicoModel = ajustaMedico(medicoDTO);
        
        try {
            EnderecoModel enderecoRetorno = EnderecoModel.enderecoModelMapper( //Converte para Model 
                    enderecoService.insertEndereco( //Exige e retorna DTO 
                            EnderecoDTO.enderecoDTOMapper(medicoModel.getEndereco() //Converte para DTO 
                            )));
            
            medicoModel.setEndereco(enderecoRetorno);

            int idPessoa = pessoaService.insertPessoa(medicoModel, true)
                    .getIdPessoa();
            medicoModel.setIdPessoa(idPessoa);

            if(medicoModel.getCRM().length() != 12) {
                throw new ValidationException("CRM inválido! Informe um CRM com 12 digitos (" + medicoModel.getCRM().length() + ")");
            }
            if(medicoModel.getEspecialidade() == null){
                throw new ValidationException("Especialidade inválida! Porfavor informe um tipo de especialidade válida");
            }
            
            connection = connectionFactory.getConnection();
            connection.setAutoCommit(false);
            medicoRepository = new MedicoRepository(connection);

            medicoModel = medicoRepository.insertMedico(medicoModel);
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
        
        return MedicoDTO.medicoDTOMapper(medicoModel);
    }
    
    public MedicoDTO getMedicoById(int id) {
        MedicoModel retorno = new MedicoModel();
        
        try {
            connection = connectionFactory.getConnection();
            connection.setAutoCommit(false);
            medicoRepository = new MedicoRepository(connection);

            retorno = medicoRepository.getMedicoById(id);
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
        
        return MedicoDTO.medicoDTOMapper(retorno);
    }
    
    public ArrayList<MedicoDTO> getAllMedicos() {
        ArrayList<MedicoDTO> retorno = new ArrayList<MedicoDTO>();
        ArrayList<MedicoModel> consulta = new ArrayList<MedicoModel>();
        
        try {
            connection = connectionFactory.getConnection();
            connection.setAutoCommit(false);
            medicoRepository = new MedicoRepository(connection);

            consulta = medicoRepository.getAllMedicos();
            
            for(MedicoModel medicoModel : consulta) {
                medicoModel = (MedicoModel) pessoaService.getPessoaById(medicoModel);
                retorno.add(MedicoDTO.medicoDTOMapper(medicoModel));
            }
            
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
        
        return retorno;
    }
    
    public MedicoDTO updateMedico(MedicoDTO medicoDTO) {
        MedicoModel medicoModel = ajustaMedico(medicoDTO);
        
        try {
            EnderecoModel enderecoRetorno = EnderecoModel.enderecoModelMapper( //Converte para Model 
                    enderecoService.updateEndereco( //Exige e retorna DTO 
                            EnderecoDTO.enderecoDTOMapper(medicoModel.getEndereco() //Converte para DTO 
                            )));
            
            medicoModel.setEndereco(enderecoRetorno);

            if(medicoModel.getGmail() != null) {
                throw new ValidationException("Você não pode atualizar o email de um médico!");
            }
            if(medicoModel.getEspecialidade() != null) {
                throw new ValidationException("Você não pode atualizar a especialidade de um médico!");
            }
            if(medicoModel.getCRM() != null) {
                throw new ValidationException("Você não pode atualizar o CRM de um médico!");
            }
            
            if(medicoModel.getCpf().length() != 11) {
                throw new ValidationException("CPF inválido! Informe um CPF com 11 digitos ("+ medicoModel.getCpf().length() +")");
            }
            if(medicoModel.getNome() == null){
                throw new ValidationException("Nome inválido! Porfavor informe algum nome");
            }
            if(medicoModel.getTelefone().length() < 9
                    || medicoModel.getTelefone() == null){
                throw new ValidationException("Telefone inválido! Porfavor informe um telefone com 9 digitos ("+ medicoModel.getTelefone().length() +")");
            }
            
            int idPessoa = pessoaService.updatePessoa(medicoModel, false)
                    .getIdPessoa();
            medicoModel.setIdPessoa(idPessoa);
            
            connection = connectionFactory.getConnection();
            connection.setAutoCommit(false);
            medicoRepository = new MedicoRepository(connection);

            medicoModel = medicoRepository.updateMedico(medicoModel);
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
        
        return MedicoDTO.medicoDTOMapper(medicoModel);
    }
    
    public MedicoDTO desativaMedico(int id) {
        MedicoDTO retorno = new MedicoDTO();
        
        try {
            connection = new ConnectionFactory().getConnection();
            connection.setAutoCommit(false);
            medicoRepository = new MedicoRepository(connection);

            int retornoConsulta = medicoRepository.desativaMedico(id);
            if(retornoConsulta == 0) {
                throw new ValidationException("Erro ao deletar: Não foi possivel encontrar esse médico");
            }
            
            retorno.setId(id);
            retorno.setAtivo(false);
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
        
        return retorno;
    }
    
}

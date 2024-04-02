package br.unipar.hospitalws.services;

import br.unipar.hospitalws.DTO.MedicoDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.infrastructure.ConnectionFactory;
import br.unipar.hospitalws.models.EnderecoModel;
import br.unipar.hospitalws.models.MedicoModel;
import br.unipar.hospitalws.repositories.EnderecoRepository;
import br.unipar.hospitalws.repositories.MedicoRepository;
import br.unipar.hospitalws.repositories.PessoaRepository;
import br.unipar.hospitalws.utils.StringFormatterUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedicoService {
    
    private ConnectionFactory connectionFactory = new ConnectionFactory();
    private Connection connection = null;
    private MedicoRepository medicoRepository = null;
    private PessoaRepository pessoaRepository = null;
    private EnderecoRepository enderecoRepository = null;

    public MedicoService() {
        try {
            this.connection = connectionFactory.getConnection();
            medicoRepository = new MedicoRepository(this.connection);
            pessoaRepository = new PessoaRepository(this.connection);
            enderecoRepository = new EnderecoRepository(this.connection);
        }
        catch(SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
    }
    
    
    public MedicoDTO insertMedico(MedicoDTO medicoDTO) {
        MedicoModel medicoModel = ajustaMedico(medicoDTO);
        
        try {
             try {
                this.connection = connectionFactory.getConnection();
                this.medicoRepository = new MedicoRepository(this.connection);
                this.enderecoRepository = new EnderecoRepository(this.connection);
            } catch (SQLException ex) { }
            
            
            EnderecoService.validaEndereco(medicoModel.getEndereco());
            EnderecoModel enderecoRetorno = this.enderecoRepository.insertEndereco(medicoModel.getEndereco());
            medicoModel.setEndereco(enderecoRetorno);
            connectionFactory.commit(connection);

            PessoaService.validaPessoa(medicoModel);
            int idPessoa = this.pessoaRepository.insertPessoa(medicoModel)
                    .getIdPessoa();
            medicoModel.setIdPessoa(idPessoa);

            if(medicoModel.getCRM() == null) {
                throw new ValidationException("CRM inválido! Porfavor informe um CRM");
            }
            if(medicoModel.getCRM() != null && medicoModel.getCRM().length() != 12) {
                throw new ValidationException("CRM inválido! Informe um CRM com 12 digitos (" + medicoModel.getCRM().length() + ")");
            }
            if(medicoModel.getEspecialidade() == null){
                throw new ValidationException("Especialidade inválida! Porfavor informe um tipo de especialidade válida (DERMATOLOGIA, ORTOPEDIA, CARDIOLOGIA, GINECOLOGIA)");
            }
            
            medicoModel = this.medicoRepository.insertMedico(medicoModel);
            connectionFactory.commit(connection);
        } 
        catch (DataBaseException ex) {
            connectionFactory.rollback(connection);
            throw ex;
        } 
        finally {
            connectionFactory.closeConnection(connection);
        }
        
        return MedicoDTO.medicoDTOMapper(medicoModel);
    }
    
    public MedicoDTO getMedicoById(int id) {
        try {
            try {
                this.connection = connectionFactory.getConnection();
                this.medicoRepository = new MedicoRepository(this.connection);
            } catch (SQLException ex) { }
            
            MedicoModel retorno = this.medicoRepository.getMedicoById(id);
            connectionFactory.commit(this.connection);
            return MedicoDTO.medicoDTOMapper(retorno);
        } 
        catch (DataBaseException ex) {
            connectionFactory.rollback(connection);
            throw ex;
        } 
        finally {
            connectionFactory.closeConnection(connection);
        }
    }
    
    public ArrayList<MedicoDTO> getAllMedicos() {
        try {
                this.connection = connectionFactory.getConnection();
                this.medicoRepository = new MedicoRepository(this.connection);
            } catch (SQLException ex) { }
            
        ArrayList<MedicoDTO> retorno = new ArrayList<MedicoDTO>();
        ArrayList<MedicoModel> consulta = new ArrayList<MedicoModel>();

        try {
            consulta = this.medicoRepository.getAllMedicos();
            
            for(MedicoModel medicoModel : consulta) {
                medicoModel = (MedicoModel) this.pessoaRepository.getPessoaById(medicoModel);
                retorno.add(MedicoDTO.medicoDTOMapper(medicoModel));
            }
            
            connectionFactory.commit(this.connection);
        } 
        catch (DataBaseException ex) {
            connectionFactory.rollback(this.connection);
            throw ex;
        } 
        finally {
            connectionFactory.closeConnection(this.connection);
        }
        
        return retorno;
    }
    
    public MedicoDTO updateMedico(MedicoDTO medicoDTO) {
        MedicoModel medicoModel = ajustaMedico(medicoDTO);
        
        try {
            try {
                this.connection = connectionFactory.getConnection();
                this.medicoRepository = new MedicoRepository(this.connection);
                this.enderecoRepository = new EnderecoRepository(this.connection);
            } catch (SQLException ex) { }
            
            EnderecoService.validaEndereco(medicoModel.getEndereco());
            EnderecoModel enderecoRetorno = this.enderecoRepository.updateEndereco(medicoModel.getEndereco());
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
            if(medicoModel.getCpf() != null && medicoModel.getCpf().length() != 11) {
                throw new ValidationException("CPF inválido! Informe um CPF com 11 digitos ("+ medicoModel.getCpf().length() +")");
            }
            if(medicoModel.getNome() == null){
                throw new ValidationException("Nome inválido! Porfavor informe algum nome");
            }
            if(medicoModel.getTelefone() == null || medicoModel.getTelefone().length() < 9){
                throw new ValidationException("Telefone inválido! Porfavor informe um telefone com 9 digitos ("+ medicoModel.getTelefone().length() +")");
            }

            medicoModel = this.medicoRepository.updateMedico(medicoModel);
            connectionFactory.commit(this.connection);
            connectionFactory.commit(connection);
        } 
        catch (DataBaseException ex) {
            connectionFactory.rollback(this.connection);
            throw ex;
        } 
        finally {
            connectionFactory.closeConnection(this.connection);
        }
        
        return MedicoDTO.medicoDTOMapper(medicoModel);
    }
    
    public MedicoDTO desativaMedico(int id) {
        try {
            this.connection = connectionFactory.getConnection();
            
            boolean isDesativado = this.medicoRepository.desativaMedico(id);
            if(!isDesativado)
                throw new ValidationException("Erro ao desativar: Não foi possivel encontrar esse médico");
            
            
            MedicoDTO retorno = new MedicoDTO();
            retorno.setId(id);
            retorno.setAtivo(false);
            
            connectionFactory.commit(connection);
            return retorno;
        } 
        catch (DataBaseException ex) {
            connectionFactory.rollback(connection);
            throw ex;
        } 
        finally {
            connectionFactory.closeConnection(connection);
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
    
}

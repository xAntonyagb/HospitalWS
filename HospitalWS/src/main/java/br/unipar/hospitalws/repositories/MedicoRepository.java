package br.unipar.hospitalws.repositories;

import br.unipar.hospitalws.enums.EspecialidadeEnum;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.infrastructure.ConnectionFactory;
import br.unipar.hospitalws.models.MedicoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MedicoRepository {

    private ConnectionFactory connectionFactory = new ConnectionFactory();
    private Connection connection = null;
    private PessoaRepository pessoaRepository = null;
    
    public MedicoRepository(Connection connection) throws SQLException {
        this.connection = connection;
        this.pessoaRepository = new PessoaRepository(this.connection);
    }
    
    public MedicoModel insertMedico(MedicoModel medicoModel) {
        String sql = "INSERT INTO tb_medico (id_pessoa, crm, id_especialidade, st_ativo) "
                + "VALUES (?, ?, ?, ?) ";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, medicoModel.getIdPessoa());
            ps.setString(2, medicoModel.getCRM());
            ps.setInt(3, medicoModel.getEspecialidade().getId());
            ps.setBoolean(4, true);
            
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            
            rs.next();
            medicoModel.setIdMedico(rs.getInt(1));
            
        } 
        catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        finally {
            connectionFactory.closeResultSet(rs);
            connectionFactory.closePreparedStatement(ps);
        }
        
        return medicoModel;
    }
    
    public MedicoModel getMedicoById(int id) {
        String sql = "SELECT * FROM tb_medico "
                + "WHERE id = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if(rs.next()) {
                return retornaMedicoInstance(rs);
            }
            
        } 
        catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        finally {
            connectionFactory.closeResultSet(rs);
            connectionFactory.closePreparedStatement(ps);
        }
        
        return null;
    }
    
    public ArrayList<MedicoModel> getAllMedicos() {
        String sql = "SELECT * FROM tb_medico";
        ArrayList<MedicoModel> listMedicos = new ArrayList<MedicoModel>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = this.connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                listMedicos.add(retornaMedicoInstance(rs));
            }
        }
        catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        finally {
            connectionFactory.closeResultSet(rs);
            connectionFactory.closePreparedStatement(ps);
        }
        
        return listMedicos;
    }
    
    public MedicoModel updateMedico(MedicoModel medicoModel) {
        String sql = "SELECT id_pessoa FROM tb_medico WHERE id = ?";
        PreparedStatement ps = null;
        
        try {
            ps = this.connection.prepareStatement(sql);
            ps.setInt(1, medicoModel.getIdMedico());
            
            ps.executeQuery();
        }
        catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        finally {
            connectionFactory.closePreparedStatement(ps);
        }
        
        medicoModel = (MedicoModel) this.pessoaRepository.updatePessoa(medicoModel);
        return medicoModel;
    }
    
    public boolean desativaMedico(int id) {
        String sql = "UPDATE tb_medico SET st_ativo = ? "
                + "WHERE id = ? ";
        
        PreparedStatement ps = null;
        
        try {
            ps = this.connection.prepareStatement(sql);
            
            ps.setBoolean(1, false);
            ps.setInt(2, id);
            
            int linhasDelete = ps.executeUpdate();
            return linhasDelete > 0;
        } 
        catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        finally {
           connectionFactory.closePreparedStatement(ps);
        }
    }
    
    public boolean isMedicoAtivo(int id) {
        String sql = "SELECT st_ativo FROM tb_medico "
                + "WHERE id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            rs.next();
            return rs.getBoolean("st_ativo");
            
        } 
        catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        finally {
            connectionFactory.closeResultSet(rs);
            connectionFactory.closePreparedStatement(ps);
        }
    }
    
    private MedicoModel retornaMedicoInstance(ResultSet rs) throws SQLException {
        MedicoModel medico = new MedicoModel();
        medico.setIdPessoa(rs.getInt(2));
        medico = (MedicoModel) this.pessoaRepository.getPessoaById(medico);
        medico.setIdMedico(rs.getInt(1));
        medico.setCRM(rs.getString(3));
        medico.setEspecialidade(EspecialidadeEnum.getEnumById(rs.getInt(4)));
        medico.setAtivo(rs.getBoolean(5));
        
        return medico;
    }
    
}

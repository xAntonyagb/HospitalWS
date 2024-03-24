package br.unipar.hospitalws.repositories;

import br.unipar.hospitalws.enums.EspecialidadeEnum;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.models.MedicoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MedicoRepository {

    private Connection connection;
    private PessoaRepository pessoaRepository;
    
    public MedicoRepository(Connection connection) throws SQLException {
        this.connection = connection;
        pessoaRepository = new PessoaRepository(connection);
        connection.setAutoCommit(false);
    }
    
    public MedicoModel insertMedico(MedicoModel medicoModel) {
        String sql = "INSERT INTO tb_medico (id_pessoa, crm, especialidade) "
                + "VALUES (?, ?, ?) ";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, medicoModel.getPessoaId());
            ps.setString(2, medicoModel.getCRM());
            ps.setString(3, medicoModel.getEspecialidade().getCodigo());
            ps.executeUpdate();
            
            rs = ps.getGeneratedKeys();
            
            rs.next();
            medicoModel.setPessoaId(rs.getInt(1));
            
            
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        
        return medicoModel;
        
    }
    
    public MedicoModel getMedicoById(int id) {
        String sql = "SELECT * FROM tb_medico "
                + "WHERE id = ?";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        MedicoModel medico = new MedicoModel();
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                medico.setmedicoId(rs.getInt(1));
                medico.setPessoaId(rs.getInt(2));
                medico.setCRM(rs.getString(3));
                medico.setEspecialidade(EspecialidadeEnum.getEnumByCodigo(rs.getString(4)));
                medico.setAtivo(rs.getBoolean(5));
            }
            
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        
        return medico;
    }
    
    public ArrayList<MedicoModel> getAllMedicos() {
        String sql = "SELECT * FROM tb_medico";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        ArrayList<MedicoModel> listMedicos = new ArrayList<MedicoModel>();
        
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                MedicoModel medico = new MedicoModel();
                
                medico.setmedicoId(rs.getInt(1));
                medico.setPessoaId(rs.getInt(2));
                medico.setCRM(rs.getString(3));
                medico.setEspecialidade(EspecialidadeEnum.getEnumByCodigo(rs.getString(4)));
                medico.setAtivo(rs.getBoolean(5));
                
                listMedicos.add(medico);
            }
            
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        
        return listMedicos;
    }
    
    public MedicoModel updateMedico(MedicoModel medicoModel) {
        pessoaRepository.updatePessoa(medicoModel);
        return medicoModel;
    }
    
    public void alteraStMedico(MedicoModel medicoModel) {
        String sql = "UPDATE tb_medico SET st_ativo = ? "
                + "WHERE id = ? ";
        
        PreparedStatement ps = null;
        
        try {
            ps = connection.prepareStatement(sql);
            
            ps.setBoolean(1, medicoModel.isAtivo());
            ps.setInt(2, medicoModel.getMedicoId());
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
    }
    
}

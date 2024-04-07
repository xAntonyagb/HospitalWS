package br.unipar.hospitalws.repositories;

import br.unipar.hospitalws.enums.EspecialidadeEnum;
import br.unipar.hospitalws.exceptions.DataBasePessoaException;
import br.unipar.hospitalws.infrastructure.ConnectionFactory;
import br.unipar.hospitalws.models.MedicoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MedicoRepository {

    private Connection connection = null;
    private PessoaRepository pessoaRepository = null;
    
    public MedicoRepository() {
        this.connection = ConnectionFactory.getConnection();
        this.pessoaRepository = new PessoaRepository();
    }
    
    public int insertMedico(MedicoModel medicoModel) throws SQLException {
        String sql = "INSERT INTO tb_medico (id_pessoa, crm, id_especialidade, st_ativo) "
                + "VALUES (?, ?, ?, ?) ";
        
        try (PreparedStatement ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, medicoModel.getIdPessoa());
            ps.setString(2, medicoModel.getCRM());
            ps.setInt(3, medicoModel.getEspecialidade().getId());
            ps.setBoolean(4, true);

            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if(rs.next()){
                    return rs.getInt(1);
                }
            }
        }
        
        return 0;
    }
    
    public MedicoModel getMedicoById(int id) throws SQLException, DataBasePessoaException {
        String sql = "SELECT * FROM tb_medico "
                + "WHERE id = ?";

        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return retornaMedicoInstance(rs);
                }
            }
        }
        
        return null;
    }
    
    public ArrayList<MedicoModel> getAllMedicos() throws SQLException, DataBasePessoaException {
        String sql = "SELECT * FROM tb_medico";
        ArrayList<MedicoModel> listMedicos = new ArrayList<MedicoModel>();
        
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    listMedicos.add(retornaMedicoInstance(rs));
                }
            }
        }
        
        return listMedicos;
    }
    
    public MedicoModel updateMedico(MedicoModel medicoModel) throws SQLException, DataBasePessoaException {
        String sql = "SELECT id_pessoa FROM tb_medico WHERE id = ?";
        
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setInt(1, medicoModel.getIdMedico());
            ps.executeQuery();
            
            medicoModel = (MedicoModel) this.pessoaRepository.updatePessoa(medicoModel);
        }

        return medicoModel;
    }
    
    public boolean desativaMedico(int id) throws SQLException {
        String sql = "UPDATE tb_medico SET st_ativo = ? "
                + "WHERE id = ? ";
        
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setBoolean(1, false);
            ps.setInt(2, id);

            int linhasDelete = ps.executeUpdate();
            return linhasDelete > 0;
        }
    }
    
    public boolean isMedicoAtivo(int id) throws SQLException {
        String sql = "SELECT st_ativo FROM tb_medico "
                + "WHERE id = ?";
        
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getBoolean("st_ativo");
            }
        }
    }
    
    private MedicoModel retornaMedicoInstance(ResultSet rs) throws SQLException, DataBasePessoaException {
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

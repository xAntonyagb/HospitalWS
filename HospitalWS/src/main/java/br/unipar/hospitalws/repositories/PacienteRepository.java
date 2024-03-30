package br.unipar.hospitalws.repositories;

import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.models.PacienteModel;

import java.sql.*;
import java.util.ArrayList;

public class PacienteRepository {

    private PessoaRepository pessoaRepository;
    private Connection connection;
    
    public PacienteRepository(Connection connection) throws SQLException {
        this.connection = connection;
        pessoaRepository = new PessoaRepository(connection);
    }
    
    public PacienteModel insertPaciente(PacienteModel pacienteModel) {
        String sql = "INSERT INTO tb_paciente (id_pessoa, st_ativo) VALUES(?, ?)";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, pacienteModel.getIdPessoa());
            ps.setBoolean(2, true);
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();

            rs.next();
            pacienteModel.setIdPaciente(rs.getInt(1));
            return pacienteModel;

        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
    }
    
    public PacienteModel getPacienteById(int id) {
        String sql = "SELECT * FROM tb_paciente "
                + "WHERE id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                PacienteModel pacienteModel = new PacienteModel();
                pacienteModel.setIdPessoa(rs.getInt("id_pessoa"));
                pacienteModel = (PacienteModel) pessoaRepository.getPessoaById(pacienteModel);

                pacienteModel.setAtivo(rs.getBoolean("st_ativo"));
                return pacienteModel;
            }
            
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }

        return null;
    }
    
    public ArrayList<PacienteModel> getAllPacientes() {
        String sql = "SELECT * FROM tb_paciente";
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<PacienteModel> pacientes = new ArrayList<>();
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()) {
                PacienteModel pacienteModel = new PacienteModel();
                pacienteModel.setIdPessoa(rs.getInt("id_pessoa"));
                pacienteModel = (PacienteModel) pessoaRepository.getPessoaById(pacienteModel);
                pacienteModel.setAtivo(rs.getBoolean("st_ativo"));
                
                pacientes.add(pacienteModel);
            }

        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        
        return pacientes;
    }
    
    public PacienteModel updatePaciente(PacienteModel pacienteModel) {
        String sql = "SELECT id_pessoa FROM tb_paciente WHERE id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, pacienteModel.getIdPaciente());
            rs = ps.executeQuery();
            
            if (rs.next()) {
                pacienteModel = new PacienteModel();
                pacienteModel.setIdPessoa(rs.getInt(1));
            }
            
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        
        pessoaRepository.updatePessoa(pacienteModel);
        
        return pacienteModel;
    }
    
    public int desativaPaciente(int id) throws SQLException{
        String sql = "UPDATE tb_paciente SET st_ativo = ? " +
                "WHERE id = ?";
        PreparedStatement ps = null;
        
        try {
            ps = connection.prepareStatement(sql);
            
            ps.setBoolean(1, false);
            ps.setInt(2, id);
            
            return ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
    }
    
}

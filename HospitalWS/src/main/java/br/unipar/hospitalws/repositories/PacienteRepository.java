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
        connection.setAutoCommit(false);
    }
    
    public PacienteModel insertPaciente(PacienteModel pacienteModel) {
        String sql = "INSERT INTO tb_paciente (id_pessoa, st_ativo) VALUES(?, ?)";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, pacienteModel.getPessoaId());
            ps.setBoolean(2, true);
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();

            rs.next();
            pacienteModel.setPacienteId(rs.getInt(1));


        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        return null;
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
                pacienteModel.setAtivo(rs.getBoolean("st_ativo"));
                pacientes.add(pacienteModel);
            }

        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        
        return pacientes;
    }
    
    public PacienteModel updatePaciente(PacienteModel pacienteModel) {
            pessoaRepository.updatePessoa(pacienteModel);

          return pacienteModel;
    }
    
    public void alteraStPaciente(PacienteModel pacienteModel) throws SQLException{
        String sql = "UPDATE tb_paciente SET st_ativo = ? " +
                "WHERE id = ?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setBoolean(1, pacienteModel.isAtivo());
            ps.setInt(2, pacienteModel.getPacienteId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
    }
    
}

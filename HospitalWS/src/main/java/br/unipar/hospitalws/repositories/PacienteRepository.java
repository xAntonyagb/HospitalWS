package br.unipar.hospitalws.repositories;

import br.unipar.hospitalws.exceptions.DataBasePessoaException;
import br.unipar.hospitalws.infrastructure.ConnectionFactory;
import br.unipar.hospitalws.models.PacienteModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepository {

    private PessoaRepository pessoaRepository;
    private Connection connection;
    
    public PacienteRepository() throws SQLException {
        this.connection = ConnectionFactory.getConnection();
        this.pessoaRepository = new PessoaRepository();
    }
    
    public int insertPaciente(PacienteModel pacienteModel) throws SQLException {
        String sql = "INSERT INTO tb_paciente (id_pessoa, st_ativo) VALUES(?, ?)";

        try (PreparedStatement ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, pacienteModel.getIdPessoa());
            ps.setBoolean(2, true);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if(rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    
    public PacienteModel getPacienteById(int id) throws SQLException, DataBasePessoaException {
        String sql = "SELECT * FROM tb_paciente "
                + "WHERE id = ?";
        
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PacienteModel pacienteModel = new PacienteModel();
                    pacienteModel.setIdPessoa(rs.getInt("id_pessoa"));
                    pacienteModel = (PacienteModel) pessoaRepository.getPessoaById(pacienteModel);
                    pacienteModel.setAtivo(rs.getBoolean("st_ativo"));
                    pacienteModel.setIdPaciente(id);

                    return pacienteModel;
                }

                return null;
            }
        }
    }
    
    public List<PacienteModel> getAllPacientes() throws SQLException, DataBasePessoaException {
        String sql = "SELECT * FROM tb_paciente";

        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {

                List<PacienteModel> pacientes = new ArrayList<>();
                while(rs.next()) {
                    PacienteModel pacienteModel = new PacienteModel();
                    pacienteModel.setIdPessoa(rs.getInt("id_pessoa"));
                    pacienteModel = (PacienteModel) pessoaRepository.getPessoaById(pacienteModel);
                    pacienteModel.setIdPaciente(rs.getInt("id"));
                    pacienteModel.setAtivo(rs.getBoolean("st_ativo"));

                    pacientes.add(pacienteModel);
                }

                return pacientes;
            }
        }
    }
    
    public PacienteModel updatePaciente(PacienteModel pacienteModel) throws SQLException, DataBasePessoaException {
        String sql = "SELECT id_pessoa FROM tb_paciente WHERE id = ?";
        
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setInt(1, pacienteModel.getIdPaciente());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pacienteModel.setIdPessoa(rs.getInt(1));
                }
            }
        }
        
        PacienteModel retorno = (PacienteModel) this.pessoaRepository.updatePessoa(pacienteModel);
        pacienteModel.getEndereco().setIdEndereco(retorno.getEndereco().getIdEndereco());
        
        EnderecoRepository enderecoRepository = new EnderecoRepository();
        enderecoRepository.updateEndereco(pacienteModel.getEndereco());
        
        return pacienteModel;
    }
    
    public boolean desativaPaciente(int id) throws SQLException{
        String sql = "UPDATE tb_paciente SET st_ativo = ? " +
                "WHERE id = ?";
        
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setBoolean(1, false);
            ps.setInt(2, id);

            int linhasDelete = ps.executeUpdate();
            return linhasDelete > 0;
        }
    }
        
    
    public boolean isPacienteAtivo(int id) throws SQLException {
        String sql = "SELECT st_ativo FROM tb_paciente "
                + "WHERE id = ?";
        
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setInt(1, id);
         
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return rs.getBoolean("st_ativo");
                }

                return false;
            }
        }
    }
    
}

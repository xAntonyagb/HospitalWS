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

        return null;
    }
    
    public PacienteModel getPacienteById(int id) {
        String sql = "SELECT * FROM tb_paciente"
                + "WHERE id = ?";

        
        return null;
    }
    
    public ArrayList<PacienteModel> getAllPacientes() {
        String sql = "SELECT * FROM tb_paciente";

        
        return null;
    }
    
    public PacienteModel updatePaciente(PacienteModel pacienteModel) {
        //Puxar metodo de update do PessoaRepository && manter return abaixo
        pessoaRepository.updatePessoa(pacienteModel);
        return pacienteModel;
    }
    
    public void alteraStPaciente(PacienteModel pacienteModel) throws SQLException{
        String sql = "UPDATE tb_paciente SET st_ativo = ?" +
                "WHERE id = ?";
    }
    
}

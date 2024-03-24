package br.unipar.hospitalws.repositories;

import br.unipar.hospitalws.models.PacienteModel;
import java.sql.Connection;
import java.sql.SQLException;
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
        String sql = "";
        
        return null;
    }
    
    public PacienteModel getPacienteById(int id) {
        String sql = "";

        
        return null;
    }
    
    public ArrayList<PacienteModel> getAllPacientes() {
        String sql = "";

        
        return null;
    }
    
    public PacienteModel updatePaciente(PacienteModel pacienteModel) {
        //Puxar metodo de update do PessoaRepository && manter return abaixo
        
        return pacienteModel;
    }
    
    public void alteraStPaciente(PacienteModel pacienteModel) throws SQLException{
        //Deixar st_ativo como pacienteModel.get ...
    }
    
}

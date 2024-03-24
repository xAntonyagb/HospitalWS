package br.unipar.hospitalws.repositories;

import br.unipar.hospitalws.models.PacienteModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PacienteRepository {

    Connection connection;
    
    public PacienteRepository(Connection connection) throws SQLException {
        this.connection = connection;
        connection.setAutoCommit(false);
    }
    
    public PacienteModel insertPaciente(PacienteModel pacienteModel) throws SQLException {
        return null;
    }
    
    public PacienteModel getPacienteById(int id) throws SQLException{
        return null;
    }
    
    public ArrayList<PacienteModel> getAllPacientes() {
        return null;
    }
    
    public PacienteModel updatePaciente(PacienteModel pacienteModel) throws SQLException{
        return null;
    }
    
    public void deletePacienteById(int id) throws SQLException{
        return;
    }
    
}

package br.unipar.hospitalws.repositories;

import br.unipar.hospitalws.models.MedicoModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedicoRepository {

    Connection connection;
    
    public MedicoRepository(Connection connection) {
        this.connection = connection;
    }
    
    public MedicoModel getMedicoResultSet(ResultSet rs) { 
        return null;
    }
    
    public MedicoModel insertMedico(MedicoModel medicoModel) throws SQLException {
        return null;
    }
    
    public MedicoModel getMedicoById(int id) throws SQLException{
        return null;
    }
    
    public ArrayList<MedicoModel> getAllMedicos() {
        return null;
    }
    
    public MedicoModel updateMedico(MedicoModel medicoModel) throws SQLException{
        return null;
    }
    
    public void deleteMedicoById(int id) throws SQLException{
        return;
    }
    
}

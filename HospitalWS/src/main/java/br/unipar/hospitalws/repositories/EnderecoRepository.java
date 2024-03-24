package br.unipar.hospitalws.repositories;

import br.unipar.hospitalws.models.EnderecoModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EnderecoRepository {

    Connection connection;
    
    public EnderecoRepository(Connection connection) throws SQLException {
        this.connection = connection;
        connection.setAutoCommit(false);
    }
    
    public EnderecoModel insertEndereco(EnderecoModel pacienteModel) throws SQLException {
        return null;
    }
    
    public EnderecoModel getEnderecoById(int id) throws SQLException{
        return null;
    }
    
    public ArrayList<EnderecoModel> getAllEnderecos() {
        return null;
    }
    
    public EnderecoModel updateEndereco(EnderecoModel pacienteModel) throws SQLException{
        return null;
    }
    
    public void deleteEnderecoById(int id) throws SQLException{
        return;
    }
    
}

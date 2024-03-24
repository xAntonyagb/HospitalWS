package br.unipar.hospitalws.repositories;

import br.unipar.hospitalws.models.EnderecoModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class EnderecoRepository {

    Connection connection;
    
    public EnderecoRepository(Connection connection) throws SQLException {
        this.connection = connection;
        connection.setAutoCommit(false);
    }
    
    public EnderecoModel insertEndereco(EnderecoModel pacienteModel) {
        String sql = "INSERT INTO tb_endereco (numero, complemento, bairro, cidade, uf, cep) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        
        return null;
    }
    
    public EnderecoModel getEnderecoById(int id) {
        String sql = "SELECT * FROM tb_endereco "
                + "WHERE id = ?";
        
        return null;
    }
    
    public ArrayList<EnderecoModel> getAllEnderecos() {
        String sql = "SELECT * FROM tb_endereco";
        
        return null;
    }
    
    public EnderecoModel updateEndereco(EnderecoModel pacienteModel) {
        String sql = "UPDATE tb_endereco SET "
                + "numero = ?, complemento = ?, bairro = ?, cidade = ?, uf = ?, cep = ? "
                + "WHERE id = ? ";
        
        return null;
    }
    
    public void deleteEnderecoById(int id) {
        String sql = "DELETE FROM tb_endereco "
                + "WHERE id = ?";
        
        return;
    }
    
}

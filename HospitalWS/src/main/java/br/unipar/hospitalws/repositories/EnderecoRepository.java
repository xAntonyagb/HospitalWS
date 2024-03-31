package br.unipar.hospitalws.repositories;

import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.models.EnderecoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EnderecoRepository {

    Connection connection;
    
    public EnderecoRepository(Connection connection) throws SQLException {
        this.connection = connection;
    }
    
    public EnderecoModel insertEndereco(EnderecoModel enderecoModel) {
        String sql = "INSERT INTO tb_endereco (numero, complemento, bairro, cidade, uf, cep) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, enderecoModel.getNumero());
            ps.setString(2, enderecoModel.getComplemento());
            ps.setString(3, enderecoModel.getBairro());
            ps.setString(4, enderecoModel.getCidade());
            ps.setString(5, enderecoModel.getUF());
            ps.setString(6, enderecoModel.getCEP());
            ps.executeUpdate();
            
            rs = ps.getGeneratedKeys();
            
            rs.next();
            enderecoModel.setIdEndereco(rs.getInt(1));
            
            
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        
        return enderecoModel;
    }
    
    public EnderecoModel getEnderecoById(int id) {
        String sql = "SELECT * FROM tb_endereco "
                + "WHERE id = ?";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if(rs.next()) {
                EnderecoModel endereco = new EnderecoModel();
                endereco.setIdEndereco(rs.getInt(1));
                endereco.setNumero(rs.getString(2));
                endereco.setComplemento(rs.getString(3));
                endereco.setBairro(rs.getString(4));
                endereco.setCidade(rs.getString(5));
                endereco.setUF(rs.getString(6));
                endereco.setCEP(rs.getString(7));
                
                return endereco;
            }
            
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        
        return null;
    }
    
    public ArrayList<EnderecoModel> getAllEnderecos() {
        String sql = "SELECT * FROM tb_endereco";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        ArrayList<EnderecoModel> listEnderecos = new ArrayList<EnderecoModel>();
        
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                EnderecoModel endereco = new EnderecoModel();
                
                endereco.setIdEndereco(rs.getInt(1));
                endereco.setNumero(rs.getString(2));
                endereco.setComplemento(rs.getString(3));
                endereco.setBairro(rs.getString(4));
                endereco.setCidade(rs.getString(5));
                endereco.setUF(rs.getString(6));
                endereco.setCEP(rs.getString(7));
                
                listEnderecos.add(endereco);
            }
            
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        
        return listEnderecos;
    }
    
    public EnderecoModel updateEndereco(EnderecoModel enderecoModel) {
        String sql = "UPDATE tb_endereco SET "
                + "numero = ?, complemento = ?, bairro = ?, cidade = ?, uf = ?, cep = ? "
                + "WHERE id = ? ";
        
        PreparedStatement ps = null;
        
        try {
            ps = connection.prepareStatement(sql);
            
            ps.setString(1, enderecoModel.getNumero());
            ps.setString(2, enderecoModel.getComplemento());
            ps.setString(3, enderecoModel.getBairro());
            ps.setString(4, enderecoModel.getCidade());
            ps.setString(5, enderecoModel.getUF());
            ps.setString(6, enderecoModel.getCEP());
            ps.setInt(7, enderecoModel.getIdEndereco());
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        
        return enderecoModel;
    }
    
    public int deleteEndereco(int id) {
        String sql = "DELETE FROM tb_endereco "
                + "WHERE id = ?";
        
        PreparedStatement ps = null;
        int retorno;
        
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            retorno = ps.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        
        return retorno;
    }
    
}

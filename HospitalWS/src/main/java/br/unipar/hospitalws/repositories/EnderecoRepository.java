package br.unipar.hospitalws.repositories;

import br.unipar.hospitalws.infrastructure.ConnectionFactory;
import br.unipar.hospitalws.models.EnderecoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EnderecoRepository {

    Connection connection = null;
    
    public EnderecoRepository() {
        this.connection = ConnectionFactory.getConnection();
    }
    
    public EnderecoModel insertEndereco(EnderecoModel enderecoModel) throws SQLException {
        String sql = "INSERT INTO tb_endereco (numero, complemento, bairro, cidade, uf, cep) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, enderecoModel.getNumero());
            ps.setString(2, enderecoModel.getComplemento());
            ps.setString(3, enderecoModel.getBairro());
            ps.setString(4, enderecoModel.getCidade());
            ps.setString(5, enderecoModel.getUF());
            ps.setString(6, enderecoModel.getCEP());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                enderecoModel.setIdEndereco(rs.getInt(1));

                return enderecoModel;
            }
        }
    }
    
    public EnderecoModel getEnderecoById(int id) throws SQLException {
        String sql = "SELECT * FROM tb_endereco "
                + "WHERE id = ?";
        
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
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
            }
        }
 
        return null;
    }
    
    public ArrayList<EnderecoModel> getAllEnderecos() throws SQLException {
        String sql = "SELECT * FROM tb_endereco";
        
        ArrayList<EnderecoModel> listEnderecos = new ArrayList<EnderecoModel>();
        
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {

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
                
                return listEnderecos;
            }
        }
    }
        
    public EnderecoModel updateEndereco(EnderecoModel enderecoModel) throws SQLException {
        String sql = "UPDATE tb_endereco SET "
                + "numero = ?, complemento = ?, bairro = ?, cidade = ?, uf = ?, cep = ? "
                + "WHERE id = ? ";
        
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setString(1, enderecoModel.getNumero());
            ps.setString(2, enderecoModel.getComplemento());
            ps.setString(3, enderecoModel.getBairro());
            ps.setString(4, enderecoModel.getCidade());
            ps.setString(5, enderecoModel.getUF());
            ps.setString(6, enderecoModel.getCEP());
            ps.setInt(7, enderecoModel.getIdEndereco());

            ps.executeUpdate();

            return enderecoModel;
        }
    }
    
    public int deleteEndereco(int id) throws SQLException {
        String sql = "DELETE FROM tb_endereco "
                + "WHERE id = ?";
        
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            return ps.executeUpdate();
        }
    }
    
}

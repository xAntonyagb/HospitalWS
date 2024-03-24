package br.unipar.hospitalws.repositories;

import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.models.PessoaModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PessoaRepository {

    private Connection connection;
    private final String[] colunas = {"id", "nome", "gmail", "telefone", "id_endereco", "cpf"};
    
    public PessoaRepository(Connection connection) throws SQLException {
        this.connection = connection;
        connection.setAutoCommit(false);
    }
    
    public PessoaModel insertPessoa(PessoaModel pessoaModel) {
        String sql = "INSERT INTO tb_livro (nome, gmail, telefone, id_endereco, cpf) VALUES(?, ?, ?, ?, ? )";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, pessoaModel.getNome());
            ps.setString(2, pessoaModel.getGmail());
            ps.setString(3, pessoaModel.getTelefone());
            ps.setInt(4, pessoaModel.getEndereco().getIdEndereco());
            ps.setString(5, pessoaModel.getCpf());
            ps.executeUpdate();
            
            rs = ps.getGeneratedKeys();
            
            rs.next();
            pessoaModel.setPessoaId(rs.getInt(1));
            
            
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        
        return pessoaModel;
    }
    
    public PessoaModel getPessoaById(int id) throws SQLException{
        return null;
    }
    
    public ArrayList<PessoaModel> getAllPessoas() {
        return null;
    }
    
    public PessoaModel updatePessoa(PessoaModel medicoModel) throws SQLException{
        return null;
    }
    
    public void deletePessoaById(int id) throws SQLException{
        return;
    }
    
}

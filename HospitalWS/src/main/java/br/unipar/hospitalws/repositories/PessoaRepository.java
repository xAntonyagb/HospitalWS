package br.unipar.hospitalws.repositories;

import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.models.PacienteModel;
import br.unipar.hospitalws.models.PessoaModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PessoaRepository {

    private Connection connection;
    private EnderecoRepository enderecoRepository;
    
    public PessoaRepository(Connection connection) throws SQLException {
        this.connection = connection;
        this.enderecoRepository = new EnderecoRepository(connection);
    }
    
    public PessoaModel insertPessoa(PessoaModel pessoaModel) {
        String sql = "INSERT INTO tb_pessoa (nome, gmail, telefone, id_endereco, cpf) VALUES(?, ?, ?, ?, ? )";
        
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
            pessoaModel.setIdPessoa(rs.getInt(1));
            
            
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        
        return pessoaModel;
    }
    
    public PessoaModel getPessoaById(PessoaModel pessoaModel) {
        String sql = "SELECT *  FROM tb_pessoa WHERE id = ?"; 
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = this.connection.prepareStatement(sql);
            ps.setInt(1, pessoaModel.getIdPessoa());
            rs = ps.executeQuery();
            
            while(rs.next()) {
                pessoaModel.setIdPessoa(rs.getInt(1));
                pessoaModel.setNome(rs.getString(2));
                pessoaModel.setGmail(rs.getString(3));
                pessoaModel.setTelefone(rs.getString(4));
                pessoaModel.setEndereco(this.enderecoRepository.getEnderecoById(rs.getInt(5)));
                pessoaModel.setCpf(rs.getString(6));
            }
            
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        
        return pessoaModel;
    }
    
    public ArrayList<PessoaModel> getAllPessoas() {
        String sql = "SELECT *  FROM tb_pessoa"; 
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        ArrayList<PessoaModel> listPessoas = new ArrayList<PessoaModel>();
        
        try {
            ps = this.connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                PessoaModel pessoa = new PacienteModel(); // :)

                pessoa.setIdPessoa(rs.getInt(1));
                pessoa.setNome(rs.getString(2));
                pessoa.setGmail(rs.getString(3));
                pessoa.setTelefone(rs.getString(4));
                pessoa.setEndereco(this.enderecoRepository.getEnderecoById(rs.getInt(5)));
                pessoa.setCpf(rs.getString(6));
                
                listPessoas.add(pessoa);
            }
            
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        
        return listPessoas;
    }
    
    public PessoaModel updatePessoa(PessoaModel pessoaModel) {
        String sql = "UPDATE tb_pessoa SET nome = ?, telefone = ? WHERE id = ?";
        PreparedStatement ps = null;
                
        try {
            ps = this.connection.prepareStatement(sql);
            
            ps.setString(1, pessoaModel.getNome());
            ps.setString(2, pessoaModel.getTelefone());
            ps.setInt(3, pessoaModel.getIdPessoa());
            this.enderecoRepository.updateEndereco(pessoaModel.getEndereco());
            
            ps.executeUpdate();
            this.connection.commit();
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        
        return pessoaModel;
    }
    
    public void deletePessoaById(int id) {
        String sql = "DELETE FROM tb_pessoa WHERE id = ?";
        PreparedStatement ps = null;
        
        try {
            ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
    }
    
}

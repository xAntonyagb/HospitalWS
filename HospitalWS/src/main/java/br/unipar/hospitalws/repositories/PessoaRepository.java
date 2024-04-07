package br.unipar.hospitalws.repositories;

import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.infrastructure.ConnectionFactory;
import br.unipar.hospitalws.models.PessoaModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PessoaRepository {

    private ConnectionFactory connectionFactory = new ConnectionFactory();
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
            ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, pessoaModel.getNome());
            ps.setString(2, pessoaModel.getGmail());
            ps.setString(3, pessoaModel.getTelefone());
            ps.setInt(4, pessoaModel.getEndereco().getIdEndereco());
            ps.setString(5, pessoaModel.getCpf());
            ps.executeUpdate();
            
            rs = ps.getGeneratedKeys();
            
            if(rs.next()) {
                pessoaModel.setIdPessoa(rs.getInt(1));
            }
        } 
        catch (SQLException ex) {
            throw new DataBaseException("(insertPessoa) "+ ex.getMessage());
        }
        finally {
            connectionFactory.closeResultSet(rs);
            connectionFactory.closePreparedStatement(ps);
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
            
            if(rs.next()) {
                pessoaModel = retornaPessoaInstance(rs, pessoaModel.getClass());
            }
        } 
        catch (SQLException ex) {
            throw new DataBaseException("(getPessoaById) "+ ex.getMessage());
        }
        finally {
            connectionFactory.closeResultSet(rs);
            connectionFactory.closePreparedStatement(ps);
        }
        
        return pessoaModel;
    }
    
    public ArrayList<PessoaModel> getAllPessoas(Class<? extends PessoaModel> tipoPessoa) {
        String sql = "SELECT *  FROM tb_pessoa"; 
        ArrayList<PessoaModel> listPessoas = new ArrayList<PessoaModel>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = this.connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                listPessoas.add(retornaPessoaInstance(rs, tipoPessoa));
            }
        } 
        catch (SQLException ex) {
            throw new DataBaseException("(getAllPessoas) "+ ex.getMessage());
        }
        finally {
            connectionFactory.closeResultSet(rs);
            connectionFactory.closePreparedStatement(ps);
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
            return getPessoaById(pessoaModel);
        } 
        catch (SQLException ex) {
            throw new DataBaseException("(updatePessoa) "+ ex.getMessage());
        }
        finally {
            connectionFactory.closePreparedStatement(ps);
        }
        
    }
    
    public void deletePessoaById(int id) {
        String sql = "DELETE FROM tb_pessoa WHERE id = ?";
        PreparedStatement ps = null;
        
        try {
            ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();
        } 
        catch (SQLException ex) {
            throw new DataBaseException("(deletePessoaById) "+ ex.getMessage());
        }
        finally {
            connectionFactory.closePreparedStatement(ps);
        }
    }
    
    private PessoaModel retornaPessoaInstance(ResultSet rs, Class<? extends PessoaModel> tipoPessoa) {
        try {
            PessoaModel pessoa = tipoPessoa.getDeclaredConstructor().newInstance();
            pessoa.setIdPessoa(rs.getInt(1));
            pessoa.setNome(rs.getString(2));
            pessoa.setGmail(rs.getString(3));
            pessoa.setTelefone(rs.getString(4));
            pessoa.setEndereco(this.enderecoRepository.getEnderecoById(rs.getInt(5)));
            pessoa.setCpf(rs.getString(6));
            return pessoa;
        }
        catch (Exception ex) {
            throw new DataBaseException("erro ao obter retorno de pessoa");
        }
    }
    
}

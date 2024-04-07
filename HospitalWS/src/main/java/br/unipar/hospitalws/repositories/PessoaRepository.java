package br.unipar.hospitalws.repositories;

import br.unipar.hospitalws.exceptions.DataBasePessoaException;
import br.unipar.hospitalws.infrastructure.ConnectionFactory;
import br.unipar.hospitalws.models.PessoaModel;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PessoaRepository {

    private Connection connection;
    private EnderecoRepository enderecoRepository;
    
    public PessoaRepository() {
        this.connection = ConnectionFactory.getConnection();
        this.enderecoRepository = new EnderecoRepository();
    }
    
    public PessoaModel insertPessoa(PessoaModel pessoaModel) throws SQLException {
        String sql = "INSERT INTO tb_pessoa (nome, gmail, telefone, id_endereco, cpf) VALUES(?, ?, ?, ?, ? )";
        
        try (PreparedStatement ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, pessoaModel.getNome());
            ps.setString(2, pessoaModel.getGmail());
            ps.setString(3, pessoaModel.getTelefone());
            ps.setInt(4, pessoaModel.getEndereco().getIdEndereco());
            ps.setString(5, pessoaModel.getCpf());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if(rs.next()) {
                    pessoaModel.setIdPessoa(rs.getInt(1));
                }

                return pessoaModel;
            }
        }
    }
    
    public PessoaModel getPessoaById(PessoaModel pessoaModel) throws SQLException, DataBasePessoaException {
        String sql = "SELECT *  FROM tb_pessoa WHERE id = ?"; 
        
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setInt(1, pessoaModel.getIdPessoa());
            
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    pessoaModel = retornaPessoaInstance(rs, pessoaModel.getClass());
                }

                return pessoaModel;
            }
        }
    }
    
    public ArrayList<PessoaModel> getAllPessoas(Class<? extends PessoaModel> tipoPessoa) throws SQLException, DataBasePessoaException {
        String sql = "SELECT *  FROM tb_pessoa"; 
        ArrayList<PessoaModel> listPessoas = new ArrayList<PessoaModel>();
        
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    listPessoas.add(retornaPessoaInstance(rs, tipoPessoa));
                }

                return listPessoas;
            }
        }
    }
    
    public PessoaModel updatePessoa(PessoaModel pessoaModel) throws SQLException, DataBasePessoaException {
        String sql = "UPDATE tb_pessoa SET nome = ?, telefone = ? WHERE id = ?";
                
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setString(1, pessoaModel.getNome());
            ps.setString(2, pessoaModel.getTelefone());
            ps.setInt(3, pessoaModel.getIdPessoa());
            this.enderecoRepository.updateEndereco(pessoaModel.getEndereco());

            ps.executeUpdate();
            return getPessoaById(pessoaModel);
        }
    }
    
    public void deletePessoaById(int id) throws SQLException {
        String sql = "DELETE FROM tb_pessoa WHERE id = ?";
        
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    private PessoaModel retornaPessoaInstance(ResultSet rs, Class<? extends PessoaModel> tipoPessoa) throws SQLException, DataBasePessoaException {
        try {
            PessoaModel pessoa = tipoPessoa.getDeclaredConstructor().newInstance();
            pessoa.setIdPessoa(rs.getInt(1));
            pessoa.setNome(rs.getString(2));
            pessoa.setGmail(rs.getString(3));
            pessoa.setTelefone(rs.getString(4));
            pessoa.setEndereco(this.enderecoRepository.getEnderecoById(rs.getInt(5)));
            pessoa.setCpf(rs.getString(6));
            
            return pessoa;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new DataBasePessoaException();
        }
    }
    
}

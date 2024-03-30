package br.unipar.hospitalws.services;

import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.infrastructure.ConnectionFactory;
import br.unipar.hospitalws.models.PessoaModel;
import br.unipar.hospitalws.repositories.PessoaRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PessoaService {
    
    private ConnectionFactory connectionFactory = new ConnectionFactory();
    private Connection connection = null;
    private PessoaRepository pessoaRepository = null;
    
    
    public static void validaPessoa(PessoaModel pessoa) {
        if(pessoa.getCpf().length() != 11) {
            throw new ValidationException("CPF inválido! Informe um CPF com 11 digitos ("+ pessoa.getCpf().length() +")");
        }
        if(pessoa.getNome() == null){
            throw new ValidationException("Nome inválido! Porfavor informe algum nome");
        }
        if(pessoa.getGmail() == null){
            throw new ValidationException("E-mail inválido! Porfavor informe algum e-mail");
        }
        if(pessoa.getTelefone().length() < 9 || pessoa.getTelefone() == null){
            throw new ValidationException("Telefone inválido! Porfavor informe um telefone com 9 digitos ("+ pessoa.getTelefone().length() +")");
        }
    }
    
    public PessoaModel insertPessoa(PessoaModel pessoaModel, boolean validar) {
        if(validar) {
            validaPessoa(pessoaModel);
        }
        
        PessoaModel retorno = null;
        try {
            connection = connectionFactory.getConnection();
            connection.setAutoCommit(false);
            pessoaRepository = new PessoaRepository(connection);

            retorno = pessoaRepository.insertPessoa(pessoaModel);
        } 
        catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        } 
        finally {
            if(connection != null)
                connectionFactory.closeConnection(connection);
        }
        
        return retorno;
    }
    
    public PessoaModel getPessoaById(PessoaModel pessoaModel) {
        PessoaModel retorno = null;
        
        try {
            connection = connectionFactory.getConnection();
            connection.setAutoCommit(false);
            pessoaRepository = new PessoaRepository(connection);

            retorno = pessoaRepository.getPessoaById(pessoaModel);
        } 
        catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        finally {
            if(connection != null)
                connectionFactory.closeConnection(connection);
        }
        
        return retorno;
    }
    
    public ArrayList<PessoaModel> getAllPessoas() {
        ArrayList<PessoaModel> retorno = null;
        
        try {
            connection = connectionFactory.getConnection();
            connection.setAutoCommit(false);
            pessoaRepository = new PessoaRepository(connection);

            retorno = pessoaRepository.getAllPessoas();
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        finally {
            if(connection != null)
                connectionFactory.closeConnection(connection);
        }
        
        return retorno;
    }
    
    public PessoaModel updatePessoa(PessoaModel pessoaModel, boolean validar) {
        if(validar){
            validaPessoa(pessoaModel);
        }
        
        PessoaModel retorno = null;
        try {
            connection = connectionFactory.getConnection();
            connection.setAutoCommit(false);
            pessoaRepository = new PessoaRepository(connection);

            retorno = pessoaRepository.updatePessoa(pessoaModel);
        } catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        finally {
            if(connection != null)
                connectionFactory.closeConnection(connection);
        }
        
        return retorno;
    }
    
    public void deletePessoaById(int id) {
        try {
            connection = connectionFactory.getConnection();
            connection.setAutoCommit(false);
            pessoaRepository = new PessoaRepository(connection);

            pessoaRepository.deletePessoaById(id);
        } 
        catch (SQLException ex) {
            throw new DataBaseException(ex.getMessage());
        }
        finally {
            if(connection != null)
                connectionFactory.closeConnection(connection);
        }
    }
    
}

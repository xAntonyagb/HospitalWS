package br.unipar.hospitalws.services;

import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.InternalException;
import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.infrastructure.ConnectionFactory;
import br.unipar.hospitalws.models.PessoaModel;
import br.unipar.hospitalws.repositories.EnderecoRepository;
import br.unipar.hospitalws.repositories.PessoaRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PessoaService {
    
    private Connection connection = null;
    private PessoaRepository pessoaRepository = null;
    private EnderecoRepository enderecoRepository = null;
    
    private final Logger logger = Logger.getLogger("PessoaService");

    
    public PessoaModel insertPessoa(PessoaModel pessoaModel) {
        try {
            validaInsertPessoa(pessoaModel);
            
            this.connection = ConnectionFactory.getConnection();
            this.pessoaRepository = new PessoaRepository();
            this.enderecoRepository = new EnderecoRepository();
            
            this.enderecoRepository.insertEndereco(pessoaModel.getEndereco());
            PessoaModel retorno = this.pessoaRepository.insertPessoa(pessoaModel);
            
            ConnectionFactory.commit();
            
            return retorno;
        } 
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(insertPessoa) "+ ex.getMessage());
            throw new DataBaseException("erro ao inserir uma nova pessoa.");
        }
        catch(ValidationException ex) {
            logger.log(Level.INFO, "(insertPessoa) Requisicao foi rejeitada pelo processo de validacao "+ ex.getMessage());
            throw ex;
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(insertPessoa) Um erro inesperado aconteceu: Nao foi possivel finalizar a execução desse metodo. "+ ex.getMessage());
            throw new InternalException("insertPessoa");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
        
    }
    
    public PessoaModel getPessoaById(PessoaModel pessoaModel) {
        try {
            this.connection = ConnectionFactory.getConnection();
            this.pessoaRepository = new PessoaRepository();

            PessoaModel retorno = this.pessoaRepository.getPessoaById(pessoaModel);
            ConnectionFactory.commit();
            
            return retorno;
        } 
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(getPessoaById) "+ ex.getMessage());
            throw new DataBaseException("erro ao pesquisar pessoa.");
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(getPessoaById) Um erro inesperado aconteceu: Nao foi possivel finalizar a execução desse metodo. "+ ex.getMessage());
            throw new InternalException("getPessoaById");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
    }
    
    public ArrayList<PessoaModel> getAllPessoas(Class<? extends PessoaModel> tipoPessoa) {
        try {
            this.connection = ConnectionFactory.getConnection();
            this.pessoaRepository = new PessoaRepository();
            ArrayList<PessoaModel> retorno = null;
            
            retorno = this.pessoaRepository.getAllPessoas(tipoPessoa);
            ConnectionFactory.commit();
            
            return retorno;
        } 
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(getAllPessoas) "+ ex.getMessage());
            throw new DataBaseException("erro ao pesquisar pessoas.");
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(getAllPessoas) Um erro inesperado aconteceu: Nao foi possivel finalizar a execução desse metodo. "+ ex.getMessage());
            throw new InternalException("getAllPessoas");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
        
    }
    
    public PessoaModel updatePessoa(PessoaModel pessoaModel) {
        try {
            validaUpdatePessoa(pessoaModel);
            
            this.connection = ConnectionFactory.getConnection();
            this.pessoaRepository = new PessoaRepository();

            PessoaModel retorno = this.pessoaRepository.updatePessoa(pessoaModel);
            ConnectionFactory.commit();
            
            return retorno;
        } 
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(updatePessoa) "+ ex.getMessage());
            throw new DataBaseException("erro ao atualizar pessoa.");
        }
        catch(ValidationException ex) {
            logger.log(Level.INFO, "(updatePessoa) Requisicao foi rejeitada pelo processo de validacao "+ ex.getMessage());
            throw ex;
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(updatePessoa) Um erro inesperado aconteceu: Nao foi possivel finalizar a execução desse metodo. "+ ex.getMessage());
            throw new InternalException("updatePessoa");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
    }
    
    public void deletePessoaById(int id) {
        try {
            this.connection = ConnectionFactory.getConnection();
            this.pessoaRepository = new PessoaRepository();

            this.pessoaRepository.deletePessoaById(id);
            ConnectionFactory.commit();
        } 
        catch(SQLException ex) {
            logger.log(Level.SEVERE, "(deletePessoaById) "+ ex.getMessage());
            throw new DataBaseException("erro ao deletar o registro de pessoa.");
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, "(deletePessoaById) Um erro inesperado aconteceu: Nao foi possivel finalizar a execução desse metodo. "+ ex.getMessage());
            throw new InternalException("deletePessoaById");
        }
        finally {
            ConnectionFactory.closeConnection();
        }
    }
    
        
    public static void validaInsertPessoa(PessoaModel pessoa) {
        EnderecoService.validaEndereco(pessoa.getEndereco());
        
        if(pessoa.getCpf() == null || pessoa.getCpf().length() != 11) {
            throw new ValidationException("CPF inválido! Informe um CPF com 11 digitos ("+ pessoa.getCpf().length() +")");
        }
        if(pessoa.getNome() == null) {
            throw new ValidationException("Nome inválido! Porfavor informe algum nome");
        }
        if(pessoa.getGmail() == null) {
            throw new ValidationException("E-mail inválido! Porfavor informe algum e-mail");
        }
        if(pessoa.getTelefone() == null || pessoa.getTelefone().length() < 9) {
            throw new ValidationException("Telefone inválido! Porfavor informe um telefone com 9 digitos ("+ pessoa.getTelefone().length() +")");
        }
    }
    
    public static void validaUpdatePessoa(PessoaModel pessoa) {
        EnderecoService.validaEndereco(pessoa.getEndereco());
        
        if(pessoa.getCpf() != null) {
            throw new ValidationException("CPF informado! Voce não pode atualizar o CPF de um cadastro já feito.");
        }
        if(pessoa.getNome() == null) {
            throw new ValidationException("Nome inválido! Porfavor informe algum nome");
        }
        if(pessoa.getGmail() != null) {
            throw new ValidationException("E-mail informado! Você não pode atualizar o e-mail de um cadastro já feito.");
        }
        if(pessoa.getTelefone() == null || pessoa.getTelefone().length() < 9) {
            throw new ValidationException("Telefone inválido! Porfavor informe um telefone com 9 digitos ("+ pessoa.getTelefone().length() +")");
        }
    }
    
}

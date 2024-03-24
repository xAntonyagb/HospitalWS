package br.unipar.hospitalws.repositories;

import br.unipar.hospitalws.models.MedicoModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedicoRepository {

    private Connection connection;
    private PessoaRepository pessoaRepository;
    
    public MedicoRepository(Connection connection) throws SQLException {
        this.connection = connection;
        pessoaRepository = new PessoaRepository(connection);
        connection.setAutoCommit(false);
    }
    
    public MedicoModel insertMedico(MedicoModel medicoModel) {
        String sql = "INSERT INTO tb_medico (id_pessoa, crm, especialidade) "
                + "VALUES (?, ?, ?) ";
        
        
        return null;
    }
    
    public MedicoModel getMedicoById(int id) {
        String sql = "SELECT * FROM tb_medico "
                + "WHERE id = ?";
        
        return null;
    }
    
    public ArrayList<MedicoModel> getAllMedicos() {
        String sql = "SELECT * FROM tb_medico";
        
        return null;
    }
    
    public MedicoModel updateMedico(MedicoModel medicoModel) {
        pessoaRepository.updatePessoa(medicoModel);
        return medicoModel;
    }
    
    public void alteraStMedico(MedicoModel medicoModel) {
        String sql = "UPDATE tb_medico SET st_ativo = ? "
                + "WHERE id = ? ";
        
        return;
    }
    
}

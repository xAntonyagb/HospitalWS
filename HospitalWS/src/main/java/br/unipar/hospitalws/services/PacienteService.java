package br.unipar.hospitalws.services;

import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.infrastructure.ConstructionFactory;
import br.unipar.hospitalws.models.PacienteModel;
import br.unipar.hospitalws.repositories.PacienteRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PacienteService {
    
    private final Connection connection = new ConstructionFactory().getConnection();
    private final PacienteRepository pacienteRepository = new PacienteRepository(connection);
    
    public PacienteModel insertPaciente(PacienteModel PacienteModel) throws SQLException {
        return pacienteRepository.insertPaciente(PacienteModel);
    }
    
    public PacienteModel getPacienteById(int id) throws SQLException{
        return pacienteRepository.getPacienteById(id);
    }
    
    public ArrayList<PacienteModel> getAllPacientes() {
        return pacienteRepository.getAllPacientes();
    }
    
    public PacienteModel updatePaciente(PacienteModel PacienteModel) throws SQLException{
        
        if(PacienteModel.getCpf() != null) {
            throw new ValidationException("Não se pode atualizar o cpf de um paciente!");
        }
        if(PacienteModel.getNome().length() < 0
                || PacienteModel.getNome().isEmpty()
                || PacienteModel.getNome() == null){
            throw new ValidationException("Nome inválido! Porfavor informe algum nome");
        }
        if(PacienteModel.getGmail() != null){
            throw new ValidationException("Não se pode atualizar o e-mail de um paciente!");
        }
        if(PacienteModel.getTelefone().length() < 0
                || PacienteModel.getTelefone().length() < 9
                || PacienteModel.getTelefone().isEmpty()
                || PacienteModel.getTelefone() == null){
            throw new ValidationException("Telefone inválido! Porfavor informe um telefone válido");
        }
        
        EnderecoService.validaEndereco(PacienteModel.getEndereco());
        
        return pacienteRepository.updatePaciente(PacienteModel);
    }
    
    public void deletePacienteById(int id) throws SQLException{
        pacienteRepository.deletePacienteById(id);
    }
}

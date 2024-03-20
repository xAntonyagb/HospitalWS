package br.unipar.hospitalws.services;

import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.infrastructure.ConstructionFactory;
import br.unipar.hospitalws.models.MedicoModel;
import br.unipar.hospitalws.repositories.MedicoRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedicoService {
    
    private final Connection connection = new ConstructionFactory().getConnection();
    private final MedicoRepository medicoRepository = new MedicoRepository(connection);
    
    public MedicoModel insertMedico(MedicoModel medicoModel) throws SQLException {
        if(Integer.toString(medicoModel.getCRM()).length() != 12) {
            throw new ValidationException("CRM inválido! Informe um CRM com 12 digitos");
        }
        if(medicoModel.getCpf().length() != 11) {
            throw new ValidationException("CPF inválido! Informe um CPF com 11 digitos");
        }
        if(medicoModel.getNome().length() < 0
                || medicoModel.getNome().isEmpty()
                || medicoModel.getNome() == null){
            throw new ValidationException("Nome inválido! Porfavor informe algum nome");
        }
        if(medicoModel.getGmail().length() < 0
                || medicoModel.getGmail().isEmpty()
                || medicoModel.getGmail() == null){
            throw new ValidationException("E-mail inválido! Porfavor informe algum e-mail");
        }
        if(medicoModel.getGmail().length() < 0
                || medicoModel.getGmail().isEmpty()
                || medicoModel.getGmail() == null){
            throw new ValidationException("E-mail inválido! Porfavor informe algum e-mail");
        }
        if(medicoModel.getTelefone().length() < 0
                || medicoModel.getTelefone().length() < 9
                || medicoModel.getTelefone().isEmpty()
                || medicoModel.getTelefone() == null){
            throw new ValidationException("Telefone inválido! Porfavor informe um telefone válido");
        }
        if(medicoModel.getEspecialidade() == null){
            throw new ValidationException("Especialidade inválida! Porfavor informe um tipo de especialidade válida");
        }
        
        EnderecoService.validaEndereco(medicoModel.getEndereco());
        
        return medicoRepository.insertMedico(medicoModel);
    }
    
    public MedicoModel getMedicoById(int id) throws SQLException{
        return medicoRepository.getMedicoById(id);
    }
    
    public ArrayList<MedicoModel> getAllMedicos() {
        return medicoRepository.getAllMedicos();
    }
    
    public MedicoModel updateMedico(MedicoModel medicoModel) throws SQLException{
        if(medicoModel.getGmail() != null) {
            throw new ValidationException("Você não pode atualizar o email de um médico!");
        }
        if(medicoModel.getEspecialidade()!= null) {
            throw new ValidationException("Você não pode atualizar a especialidade de um médico!");
        }
        if(medicoModel.getCRM() != 0) {
            throw new ValidationException("Você não pode atualizar o CRM de um médico!");
        }
        return medicoRepository.updateMedico(medicoModel);
    }
    
    public void deleteMedicoById(int id) throws SQLException{
        medicoRepository.deleteMedicoById(id);
    }
    
}

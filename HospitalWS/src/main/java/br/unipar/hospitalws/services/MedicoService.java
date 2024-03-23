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
        
        PessoaService.validaPessoa(medicoModel);
        
        if(Integer.toString(medicoModel.getCRM()).length() != 12) {
            throw new ValidationException("CRM inválido! Informe um CRM com 12 digitos");
        }
        if(medicoModel.getEspecialidade() == null){
            throw new ValidationException("Especialidade inválida! Porfavor informe um tipo de especialidade válida");
        }
        
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

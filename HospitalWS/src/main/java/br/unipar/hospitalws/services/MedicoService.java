package br.unipar.hospitalws.services;

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
        return medicoRepository.insertMedico(medicoModel);
    }
    
    public MedicoModel getMedicoById(int id) throws SQLException{
        return medicoRepository.getMedicoById(id);
    }
    
    public ArrayList<MedicoModel> getAllMedicos() {
        return medicoRepository.getAllMedicos();
    }
    
    public MedicoModel updateMedico(MedicoModel medicoModel) throws SQLException{
        return medicoRepository.updateMedico(medicoModel);
    }
    
    public void deleteMedicoById(int id) throws SQLException{
        medicoRepository.deleteMedicoById(id);
    }
    
}

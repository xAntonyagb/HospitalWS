package br.unipar.hospitalws;

import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.interfaces.MedicoWebService;
import br.unipar.hospitalws.models.MedicoModel;
import br.unipar.hospitalws.services.MedicoService;
import jakarta.jws.WebService;
import java.util.ArrayList;

@WebService(endpointInterface = "br.unipar.hospitalws.interfaces.MedicoWebService")
public class MedicoWebServiceImp implements MedicoWebService{
    
    private static MedicoService medicoService = new MedicoService();

    @Override
    public MedicoModel insertMedico(MedicoModel medicoModel) throws ValidationException {
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
        
        
        return null;
    }

    @Override
    public MedicoModel getMedicoById(int id) throws ValidationException {
        return null;
    }

    @Override
    public ArrayList<MedicoModel> getAllMedicos() throws ValidationException {
        return null;
    }

    @Override
    public MedicoModel updateMedico(MedicoModel medicoModel) throws ValidationException {
        return null;
    }

    @Override
    public void deleteMedicoById(int id) throws ValidationException {
        return;
    }
    
}

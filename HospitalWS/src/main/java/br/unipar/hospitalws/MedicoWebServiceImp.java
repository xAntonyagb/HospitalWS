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
    public MedicoModel insertMedico() throws ValidationException {
        return null;
    }

    @Override
    public ArrayList<MedicoModel> getMedicoById() throws ValidationException {
        return null;
    }

    @Override
    public ArrayList<MedicoModel> getAllMedicos() throws ValidationException {
        return null;
    }

    @Override
    public MedicoModel updateMedico() throws ValidationException {
        return null;
    }

    @Override
    public void deleteMedicoById() throws ValidationException {
        return;
    }
    
}

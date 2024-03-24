package br.unipar.hospitalws;

import br.unipar.hospitalws.exceptions.DataBaseException;
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
    public MedicoModel insertMedico(MedicoModel medicoModel) throws ValidationException, DataBaseException {
        return null;
    }

    @Override
    public MedicoModel getMedicoById(int id) throws ValidationException, DataBaseException {
        return null;
    }

    @Override
    public ArrayList<MedicoModel> getAllMedicos() throws ValidationException, DataBaseException {
        return null;
    }

    @Override
    public MedicoModel updateMedico(MedicoModel medicoModel) throws ValidationException, DataBaseException {
        return null;
    }

    @Override
    public void deleteMedicoById(int id) throws ValidationException, DataBaseException {
        return;
    }
    
}

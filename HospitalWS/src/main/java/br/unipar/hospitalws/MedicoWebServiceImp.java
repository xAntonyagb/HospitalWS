package br.unipar.hospitalws;

import br.unipar.hospitalws.DTO.MedicoDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.InternalException;
import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.interfaces.MedicoWebService;
import br.unipar.hospitalws.services.MedicoService;
import jakarta.jws.WebService;
import java.util.ArrayList;

@WebService(endpointInterface = "br.unipar.hospitalws.interfaces.MedicoWebService")
public class MedicoWebServiceImp implements MedicoWebService{
    
    private static MedicoService medicoService = new MedicoService();

    @Override
    public MedicoDTO insertMedico(MedicoDTO medico) throws ValidationException, DataBaseException, InternalException {
        return medicoService.insertMedico(medico);
    }

    @Override
    public MedicoDTO getMedicoById(int id) throws ValidationException, DataBaseException, InternalException {
        return medicoService.getMedicoById(id);
    }

    @Override
    public ArrayList<MedicoDTO> getAllMedicos() throws ValidationException, DataBaseException, InternalException {
        return medicoService.getAllMedicos();
    }

    @Override
    public MedicoDTO updateMedico(MedicoDTO medico) throws ValidationException, DataBaseException, InternalException {
        return medicoService.updateMedico(medico);
    }

    @Override
    public MedicoDTO desativaMedico(int id) throws ValidationException, DataBaseException, InternalException {
        return medicoService.desativaMedico(id);
    }
    
}

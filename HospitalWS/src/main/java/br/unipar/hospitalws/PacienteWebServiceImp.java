package br.unipar.hospitalws;

import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.interfaces.PacienteWebService;
import br.unipar.hospitalws.models.PacienteModel;
import br.unipar.hospitalws.services.PacienteService;
import jakarta.jws.WebService;
import java.util.ArrayList;

@WebService(endpointInterface = "br.unipar.hospitalws.interfaces.PacienteWebService")
public class PacienteWebServiceImp implements PacienteWebService{
    
    private static PacienteService pacienteService = new PacienteService();

    @Override
    public PacienteModel insertPaciente() throws ValidationException {
        return null;
    }

    @Override
    public ArrayList<PacienteModel> getPacienteById() throws ValidationException {
        return null;
    }

    @Override
    public ArrayList<PacienteModel> getAllPacientes() throws ValidationException {
        return null;
    }

    @Override
    public PacienteModel updatePaciente() throws ValidationException {
        return null;
    }

    @Override
    public void deletePacienteById() throws ValidationException {
        return;
    }
    
}

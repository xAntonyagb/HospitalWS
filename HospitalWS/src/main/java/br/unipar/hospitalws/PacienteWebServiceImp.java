package br.unipar.hospitalws;

import br.unipar.hospitalws.DTO.PacienteDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.InternalException;
import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.interfaces.PacienteWebService;
import br.unipar.hospitalws.services.PacienteService;
import jakarta.jws.WebService;
import java.util.List;

@WebService(endpointInterface = "br.unipar.hospitalws.interfaces.PacienteWebService")
public class PacienteWebServiceImp implements PacienteWebService{
    
    private static PacienteService pacienteService = new PacienteService();

    @Override
    public PacienteDTO insertPaciente(PacienteDTO paciente) throws ValidationException, DataBaseException, InternalException {
        return pacienteService.insertPaciente(paciente);
    }

    @Override
    public PacienteDTO getPacienteById(int id) throws ValidationException, DataBaseException, InternalException {
        return pacienteService.getPacienteById(id);
    }

    @Override
    public List<PacienteDTO> getAllPacientes() throws ValidationException, DataBaseException, InternalException {
        return pacienteService.getAllPacientes();
    }

    @Override
    public PacienteDTO updatePaciente(PacienteDTO paciente) throws ValidationException, DataBaseException, InternalException {
        return pacienteService.updatePaciente(paciente);
    }

    @Override
    public PacienteDTO desativaPaciente(int id) throws ValidationException, DataBaseException, InternalException {
        return pacienteService.desativaPaciente(id);
    }
    
}

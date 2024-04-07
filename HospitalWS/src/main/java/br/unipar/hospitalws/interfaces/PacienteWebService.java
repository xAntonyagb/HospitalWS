package br.unipar.hospitalws.interfaces;

import br.unipar.hospitalws.DTO.PacienteDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.InternalException;
import br.unipar.hospitalws.exceptions.ValidationException;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.util.List;

@WebService()
public interface PacienteWebService {
    
    @WebMethod()
    PacienteDTO insertPaciente(PacienteDTO paciente) throws ValidationException, DataBaseException, InternalException;
    
    @WebMethod()
    PacienteDTO getPacienteById(int id) throws ValidationException, DataBaseException, InternalException;
    
    @WebMethod()
    List<PacienteDTO> getAllPacientes() throws ValidationException, DataBaseException, InternalException;
    
    @WebMethod()
    PacienteDTO updatePaciente(PacienteDTO paciente) throws ValidationException, DataBaseException, InternalException;
    
    @WebMethod()
    PacienteDTO desativaPaciente(int id) throws ValidationException, DataBaseException, InternalException;
    
}

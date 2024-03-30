package br.unipar.hospitalws.interfaces;

import br.unipar.hospitalws.DTO.PacienteDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.ValidationException;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.util.ArrayList;

@WebService()
public interface PacienteWebService {
    
    @WebMethod()
    PacienteDTO insertPaciente(PacienteDTO paciente) throws ValidationException, DataBaseException;
    
    @WebMethod()
    PacienteDTO getPacienteById(int id) throws ValidationException, DataBaseException;
    
    @WebMethod()
    ArrayList<PacienteDTO> getAllPacientes() throws ValidationException, DataBaseException;
    
    @WebMethod()
    PacienteDTO updatePaciente(PacienteDTO paciente) throws ValidationException, DataBaseException;
    
    @WebMethod()
    PacienteDTO desativaPaciente(int id) throws ValidationException, DataBaseException;
    
}

package br.unipar.hospitalws.interfaces;

import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.models.PacienteModel;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.util.ArrayList;

@WebService()
public interface PacienteWebService {
    
    @WebMethod()
    PacienteModel insertPaciente() throws ValidationException;
    
    @WebMethod()
    ArrayList<PacienteModel> getPacienteById() throws ValidationException;
    
    @WebMethod()
    ArrayList<PacienteModel> getAllPacientes() throws ValidationException;
    
    @WebMethod()
    PacienteModel updatePaciente() throws ValidationException;
    
    @WebMethod()
    void deletePacienteById() throws ValidationException;
    
}

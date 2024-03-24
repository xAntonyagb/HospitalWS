package br.unipar.hospitalws.interfaces;

import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.models.PacienteModel;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.util.ArrayList;

@WebService()
public interface PacienteWebService {
    
    @WebMethod()
    PacienteModel insertPaciente(PacienteModel pacienteModel) throws ValidationException, DataBaseException;
    
    @WebMethod()
    PacienteModel getPacienteById(int id) throws ValidationException, DataBaseException;
    
    @WebMethod()
    ArrayList<PacienteModel> getAllPacientes() throws ValidationException, DataBaseException;
    
    @WebMethod()
    PacienteModel updatePaciente(PacienteModel pacienteModel) throws ValidationException, DataBaseException;
    
    @WebMethod()
    void deletePacienteById(int id) throws ValidationException, DataBaseException;
    
}

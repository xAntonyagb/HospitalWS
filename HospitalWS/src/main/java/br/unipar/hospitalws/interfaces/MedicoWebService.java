package br.unipar.hospitalws.interfaces;

import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.models.MedicoModel;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.util.ArrayList;

@WebService
public interface MedicoWebService {
    
    @WebMethod()
    MedicoModel insertMedico() throws ValidationException;
    
    @WebMethod()
    ArrayList<MedicoModel> getMedicoById() throws ValidationException;
    
    @WebMethod()
    ArrayList<MedicoModel> getAllMedicos() throws ValidationException;
    
    @WebMethod()
    MedicoModel updateMedico() throws ValidationException;
    
    @WebMethod()
    void deleteMedicoById() throws ValidationException;
    
}

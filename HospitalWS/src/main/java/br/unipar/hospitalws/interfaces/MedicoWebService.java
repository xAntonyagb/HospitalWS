package br.unipar.hospitalws.interfaces;

import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.models.MedicoModel;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.util.ArrayList;

@WebService
public interface MedicoWebService {
    
    @WebMethod()
    MedicoModel insertMedico(MedicoModel medicoModel) throws ValidationException;
    
    @WebMethod()
    MedicoModel getMedicoById(int id) throws ValidationException;
    
    @WebMethod()
    ArrayList<MedicoModel> getAllMedicos() throws ValidationException;
    
    @WebMethod()
    MedicoModel updateMedico(MedicoModel medicoModel) throws ValidationException;
    
    @WebMethod()
    void deleteMedicoById(int id) throws ValidationException;
    
}

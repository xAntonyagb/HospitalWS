package br.unipar.hospitalws.interfaces;

import br.unipar.hospitalws.DTO.MedicoDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.ValidationException;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.util.ArrayList;

@WebService
public interface MedicoWebService {
    
    @WebMethod()
    MedicoDTO insertMedico(MedicoDTO medico) throws ValidationException, DataBaseException;
    
    @WebMethod()
    MedicoDTO getMedicoById(int id) throws ValidationException, DataBaseException;
    
    @WebMethod()
    ArrayList<MedicoDTO> getAllMedicos() throws ValidationException, DataBaseException;
    
    @WebMethod()
    MedicoDTO updateMedico(MedicoDTO medico) throws ValidationException, DataBaseException;
    
    @WebMethod()
    MedicoDTO desativaMedico(int id) throws ValidationException, DataBaseException;
    
}

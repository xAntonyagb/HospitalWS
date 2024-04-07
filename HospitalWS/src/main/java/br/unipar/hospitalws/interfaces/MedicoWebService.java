package br.unipar.hospitalws.interfaces;

import br.unipar.hospitalws.DTO.MedicoDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.InternalException;
import br.unipar.hospitalws.exceptions.ValidationException;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.util.ArrayList;

@WebService
public interface MedicoWebService {
    
    @WebMethod()
    MedicoDTO insertMedico(MedicoDTO medico) throws ValidationException, DataBaseException, InternalException;
    
    @WebMethod()
    MedicoDTO getMedicoById(int id) throws ValidationException, DataBaseException, InternalException;
    
    @WebMethod()
    ArrayList<MedicoDTO> getAllMedicos() throws ValidationException, DataBaseException, InternalException;
    
    @WebMethod()
    MedicoDTO updateMedico(MedicoDTO medico) throws ValidationException, DataBaseException, InternalException;
    
    @WebMethod()
    MedicoDTO desativaMedico(int id) throws ValidationException, DataBaseException, InternalException;
    
}

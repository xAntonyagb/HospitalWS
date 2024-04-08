package br.unipar.hospitalws.interfaces;

import br.unipar.hospitalws.DTO.ConsultaDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.InternalException;
import br.unipar.hospitalws.exceptions.ValidationException;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.util.List;

@WebService
public interface ConsultaWebService {
    
    @WebMethod()
    ConsultaDTO insertConsulta(ConsultaDTO consulta) throws ValidationException, DataBaseException, InternalException;
    
    @WebMethod()
    ConsultaDTO getConsultaById(int id) throws ValidationException, DataBaseException, InternalException;
    
    @WebMethod()
    List<ConsultaDTO> getAllConsultas() throws ValidationException, DataBaseException, InternalException;
    
    @WebMethod()
    ConsultaDTO updateConsulta(ConsultaDTO consulta) throws ValidationException, DataBaseException, InternalException;
    
    @WebMethod()
    ConsultaDTO cancelarConsulta(ConsultaDTO consulta) throws ValidationException, DataBaseException, InternalException;
}

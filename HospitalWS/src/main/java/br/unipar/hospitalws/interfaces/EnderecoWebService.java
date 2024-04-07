package br.unipar.hospitalws.interfaces;

import br.unipar.hospitalws.DTO.EnderecoDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.InternalException;
import br.unipar.hospitalws.exceptions.ValidationException;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.util.ArrayList;

@WebService
public interface EnderecoWebService {
    
    @WebMethod()
    EnderecoDTO insertEndereco(EnderecoDTO enndereco) throws ValidationException, DataBaseException, InternalException;
    
    @WebMethod()
    EnderecoDTO getEnderecoById(int id) throws ValidationException, DataBaseException, InternalException;
    
    @WebMethod()
    ArrayList<EnderecoDTO> getAllEnderecos() throws ValidationException, DataBaseException, InternalException;
    
    @WebMethod()
    EnderecoDTO updateEndereco(EnderecoDTO enndereco) throws ValidationException, DataBaseException, InternalException;
    
    @WebMethod()
    EnderecoDTO deleteEndereco(int id) throws ValidationException, DataBaseException, InternalException;
    
}

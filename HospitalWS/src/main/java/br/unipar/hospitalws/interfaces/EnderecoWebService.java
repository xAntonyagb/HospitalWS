package br.unipar.hospitalws.interfaces;

import br.unipar.hospitalws.DTO.EnderecoDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.ValidationException;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.util.ArrayList;

@WebService
public interface EnderecoWebService {
    
    @WebMethod()
    EnderecoDTO insertEndereco(EnderecoDTO enndereco) throws ValidationException, DataBaseException;
    
    @WebMethod()
    EnderecoDTO getEnderecoById(int id) throws ValidationException, DataBaseException;
    
    @WebMethod()
    ArrayList<EnderecoDTO> getAllEnderecos() throws ValidationException, DataBaseException;
    
    @WebMethod()
    EnderecoDTO updateEndereco(EnderecoDTO enndereco) throws ValidationException, DataBaseException;
    
    @WebMethod()
    EnderecoDTO deleteEnderecoById(int id) throws ValidationException, DataBaseException;
    
}

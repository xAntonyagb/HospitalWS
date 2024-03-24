package br.unipar.hospitalws.interfaces;

import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.models.EnderecoModel;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.util.ArrayList;

@WebService
public interface EnderecoWebService {
    
    @WebMethod()
    EnderecoModel insertEndereco(EnderecoModel enderecoModel) throws ValidationException, DataBaseException;
    
    @WebMethod()
    EnderecoModel getEnderecoById(int id) throws ValidationException, DataBaseException;
    
    @WebMethod()
    ArrayList<EnderecoModel> getAllEnderecos() throws ValidationException, DataBaseException;
    
    @WebMethod()
    EnderecoModel updateEndereco(EnderecoModel enderecoModel) throws ValidationException, DataBaseException;
    
    @WebMethod()
    void deleteEnderecoById(int id) throws ValidationException, DataBaseException;
    
}

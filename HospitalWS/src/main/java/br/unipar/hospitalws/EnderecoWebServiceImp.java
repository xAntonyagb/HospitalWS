package br.unipar.hospitalws;

import br.unipar.hospitalws.DTO.EnderecoDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.InternalException;
import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.interfaces.EnderecoWebService;
import br.unipar.hospitalws.services.EnderecoService;
import jakarta.jws.WebService;
import java.util.ArrayList;

@WebService(endpointInterface = "br.unipar.hospitalws.interfaces.EnderecoWebService")
public class EnderecoWebServiceImp implements EnderecoWebService{
    
    private static EnderecoService enderecoService = new EnderecoService();

    @Override
    public EnderecoDTO insertEndereco(EnderecoDTO endereco) throws ValidationException, DataBaseException, InternalException {
        return enderecoService.insertEndereco(endereco);
    }

    @Override
    public EnderecoDTO getEnderecoById(int id) throws ValidationException, DataBaseException, InternalException {
        return enderecoService.getEnderecoById(id);
    }

    @Override
    public ArrayList<EnderecoDTO> getAllEnderecos() throws ValidationException, DataBaseException, InternalException {
        return enderecoService.getAllEnderecos();
    }

    @Override
    public EnderecoDTO updateEndereco(EnderecoDTO endereco) throws ValidationException, DataBaseException, InternalException {
        return enderecoService.updateEndereco(endereco);
    }

    @Override
    public EnderecoDTO deleteEndereco(int id) throws ValidationException, DataBaseException, InternalException {
        return enderecoService.deleteEndereco(id);
    }
    
}

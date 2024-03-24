package br.unipar.hospitalws;

import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.interfaces.EnderecoWebService;
import br.unipar.hospitalws.models.EnderecoModel;
import br.unipar.hospitalws.services.EnderecoService;
import jakarta.jws.WebService;
import java.util.ArrayList;

@WebService(endpointInterface = "br.unipar.hospitalws.interfaces.EnderecoWebService")
public class EnderecoWebServiceImp implements EnderecoWebService{
    
    private static EnderecoService pacienteService = new EnderecoService();

    @Override
    public EnderecoModel insertEndereco(EnderecoModel pacienteModel) throws ValidationException, DataBaseException {
        return null;
    }

    @Override
    public EnderecoModel getEnderecoById(int id) throws ValidationException, DataBaseException {
        return null;
    }

    @Override
    public ArrayList<EnderecoModel> getAllEnderecos() throws ValidationException, DataBaseException {
        return null;
    }

    @Override
    public EnderecoModel updateEndereco(EnderecoModel pacienteModel) throws ValidationException, DataBaseException {
        return null;
    }

    @Override
    public void deleteEnderecoById(int id) throws ValidationException, DataBaseException {
        return;
    }
    
}

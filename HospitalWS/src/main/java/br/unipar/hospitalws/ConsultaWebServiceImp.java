package br.unipar.hospitalws;

import br.unipar.hospitalws.DTO.ConsultaDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.InternalException;
import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.interfaces.ConsultaWebService;
import br.unipar.hospitalws.services.ConsultaService;
import jakarta.jws.WebService;
import java.util.List;

@WebService(endpointInterface = "br.unipar.hospitalws.interfaces.ConsultaWebService")
public class ConsultaWebServiceImp implements ConsultaWebService{

    private static ConsultaService consultaService = new ConsultaService();
    
    @Override
    public ConsultaDTO insertConsulta(ConsultaDTO consulta) throws ValidationException, DataBaseException, InternalException {
        return consultaService.insertConsulta(consulta);
    }

    @Override
    public ConsultaDTO getConsultaById(int id) throws ValidationException, DataBaseException, InternalException {
        return consultaService.getConsultaById(id);
    }

    @Override
    public List<ConsultaDTO> getAllConsultas() throws ValidationException, DataBaseException, InternalException {
        return consultaService.getAllConsultas();
    }

    @Override
    public ConsultaDTO updateConsulta(ConsultaDTO consulta) throws ValidationException, DataBaseException, InternalException {
        return consultaService.updateConsulta(consulta);
    }

    @Override
    public ConsultaDTO cancelarConsulta(ConsultaDTO consulta) throws ValidationException, DataBaseException, InternalException {
        return consultaService.cancelarConsulta(consulta);
    }
    
}

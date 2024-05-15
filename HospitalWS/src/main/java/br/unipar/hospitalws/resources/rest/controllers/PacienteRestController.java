package br.unipar.hospitalws.resources.rest.controllers;

import br.unipar.hospitalws.DTO.PacienteDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.InternalException;
import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.services.PacienteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

/**
 *
 * @author Antony
 */

@Path("/paciente")
@Produces(MediaType.APPLICATION_JSON)
public class PacienteRestController {
    
    private static PacienteService pacienteService = new PacienteService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertPaciente(PacienteDTO paciente, @Context HttpServletRequest request) 
            throws ValidationException, DataBaseException, InternalException {
        
        PacienteDTO retorno = pacienteService.insertPaciente(paciente);
        return Response.created(
                URI.create(request.getRequestURI()+"/"+retorno.getId()))
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getPacienteById(@PathParam(value = "id") int id) 
            throws ValidationException, DataBaseException, InternalException {
        
        PacienteDTO retorno = pacienteService.getPacienteById(id);
        return Response.ok(retorno).build();
    }

    @GET
    public Response getAllPacientes() 
            throws ValidationException, DataBaseException, InternalException {
        
        List<PacienteDTO> retorno = pacienteService.getAllPacientes();
        return Response.ok(retorno).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePaciente(@PathParam(value = "id") int id, PacienteDTO paciente) 
            throws ValidationException, DataBaseException, InternalException {
        
        PacienteDTO retorno = pacienteService.updatePaciente(paciente);
        return Response.ok(retorno).build();
    }

    @DELETE
    @Path("/{id}")
    public Response desativaPaciente(@PathParam(value = "id") int id) 
            throws ValidationException, DataBaseException, InternalException {
        
        PacienteDTO retorno = pacienteService.desativaPaciente(id);
        return Response.ok(retorno).build();
    }
    
}

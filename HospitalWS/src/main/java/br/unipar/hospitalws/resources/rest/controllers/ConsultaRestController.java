package br.unipar.hospitalws.resources.rest.controllers;

import br.unipar.hospitalws.DTO.ConsultaDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.InternalException;
import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.services.ConsultaService;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import java.net.URI;
import java.util.List;

/**
 *
 * @author Antony
 */

@Path("/consulta")
@Produces(MediaType.APPLICATION_JSON)
public class ConsultaRestController {

    private static ConsultaService consultaService = new ConsultaService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertConsulta(ConsultaDTO consulta, @Context HttpServletRequest request) throws ValidationException, DataBaseException, InternalException {
        ConsultaDTO retorno = consultaService.insertConsulta(consulta);
        return Response.created(
                URI.create(request.getRequestURI()+"/"+retorno.getId()))
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getConsultaById(@PathParam("id") int id) throws ValidationException, DataBaseException {
        ConsultaDTO retorno = consultaService.getConsultaById(id);
        return Response.ok(retorno).build();
    }

    @GET
    public Response getAllConsultas() throws ValidationException, DataBaseException, InternalException {
        List<ConsultaDTO> retorno = consultaService.getAllConsultas();
        return Response.ok(retorno).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateConsulta(@PathParam("id") int id, ConsultaDTO consulta) throws ValidationException, DataBaseException, InternalException {
        ConsultaDTO retorno = consultaService.updateConsulta(consulta);
        return Response.ok(retorno).build();
    }

    @DELETE
    @Path("/{id}")
    public Response cancelarConsulta(@PathParam("id") int id) throws ValidationException, DataBaseException, InternalException {
        ConsultaDTO consulta = new ConsultaDTO();
        consulta.setId(id);

        ConsultaDTO retorno = consultaService.cancelarConsulta(consulta);
        return Response.ok(retorno).build();
    }
}
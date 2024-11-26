package br.unipar.hospitalws.resources.rest.controllers;

import br.unipar.hospitalws.DTO.EnderecoDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.InternalException;
import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.services.EnderecoService;
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

@Path("/endereco")
@Produces(MediaType.APPLICATION_JSON)
public class EnderecoRestController {

    private static EnderecoService enderecoService = new EnderecoService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertEndereco(EnderecoDTO endereco, @Context HttpServletRequest request) throws ValidationException, DataBaseException, InternalException {
        EnderecoDTO retorno = enderecoService.insertEndereco(endereco);
        return Response.created(
                URI.create(request.getRequestURI()+"/"+retorno.getId()))
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getEnderecoById(@PathParam("id") int id) throws ValidationException, DataBaseException {
        EnderecoDTO retorno = enderecoService.getEnderecoById(id);
        return Response.ok(retorno).build();
    }

    @GET
    public Response getAllEnderecos() throws ValidationException, DataBaseException, InternalException {
        List<EnderecoDTO> retorno = enderecoService.getAllEnderecos();
        return Response.ok(retorno).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEndereco(@PathParam("id") int id, EnderecoDTO endereco) throws ValidationException, DataBaseException, InternalException {
        EnderecoDTO retorno = enderecoService.updateEndereco(endereco);
        return Response.ok(retorno).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteEndereco(@PathParam("id") int id) throws ValidationException, DataBaseException, InternalException {
        EnderecoDTO retorno = enderecoService.deleteEndereco(id);
        return Response.ok(retorno).build();
    }
}
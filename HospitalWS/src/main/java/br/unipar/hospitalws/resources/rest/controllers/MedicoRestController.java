/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unipar.hospitalws.resources.rest.controllers;

import br.unipar.hospitalws.DTO.MedicoDTO;
import br.unipar.hospitalws.exceptions.DataBaseException;
import br.unipar.hospitalws.exceptions.InternalException;
import br.unipar.hospitalws.exceptions.ValidationException;
import br.unipar.hospitalws.services.MedicoService;
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

@Path("/medico")
@Produces(MediaType.APPLICATION_JSON)
public class MedicoRestController {

    private static MedicoService medicoService = new MedicoService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertMedico(MedicoDTO medico, @Context HttpServletRequest request) 
            throws ValidationException, DataBaseException, InternalException {
        
        MedicoDTO retorno = medicoService.insertMedico(medico);
        return Response.created(
                URI.create(request.getRequestURI()+"/"+retorno.getId()))
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getMedicoById(@PathParam(value = "id") int id) 
            throws ValidationException, DataBaseException, InternalException {
        
        MedicoDTO retorno = medicoService.getMedicoById(id);
        return Response.ok(retorno).build();
    }

    @GET
    public Response getAllMedicos() 
            throws ValidationException, DataBaseException, InternalException {
        
        List<MedicoDTO> retorno = medicoService.getAllMedicos();
        return Response.ok(retorno).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMedico(@PathParam(value = "id") int id, MedicoDTO medico) 
            throws ValidationException, DataBaseException, InternalException {
        
        MedicoDTO retorno = medicoService.updateMedico(medico);
        return Response.ok(retorno).build();
    }

    @DELETE
    @Path("/{id}")
    public Response desativaMedico(@PathParam(value = "id") int id) 
            throws ValidationException, DataBaseException, InternalException {
        
        MedicoDTO retorno = medicoService.desativaMedico(id);
        return Response.ok(retorno).build();
    }
}

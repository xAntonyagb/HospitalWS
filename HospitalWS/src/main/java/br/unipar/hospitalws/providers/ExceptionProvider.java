package br.unipar.hospitalws.providers;

import br.unipar.hospitalws.DTO.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.Date;
import java.util.logging.Logger;

/**
 *
 * @author Antony
 */

@Provider
public class ExceptionProvider implements ExceptionMapper<Exception> {

    @Context
    private static HttpServletRequest request;
    
    @Override
    public Response toResponse(Exception ex) {
        final Logger LOGGER = Logger.getLogger(ex.getClass().getName());
        LOGGER.severe(ex.getMessage());
                
        ExceptionResponse response = new ExceptionResponse(
                "Ops, algo estranho aconteceu na nossa parte! Por favor entre em contato com um agente responsável para resolver seu problema.", 
                new Date(), 
                request.getRequestURI(), 
                Response.serverError().toString());
        
        return Response.status(
                Response.Status.INTERNAL_SERVER_ERROR)
                .entity(response)
                .build();
    }
    
}

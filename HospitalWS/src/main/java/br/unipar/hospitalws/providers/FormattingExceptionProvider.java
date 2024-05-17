package br.unipar.hospitalws.providers;

import br.unipar.hospitalws.DTO.ExceptionResponse;
import br.unipar.hospitalws.exceptions.FormattingException;
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
public class FormattingExceptionProvider implements ExceptionMapper<FormattingException> {

    @Context
    private HttpServletRequest request;
    
    @Override
    public Response toResponse(FormattingException ex) {
        final Logger LOGGER = Logger.getLogger(ex.getClass().getName());
        LOGGER.severe(ex.getMessage());
                
        ExceptionResponse response = new ExceptionResponse(
                ex.getMessage(), 
                new Date(), 
                request.getRequestURI(), 
                Response.serverError().toString());
        
        return Response.status(
                Response.Status.BAD_REQUEST)
                .entity(response)
                .build();
    }
    
}

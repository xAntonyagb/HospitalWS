package br.unipar.hospitalws.exceptions;

import jakarta.xml.ws.WebFault;

@WebFault
public class ValidationException extends RuntimeException {
    
    String message;

    public ValidationException(String message) {
        super(message);
    }
}

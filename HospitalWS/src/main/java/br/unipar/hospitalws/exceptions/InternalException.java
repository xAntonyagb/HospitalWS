package br.unipar.hospitalws.exceptions;

import jakarta.xml.ws.WebFault;

@WebFault
public class InternalException extends RuntimeException {
    String message;

    public InternalException(String message) {
        super("Ops, um problema interno aconteceu ao processar sua requisição: " + message);
    }
}

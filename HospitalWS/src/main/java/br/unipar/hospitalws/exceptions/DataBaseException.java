package br.unipar.hospitalws.exceptions;

import jakarta.xml.ws.WebFault;

@WebFault
public class DataBaseException extends RuntimeException {
    String message;

    public DataBaseException(String message) {
        super("Ops, um problema interno aconteceu: " + message);
    }
}

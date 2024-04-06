package br.unipar.hospitalws.exceptions;

import jakarta.xml.ws.WebFault;

@WebFault
public class FormattingException extends RuntimeException {
    
    String message;

    public FormattingException(String message) {
        super("Não é possivel converter a data: "+message);
    }
}

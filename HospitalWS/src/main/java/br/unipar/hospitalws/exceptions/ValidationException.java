package br.unipar.hospitalws.exceptions;

public class ValidationException extends RuntimeException {
    
    String message;

    public ValidationException(String message) {
        super(message);
    }
}

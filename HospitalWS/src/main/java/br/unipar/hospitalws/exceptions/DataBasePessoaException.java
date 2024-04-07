package br.unipar.hospitalws.exceptions;

import jakarta.xml.ws.WebFault;

@WebFault
public class DataBasePessoaException extends Exception {
    
    public DataBasePessoaException() { }
    
}

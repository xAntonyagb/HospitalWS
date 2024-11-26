package br.unipar.hospitalws.DTO;

import java.util.Date;

/**
 *
 * @author Antony
 */
public class ExceptionResponse {
    private String message;
    private Date dtException;
    private String path;
    private String httpStatus;

    public ExceptionResponse() {
    }
    
    public ExceptionResponse(String message, Date dtException, String path, String httpStatus) {
        this.message = message;
        this.dtException = dtException;
        this.path = path;
        this.httpStatus = httpStatus;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDtException() {
        return dtException;
    }

    public void setDtException(Date dtException) {
        this.dtException = dtException;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }
    
    
}

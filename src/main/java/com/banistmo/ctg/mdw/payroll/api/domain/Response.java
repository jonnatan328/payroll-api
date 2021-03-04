package com.banistmo.ctg.mdw.payroll.api.domain;

import org.springframework.http.HttpStatus;

import com.banistmo.ctg.mdw.payroll.api.util.Utilities;


/**
 * Generic Response.
 * 
 */
public class Response<T> {

    private int status;
    private String message;
    private T body;

    public Response() {
        super();
    }

    public Response(int status, String message) {
        this.status = status;
        this.message = Utilities.utf8ToIso(message);
    }
    
    public Response(HttpStatus status, String message) {
        this.status = status.value();
        this.message = Utilities.utf8ToIso(message);
    }
    
    public Response(int status, String message, T body) {
        this(status, message);
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = Utilities.utf8ToIso(message);
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

}

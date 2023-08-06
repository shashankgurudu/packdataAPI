package com.packdata.packdata.ExceptionHandlers;

public class ServiceUnAvailableException  extends RuntimeException {
    
    public ServiceUnAvailableException() {
        super();
    }
    public ServiceUnAvailableException(String message) {
        super(message);
    }  
}
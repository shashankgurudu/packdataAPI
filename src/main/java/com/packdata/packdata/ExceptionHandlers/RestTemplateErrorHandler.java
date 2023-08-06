package com.packdata.packdata.ExceptionHandlers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {
    
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {

        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();

    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is5xxServerError()) {

            if (response.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {

                throw new ServiceUnAvailableException("Service is currently unavailable");
            }

            if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {

                throw new ServiceUnAvailableException("Internal Server Error");
            }
        } else  {
            throw new RuntimeException("Error Occurred. Please contact help desk");
        }

    }

}
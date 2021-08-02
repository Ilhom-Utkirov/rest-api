package com.example.demo.utils;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

public class ApiHelper  {

    private static Logger logger = LogManager.getLogger(ApiHelper.class);


    static  class RestErrorHandler implements ResponseErrorHandler{


        @Override
        public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
            return clientHttpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR ||
                    clientHttpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR;
        }

        @Override
        public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
            Logger logger = LogManager.getLogger(ApiHelper.class);
            logger.error("Response error: {} {}", clientHttpResponse.getStatusCode(), clientHttpResponse.getStatusText());
        }

        @Override
        public void handleError(URI url, HttpMethod method, ClientHttpResponse clientHttpResponse) throws IOException {
            ResponseErrorHandler.super.handleError(url, method, clientHttpResponse);
        }
    }

}

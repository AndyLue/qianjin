package com.dream.qianjin.service.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

//@Provider
public class CustomExceptionMapper implements ExceptionMapper<Exception> {

    public Response toResponse(Exception exception) {
    	System.out.println("CustomExceptionMapper:"+exception);
//    	Response.status(Status.GATEWAY_TIMEOUT);
        return Response.ok(exception.getMessage()).build();
    }
    
}

package com.dvd.rental.capstone.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.dvd.rental.capstone.business.exception.ComponentException;

/**
 * CommonExceptionHandler - Common handler of the ComponentException to convert
 * into proper error message understandable by client
 * 
 * @author rachana
 * @since Aug 10, 2014
 * 
 */
@Provider
public class CommonExceptionHandler implements
        ExceptionMapper<ComponentException> {

    /*
     * (non-Javadoc)
     * 
     * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
     */
    @Override
    public Response toResponse(ComponentException exception) {
        if (exception.getHttpStatusCode() == Status.BAD_REQUEST.getStatusCode()) {
            return Response.status(Status.BAD_REQUEST)
                    .entity(exception.getDetails()).build();
        }
        if (exception.getHttpStatusCode() == Status.FORBIDDEN.getStatusCode()) {
            return Response.status(Status.FORBIDDEN)
                    .entity(exception.getDetails()).build();
        }
        if (exception.getHttpStatusCode() == Status.NOT_FOUND.getStatusCode()) {
            return Response.status(Status.NOT_FOUND)
                    .entity(exception.getDetails()).build();
        }
        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }

}

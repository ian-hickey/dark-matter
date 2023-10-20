package cfscript.library;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DumpExceptionMapper implements ExceptionMapper<DumpException> {

    @Override
    public Response toResponse(DumpException exception) {
        // Here you decide what to send as a response when the process is aborted.
        // You might want to log the event, send a specific HTTP status code, etc.
        return Response.status(Response.Status.OK)
                .type("text/html")
                .entity(exception.getMessage()) // optional message
                .build();
    }
}

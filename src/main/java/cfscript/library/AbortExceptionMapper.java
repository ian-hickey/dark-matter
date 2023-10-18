package cfscript.library;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class AbortExceptionMapper implements ExceptionMapper<AbortException> {

    @Override
    public Response toResponse(AbortException exception) {
        // Here you decide what to send as a response when the process is aborted.
        // You might want to log the event, send a specific HTTP status code, etc.
        return Response.status(Response.Status.OK) // or any other appropriate status
                .entity("Process aborted: " + exception.getMessage()) // optional message
                .build();
    }
}

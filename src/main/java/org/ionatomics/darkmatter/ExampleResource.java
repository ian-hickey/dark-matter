/*
  This test component represents a basic cfscript REST component.
  This is an example REST service that greets the user generically or by name.
*/
package org.ionatomics.darkmatter;

import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;
import java.net.URI;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.*;
import jakarta.enterprise.context.*;
import io.quarkus.qute.*;
import static cfscript.library.StdLib.*;
import io.quarkus.logging.Log;
import java.lang.*;
import java.util.*;
import io.quarkus.mailer.Mailer;
@Path("")
public class ExampleResource  {

    @Inject
    Mailer mailer;

    public ExampleResource() { }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/greet")
    public Response greet() {
                return Response.ok("Greeting from Dark Matter!").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/greetname")
    public Response greetByName(@QueryParam("name") String name) {
        if (isNotNull(name)) {
            var greeting = "Greetings "+ name +" from Dark Matter!";
            var myStruct = new HashMap<String, Object>() {{ put("greeting", greeting);put("id", 1);}};
            return Response.ok(myStruct).build();
        }else{
            return Response.ok("Greetings ANONYMOUS Dark Matter user!").build();
        }
    }
}
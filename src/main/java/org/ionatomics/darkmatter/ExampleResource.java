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
import org.eclipse.microprofile.config.inject.ConfigProperty;
import jakarta.enterprise.context.*;
import static cfscript.library.StdLib.*;
import io.quarkus.logging.Log;
import io.quarkus.mailer.Mailer;
import io.quarkus.qute.*;
import java.lang.*;
import java.util.*;
@Path("")
public class ExampleResource  {

    /**
     * You can inject the Mailer object to send mail.
     */
    @Inject
Mailer mailer;

    /**
     * Config properties are stored in your application.properties file in /resources
     * You can put any values you need to use in the app in there.
     * Here, the default value is used if you need it.
     */
    @ConfigProperty(name="greeting.anon",defaultValue="anonymous")
String anonGreeting;

    public ExampleResource() { }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/greet")
    public Response greet() {
                return Response.ok("Greeting from Dark Matter!").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/greetmebyname")
    public Response greetByName(@QueryParam("name") String name) {
        Log.info("Greet Me called with: " + name);
        if (isNotNull(name)) {
            var greeting = "Greetings "+ name +" from Dark Matter!";
            var myStruct1 = new HashMap<String, Object>() {{ put("greeting", greeting);put("id", 1);put("name", "Ian");put("info", "1 Test st. Worcester, Ma.");}};
            var myStruct2 = new HashMap<String, Object>() {{ put("greeting", greeting);put("id", 2);put("name", "Admin");put("info", "1 Test st. Worcester, Ma.");}};
            var myArray  = new ArrayList<Object>() {{add(myStruct1);add(myStruct2);}};
            return Response.ok(myArray).build();
        }else{
            return Response.ok("Greetings "+anonGreeting+" from Dark Matter!").build();
        }
    }
}
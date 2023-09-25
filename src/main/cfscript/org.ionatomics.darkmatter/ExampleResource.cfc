/*
  This test component represents a basic cfscript REST component.
  This is an example REST service that greets the user generically or by name.
*/
@Path("")
component name="ExampleResource" hint="This is a REST controller" {

    @Inject
    property type="Mailer" name="mailer";

    public function init() { }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/greet")
    public Response function greet() {
        //mailer.send(new Mail().addTo("me@ionatomics.org"));
        return Response.ok("Greeting from Dark Matter!").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/greetname")
    public Response function greetByName(@QueryParam("name") String name) {
        if (isNotNull(name)) {
            var greeting = "Greetings "+ name +" from Dark Matter!";
            var myStruct = {greeting: greeting, id: 1};
            return Response.ok(myStruct).build();
        }else{
            return Response.ok("Greetings ANONYMOUS Dark Matter user!").build();
        }
    }
}
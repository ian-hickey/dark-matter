/*
  This test component represents a basic cfscript REST component.
  This is an example REST service that greets the user generically or by name.
*/
@Path("")
component name="ExampleResource" hint="This is a REST controller" {

    /**
     * You can inject the Mailer object to send mail.
     */
    @Inject
    property type="Mailer" name="mailer";

    /**
     * Config properties are stored in your application.properties file in /resources
     * You can put any values you need to use in the app in there.
     * Here, the default value is used if you need it.
     */
    @ConfigProperty(name = "greeting.anon", defaultValue="anonymous")
    property type="String" name="anonGreeting";

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
    @Path("/greetmebyname")
    public Response function greetByName(@QueryParam("name") String name) {
        Log.info("Greet Me called with: " + name);
        if (isNotNull(name)) {
            var greeting = "Greetings "+ name +" from Dark Matter!";
            var myStruct = {greeting: greeting, id: 1, name: "Ian", info: "1 test st. Worcester, Ma."};
            return Response.ok(myStruct).build();
        }else{
            return Response.ok("Greetings "+anonGreeting+" from Dark Matter!").build();
        }
    }
}
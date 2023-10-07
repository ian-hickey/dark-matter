# dark matter starter

This project uses Dark Matter + Quarkus, the Supersonic Subatomic Java Framework.

**Dark Matter** is a robust translation layer designed for Quarkus, facilitating the 
integration of DMScript—a versatile scripting language influenced by CFScript. 

This project embarks on simplifying the 
development of Quarkus applications by leveraging the expressive syntax and 
dynamic capabilities of DMScript. 

By bridging the gap between DMScript and Quarkus, 
Dark Matter unleashes a streamlined, flexible avenue for developers to architect,
build, and deploy sophisticated Quarkus applications. Experience the blend of 
DMScript's simplicity with the power and efficiency of Quarkus through Dark Matter.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

# Prerequisites
### Java
To run Dark Matter / Quarkus, you need Java 17+ installed. If you type `java` into your terminal, it should resolve
to your java version.

For example: 

`$ java -version`

openjdk version "17.0.8" 2023-07-18


**JAVA_HOME** should also be set and resolve to the above. On Windows, this involves setting your global / user environment
variables and possibly restarting. You **PATH** should also contain a reference to JAVA_HOME/bin folder. On linux, mac, you
should set JAVA_HOME and PATH in the configuration file for the terminal you are using (bash, etc). 

### Docker

You need to have docker installed. Usually the easiest way to do this is to install Docker Desktop. You should be 
signed in to Docker, and docker needs to be running. Quarkus will throw errors when you start in dev mode if Docker 
is not running.

### GIT

Dark Matter using Git to get files used to bootstrap your project. If you are using the Dark Matter CLI, you need
git installed. You should be able to type `git` in your terminal and see output.


## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
Update permissions on the script first: 

The easiest way to get Dark Matter + Quarkus started
is to use the included scripts:

Windows: `.\start-dev.bat`

Mac/Linux/WSL: `./start-dev.sh`

> **_NOTE:_**  When Quarkus starts, if Docker is not running, or,
you don't have permission to use docker, it will error. Correct and try again.

--------------------------------------------------

Or, You can also start each manually if needed:
Start **Quarkus**:

Windows Powershell/Command Prompt: ```.\mvnw.cmd run quarkus:dev```
Mac/Linux/Unix/WSL: ```./mvnw run quarkus:dev```  

Start **Dark Matter**:
```java -jar darkmatter-watcher-1.0-ALPHA.jar src/main/cfscript process-resources```
This instructs Dark Matter to watch for file changes. 
---------------------------------------------------

You app code lives in the /src/main/cfscript directory (+ your package name org.acme etc).
As you make changes in dev mode, your app will live reload.

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:

```
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/starter-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- RESTEasy Reactive ([guide](https://quarkus.io/guides/resteasy-reactive)): A Jakarta REST implementation utilizing
  build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the
  extensions that depend on it.
- Mailer ([guide](https://quarkus.io/guides/mailer)): Send emails
- JDBC Driver - MySQL ([guide](https://quarkus.io/guides/datasource)): Connect to the MySQL database via JDBC

## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

### VSCode IDE support.
We recommend using the VSCode extension by Sketchpunk. It does a great job coloring without issues with DMScript.

_____________________________________________________
# DMScript Annotations Tutorial

Quarkus is a Kubernetes-native Java framework tailored for GraalVM and HotSpot, 
crafted from the best-of-breed Java libraries and standards. 
Dark Matter is a Cfscript dialect that allows using Java annotations natively.
Below is a simple explanation of common annotations used in DMScript.

## Table of Contents

- [@Inject](#inject)
- [@Singleton, @ApplicationScoped, @RequestScoped](#singleton)
- [@Path](#path)
- [@Entity](#entity)
- [@GET, @POST, @PUT, @DELETE](#http-verbs)
- [@Produces, @Consumes](#produces-and-consumes)
- [@ConfigProperty](#configproperty)
- [@Transactional](#transactional)

---

### <a name="inject"></a>@Inject

The `@Inject` annotation is used to inject one bean into another.

**Example:**

```
@Singleton
component name="SomeService" {
    function sayHello() {
        return "Hello, Quarkus!";
    }
}

@RequestScoped
component name="HelloController" {
    
    @Inject
    property type="SomeService" name="someService";
    
    function getGreeting() {
        return someService.sayHello();
    }
}
```

### <a name="@Singleton"></a>@Singleton @ApplicationScoped @RequestScoped

The `@Singleton` (or `@ApplicationScoped`) annotations are used to specify that the 
component should only be instantiated once on application start (technically on injection).

The `@RequestScoped` annotation is used to specify that the component should be instantiated on each request.

* `Note that you could create an Application component that stores application level values. Or, a request level compoent to store
request level values. You can inject these components into other components where needed. `


---

### <a name="path"></a>@Path

The `@Path` annotation is used to define the URL at which a component or function is available.

**Example:**

```
@Path("/hello")
component name="HelloController" {
    @GET
    function sayHello() {
        return "Hello, World!";
    }
}
```

---

### <a name="entity"></a>@Entity

The `@Entity` annotation marks a component as an entity.

**Example:**

```
@Entity
component name="User" extends="PanachEntity"{
    /* id field handled automatically. */
    property name="name";
    property name="address";
    property name="zip";
}
```

---

### <a name="http-verbs"></a>@GET, @POST, @PUT, @DELETE

These annotations specify the HTTP verb associated with a function.

**Example:**

```
@Path("/users")
component name="UserController" {
    @GET
    function getAllUsers() {
        // return all users
    }
    
    @POST
    function createUser(User user) {
        // create a new user
    }
}
```

---

### <a name="produces-and-consumes"></a>@Produces, @Consumes

These annotations define the MIME type that a method can produce or consume.

**Example:**

```
@Path("/books")
component name="BookController" {
    
    @GET 
    @Produces(MediaType.APPLICATION_JSON) /* This is the default and can be omited */
    function getAllBooks() {
        // return all books
    }
    
    @POST 
    @Consumes(MediaType.APPLICATION_JSON)
    function createBook(Book book) {
        // create a new book
    }
}
```

---

### <a name="configproperty"></a>@ConfigProperty

The `@ConfigProperty` annotation is used to inject configuration properties.

**Example:**

For a configuration property (located in your /resources/application.properties file):

```
greeting.message=Hello from Quarkus!
```

You can inject it using:

```
component name="GreetingService" {
    @ConfigProperty(name="greeting.message") 
    property name="message";
}
```

---

### <a name="transactional"></a>@Transactional

The `@Transactional` annotation indicates that a function should run within a transaction.

**Example:**

```
component name="UserService" {
    @Inject 
    property type="EntityManager" name="em";
    
    @Transactional
    function addUser(User user) {
        em.persist(user);
    }
}
```

---




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

`openjdk version "17.0.8" 2023-07-18
OpenJDK Runtime Environment GraalVM CE 22.3.3 (build 17.0.8+7-jvmci-22.3-b22)
OpenJDK 64-Bit Server VM GraalVM CE 22.3.3 (build 17.0.8+7-jvmci-22.3-b22, mixed mode, sharing)
`

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

Start **Quarkus**:

Windows Powershell/Command Prompt: ```.\mvnw.cmd run quarkus:dev```
Mac/Linux/Unix/WSL: ```./mvnw run quarkus:dev```  

Start **Dark Matter**:

```java -jar darkmatter-watcher-1.0-ALPHA.jar src/main/cfscript process-resources```
This instructs Dark Matter to watch for file changes. 

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


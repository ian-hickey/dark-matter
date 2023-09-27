# dark matter starter

This project uses Dark Matter (A Cfscript influenced scripting language) + Quarkus, the Supersonic Subatomic Java Framework.

*Dark Matter* is a robust translation layer designed for Quarkus, facilitating the integration of DMScript—a versatile scripting language influenced by CFScript. This project embarks on simplifying the development of Quarkus applications by leveraging the expressive syntax and dynamic capabilities of DMScript. By bridging the gap between DMScript and Quarkus, Dark Matter unleashes a streamlined, flexible avenue for developers to architect, build, and deploy sophisticated Quarkus applications. Experience the blend of DMScript's simplicity with the power and efficiency of Quarkus through Dark Matter.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
Update permissions on the script first: 

Start Quarkus:
```mvn run quarkus:dev```

Start Dark Matter:

```shell script chmod +x dm.sh```

```
./dm.sh
```

This instructs Dark Matter to watch for file changes. 

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

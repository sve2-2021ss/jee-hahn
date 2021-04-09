# jwtprop project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

### JWT RBAC (Json Web Token - Role Based Access Control)
The Microprofile JWT RBAC specification requires, that JWT's are signed with the RSA-256 
signature algorithm. This requires a RSA public key pair. The secured REST endpoint needs 
access to the public cey, which is needed to verify the JWT sent with each request.
This is done in application.properties by the setting: mp.jwt.verify.publickey.location=publicKey.pem 

### SmallRye Jwt
SmallRye is a library for implementing the Microprofile JWT RBAC. It deals with the decryption 
and verification of the JWT token and parsing it into a JsonWebToken implementation.

SmallRye also provide an API to generate JWT-Tokens. Therefore the private key is needed 
and it's location must be set in application.properties: smallrye.jwt.sign.key-location

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory. Be aware that it’s not an _über-jar_ as
the dependencies are copied into the `target/quarkus-app/lib/` directory.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/jwtprop-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html
.

## Provided examples

### RESTEasy JAX-RS example

REST is easy peasy with this Hello World RESTEasy resource.

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)

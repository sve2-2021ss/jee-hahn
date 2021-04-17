# jwtprop project

This project shows the usage of JWT Tokens to secure Web-API's by using the Microprofile JWT Propagation.

Technologystack:
- Quarkus 
- Java
- JWT Propagation
- JSON-B

## JSON Web Tokens
JSON Web Tokens are an open, industry standard RFC-7519 (https://tools.ietf.org/html/rfc7519) method for representing claims securely between two parties.

A standard JWT contains 3 parts:
- Header
- Payload (Claims)
- Signature

#### Header:
The header identifies, which algorithm is used to generate the signature and some metadata
```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

#### Payload
The payload contains a set of claims, containing standard fields and custom claims.
Claims in the example: "sub -> Subject", "iss -> Issuer", "iat -> Issued at" 
```json
{
  "sub": 1422779638,
  "iss": "https://sve2.jwt.com/issuer",
  "iat": 1516239022
}
```

#### Signature
The signature is calculated by encoding the header and payload using Base64 url encoding, concatenating the two together with a '.' separator and encoded by a cryptographic operation, which is specified in the Header.
```
HMAC-SHA256(
  secret,
  base64urlEncoding(header) + '.' +
  base64urlEncoding(payload)
)
```

The full token is represented by separately using Base64 encoding of the 3 parts and concatenated together with a '.' separator.
```
string token = base64urlEncoding(header) + '.' + base64urlEncoding(payload) + '.' + base64urlEncoding(signature)
```

## Microprofile JWT Specification (MP-JWT)
The focus of the specification is the definition and the required format of the JWT's. 

https://www.eclipse.org/community/eclipse_newsletter/2017/september/article2.php

https://github.com/eclipse/microprofile-jwt-auth

The specification defines the requirements for an JWT, which is issued by any other party.

Requirements:
- Be usable as an authentication token
- Be usable as an authorization token that contains Java EE application level roles indirectly granted via a groups claim
- Can support additional standard claims as well as non-standard claims

Therefore, two (new) claims are introduces:
- "upn": Human readable claim that uniquely identifies the user principal of the token
- "groups": Memberships (roles) of the user 

On the Server, a token-based authentication mechanism verifies the token. As part of the security context, the server establishes 
role and group mappings for the subject based on the JWT claims. 


## SmallRye Jwt
SmallRye is a library for implementing the Microprofile JWT RBAC. It deals with the decryption 
and verification of the JWT token and parsing it into a JsonWebToken implementation.

SmallRye also provide an API to generate JWT-Tokens. 

For signing the JWT, the RSA-256 cryptographic algorithm is used. Therefore a private/public keypair is needed.
For generating a keypar the following commands can be used:
```
openssl genrsa -out rsaPrivateKey.pem 2048
openssl rsa -pubout -in rsaPrivateKey.pem -out publicKey.pem
openssl pkcs8 -topk8 -nocrypt -inform pem -in rsaPrivateKey.pem -outform pem -out privateKey.pem
```

The private key is used by the issuer for signing the token.
The public key is needed for verification of the token by the secured Api.

The location of the key's must be set in application.properties:
```
smallrye.jwt.sign.key-location=META-INF/resources/privateKey.pem
mp.jwt.verify.publickey.location=META-INF/resources/publicKey.pem
mp.jwt.verify.issuer=https://sve2.jwt.com/issuer
```

## Application Architecture

Overview of the application architecture:

![Picture application architecture](https://github.com/sve2-2021ss/jee-hahn/blob/master/doc/architecture.png)

#### Http-Client:
A http-client is required to use the REST-Service. This can be postman or any other tool like VSCode (with REST-Client extension) or 
integrated http-client in IntelliJ.

The "/init" endpoint can be used to initialize the database (basic user, admin user, movies),
Created user credentials:
- basic user: "user", password: "user" ("user"-role is assigned)
- admin user: "admin", password: "admin" ("user" and "admin"-role is assigned)

By passing user credentials (username + password) to the user/authenticate endpoint, a user can be authenticated and retrieves a signed JSON web token.
This token can be used for requests to the "/movies" endpoint. The token must be set in the "authorization"-header with the format: "Bearer <token>".

Read-operations on the "/movies"-endpoint requires the "user"-role/claim (GET /movies && GET /movies/{id})
Write-operations on the "/movies"-endpoint requires the "admin"-role/claim (POST /movies && PUT /movies/{id})

#### REST-Service:
Contains the REST-endpoints, with business logic and databae access.

#### Database:

In the database the users are persisted, with their role assignments.
In a separate table the movies are stored.

![Picture application architecture](https://github.com/sve2-2021ss/jee-hahn/blob/master/doc/database_model.png)

## Running the application in dev mode

First you need to ensure this line is activated in appsettings.properties:
```
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/jwt-db
```

Afterwards you need to start postgres database in a docker container:
```shell script
docker run --name jwt-db -e POSTGRES_USER=jwt -e POSTGRES_PASSWORD=jwt -e POSTGRES_DB=jwt-db --publish 5432:5432 -d postgres
```

Then you can run the application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

## Creating a native executable

You can create a native executable using this command:

```shell script
./mvnw package -Pnative -D"quarkus.native.container-build=true"
```

## Running the application in docker containers

Requirement: native executable is created

Ensure this line is activated in appsettings.properties:
```
quarkus.datasource.jdbc.url=jdbc:postgresql://jwt-db:5432/jwt-db
```

Build docker container:

```shell script
docker build -f Dockerfile.native -t jwt-api .
```

Build and run:

```shell script
docker-compose build
docker-compose up
```

Application is now running on localhost port 8080
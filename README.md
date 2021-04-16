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

## Demo Application Architecture

## Running the application in dev mode

You can run the application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

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

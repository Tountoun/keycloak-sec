# OAUTH2 SECURITY USING KEYCLOAK

## DESCRIPTION

This project aims to make a use of keycloak which is an open source identity and access management solution
for modern applications and services.
Here we secure a service of customers using keycloak solution.

## Installation and running

- Keycloak
  - Install keycloak server and start it in development mode
  - Follow this link [Get started with keycloak](https://www.keycloak.org/getting-started/getting-started-zip) to set up a realm, create users, and register a client with id `keycloak-sec-client`
  - Make sure configuration is ok by logging in using created user's credentials

- Project
  - Get the project
    ````shell
    git clone https://github.com/Tountoun/keycloak-sec.git
    ````
  - Build the project
    ````shell
    cd keycloak-sec
    mvn clean install
    ````
  - Update the following properties by the name of the realm you created
    ````yaml
      spring:
        security:
          oauth2:
            resourceserver:
              jwt:
                issuer-uri: http://localhost:8080/realms/<your-realm>
                jwt-set-uri: http://localhost:8080/realms/<your_realm>/protocol/openid-connect/certs
      authorization:
          url: http://localhost:8080/realms/<your_realm>/protocol/openid-connect/token
    ````
  - Run the application in IntelliJ

## Contact
- Feel free to join me at [tountounabela@gmail.com](mailto://tountounabela@gmail.com)

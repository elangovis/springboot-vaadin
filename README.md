# Spring Boot + Vaadin sample login

A small Vaadin application secured with Spring Security. The root page is available only after signing in.

## Run locally

Requirements: Java 25 and Maven.

```shell
mvn spring-boot:run
```

Open [http://localhost:8080](http://localhost:8080) and sign in with:

- **Username:** `demo`
- **Password:** `vaadin`

The sample account is stored in memory and is for local demonstration only. Replace it with an application-specific user store and authentication policy before deploying.

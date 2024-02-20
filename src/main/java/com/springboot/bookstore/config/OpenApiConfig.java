package com.springboot.bookstore.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(

                contact = @Contact(
                        name = "Mohamed Saeed",
                        email = "muhamdsaid253@gmail.com",
                        url = "https://github.com/SaiD-MH"
                ),
                version = "1.0",
                description = "Documentation for Blog app rest api",
                title = "Open Api Doc",
                license = @License(
                        name = "Blog",
                        url = "License : https://github.com/SaiD-MH"

                ),
                summary = "API Documentation for Blog App"

        ),
        servers = {

                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                )
        }
        ,
        security = {
                @SecurityRequirement(name = "Bear Authentication")
        }

)
@SecurityScheme(
        name = "Bear Authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)

public class OpenApiConfig {
}
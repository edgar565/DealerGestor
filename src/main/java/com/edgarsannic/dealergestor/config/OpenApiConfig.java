package com.edgarsannic.dealergestor.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "DealerGestor API",
                version = "1.0",
                description = "Documentación de la API de DealerGestor"
        ),
        security = @SecurityRequirement(name = "bearerAuth"),
        servers = @Server(url = "/", description = "Servidor local")
)
@SecuritySchemes({
        @SecurityScheme(
                name = "bearerAuth",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT",
                description = "Usa ‘Authorize’ para incluir el token Bearer {token}"
        )
})
public class OpenApiConfig {
    // No hace falta añadir más; las anotaciones generan el componente
}

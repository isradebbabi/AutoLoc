package tn.esprit.autoloc.web;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "AutoLoc API",
        version = "1.0",
        description = "API REST de gestion d'une plateforme de location de véhicules multi-agences"
))
public class OpenApiConfig {
}

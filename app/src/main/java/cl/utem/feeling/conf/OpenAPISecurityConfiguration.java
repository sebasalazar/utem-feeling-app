package cl.utem.feeling.conf;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Sebastián Salazar Molina.
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de referencia para proyecto de computación distribuida UTEM primer semestre de 2023",
                version = "1.0.0",
                contact = @Contact(
                        name = "Sebastián Salazar Molina", email = "ssalazar@utem.cl", url = "https://sebastian.cl"
                )
        ),
        servers = @Server(
                url = "https://api.sebastian.cl/feeling/",
                description = "Production"
        )
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenAPISecurityConfiguration {

}

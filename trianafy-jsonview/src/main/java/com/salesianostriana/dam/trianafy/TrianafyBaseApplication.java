package com.salesianostriana.dam.trianafy;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
    @Info(
            description = "API de información de canciones y artistas, en la que podremos tener nuestras propias listas de canciones favoritas.",
            version = "1.0",
            contact = @Contact(email = "ruiz.lomar22@triana.salesianos.edu", name = "Mario Ruiz"),
            title = "Trianafy API",
            license = @License(name = "Licencia de Trianafy API"),
            termsOfService = "https://www.spotify.com/us/legal/end-user-agreement/"
    )
)
public class TrianafyBaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrianafyBaseApplication.class, args);
    }

}

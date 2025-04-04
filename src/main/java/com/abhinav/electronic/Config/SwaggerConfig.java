package com.abhinav.electronic.Config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myCustomOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Electronic Store Backend APIs")
                .description("This backend project is created by Abhinav Tiwari")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Abhinav Tiwari")
                    .email("abhinavtiwari3056@gmail.com")
                    .url("https://www.instagram.com/abhinav6901")
                )
                .termsOfService("https://www.abhinav-developer.com")
                .license(new io.swagger.v3.oas.models.info.License()
                    .name("API License")
                    .url("https://www.abhinavtiwari.com/about")
                )
            );
    }
}



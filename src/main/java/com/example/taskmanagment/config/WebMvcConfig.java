package com.example.taskmanagment.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "HEAD")
                .allowCredentials(false);
    }
    @Bean
    public OpenAPI myOpenAPI() {
        final String securityScheme = "Authentication";
        return new OpenAPI()
                .info(new Info()
                        .title("Task Menegment API Documantation")
                        .version("1.0")
                        .description("Spring Boot RESTful application"))
                .addSecurityItem(new SecurityRequirement().addList(securityScheme))
                .components(
                        new Components()
                                .addSecuritySchemes(securityScheme, new SecurityScheme()
                                        .name(securityScheme)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic"))

                );
    }

}

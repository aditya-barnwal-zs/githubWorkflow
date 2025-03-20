package com.zs.assignments.assignment11.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Swagger documentation.
 * Customizes the OpenAPI specification for the API.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configures the OpenAPI documentation with application information,
     * contact details, and available servers.
     *
     * @return The configured OpenAPI object
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Product Management API")
                        .description("REST API for managing products and categories")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Developer")
                                .email("aditya.barnwal@zopsmart.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));

    }
}

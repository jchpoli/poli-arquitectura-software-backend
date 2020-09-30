package com.apirest.backendapirest.configurations;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 *
 * @author Jonathan Castelblanco Higuera <jocastelblanco3@poligran.edu.co>
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket usersApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(pokemonApiInfo())
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }

    private ApiInfo pokemonApiInfo() {
        return new ApiInfoBuilder()
                .title("Servicios de Pokemon")
                .version("1.0")
                .license("Apache License Version 2.0")
                .build();
    }

}

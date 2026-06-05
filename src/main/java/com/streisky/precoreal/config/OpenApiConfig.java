package com.streisky.precoreal.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Preço Real API")
                        .description("API para sincronização de tabelas IBPT, identificação de NCM por IA e cálculo de preço sem impostos.")
                        .version("0.0.1-SNAPSHOT"));
    }
}

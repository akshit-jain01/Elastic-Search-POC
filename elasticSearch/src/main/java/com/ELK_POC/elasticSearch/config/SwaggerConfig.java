package com.ELK_POC.elasticSearch.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI practicalJavaOpenApi() {
        var info = new Info()
                .title("ElasticSearch POC")
                .description("OpenApi (Swagger) documentation auto generated from code")
                .version("1.0");

        return new OpenAPI().info(info);
    }
}

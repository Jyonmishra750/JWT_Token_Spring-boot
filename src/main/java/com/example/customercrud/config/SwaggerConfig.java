package com.example.customercrud.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(title = "JWT Token Generate Project",
description = "This project will generate JWT Token to authenticate other APIs",
summary = "Generates Token",
termsOfService = "Jyonmishra@2024",
contact = @Contact(name = "Jyoti Narayan Mishra", email = "jyotinarayanmishra05@gmail.com",
        url = "https://github.com/Jyonmishra750"), version = "Api/V1"))

@SecurityScheme(name = "Authorization",
in = SecuritySchemeIn.HEADER,
type = SecuritySchemeType.APIKEY,
bearerFormat = "JWT",
scheme = "bearer",
description = "JWT Token with Spring Security")
@Configuration
public class SwaggerConfig {
}

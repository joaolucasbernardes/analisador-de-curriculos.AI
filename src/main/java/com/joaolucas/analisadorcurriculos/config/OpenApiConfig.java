package com.joaolucas.analisadorcurriculos.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "API de Análise de Currículos com IA", version = "1.0.0", description = "API para extração de dados de PDFs e análise utilizando Google Gemini"), security = @SecurityRequirement(name = "ApiKeyAuth") // Aplica
                                                                                                                                                                                                                                               // globalmente
)
@SecurityScheme(name = "ApiKeyAuth", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER, paramName = "x-api-key", description = "Informe a sua chave de API para autenticar as requisições.")
public class OpenApiConfig {
}

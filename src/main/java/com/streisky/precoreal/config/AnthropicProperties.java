package com.streisky.precoreal.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Component
@Validated
@ConfigurationProperties(prefix = "anthropic")
@ConditionalOnProperty(name = "ai.provider", havingValue = "anthropic")
public class AnthropicProperties {

    @NotBlank(message = "Chave Anthropic não encontrada. Configure a variável de ambiente ANTHROPIC_API_KEY ou a propriedade anthropic.api-key.")
    private String apiKey;

    @NotBlank
    private String model;

    @Positive
    private long maxTokens;
}

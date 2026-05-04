package com.streisky.precoreal.clients;

import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.streisky.precoreal.clients.interfaces.AiClient;
import com.streisky.precoreal.config.AnthropicProperties;
import com.streisky.precoreal.exceptions.AiClientException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "ai.provider", havingValue = "anthropic")
public class ClaudeAiClientImpl implements AiClient {

    private final AnthropicClient client;
    private final String model;
    private final long maxTokens;

    public ClaudeAiClientImpl(AnthropicProperties props) {
        this.client = AnthropicOkHttpClient.builder().apiKey(props.getApiKey()).build();
        this.model = props.getModel();
        this.maxTokens = props.getMaxTokens();
    }

    @Override
    public String chat(String systemPrompt, String userMessage) {
        MessageCreateParams params = MessageCreateParams.builder()
            .model(model)
            .maxTokens(maxTokens)
            .system(systemPrompt)
            .addUserMessage(userMessage)
            .build();

        Message response = client.messages().create(params);
        return response.content().stream()
            .flatMap(block -> block.text().stream())
            .map(block -> block.text())
            .findFirst()
            .orElseThrow(() -> new AiClientException("Resposta vazia do Claude"));
    }
}

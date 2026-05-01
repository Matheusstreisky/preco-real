package com.streisky.precoreal.clients;

import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.streisky.precoreal.clients.interfaces.AiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "ai.provider", havingValue = "claude")
public class ClaudeAiClientImpl implements AiClient {

    private final AnthropicClient client;

    public ClaudeAiClientImpl(@Value("${anthropic.api-key}") String apiKey) {
        this.client = apiKey.isBlank()
            ? AnthropicOkHttpClient.fromEnv()
            : AnthropicOkHttpClient.builder().apiKey(apiKey).build();
    }

    @Override
    public String chat(String systemPrompt, String userMessage) {
        MessageCreateParams params = MessageCreateParams.builder()
            .model("claude-haiku-4-5")
            .maxTokens(256L)
            .system(systemPrompt)
            .addUserMessage(userMessage)
            .build();

        Message response = client.messages().create(params);
        return response.content().stream()
            .flatMap(block -> block.text().stream())
            .map(block -> block.text())
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Resposta vazia do Claude"));
    }
}

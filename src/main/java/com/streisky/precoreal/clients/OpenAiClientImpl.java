package com.streisky.precoreal.clients;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.streisky.precoreal.clients.interfaces.AiClient;
import com.streisky.precoreal.config.OpenAiProperties;
import com.streisky.precoreal.exceptions.AiClientException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
@ConditionalOnProperty(name = "ai.provider", havingValue = "openai")
public class OpenAiClientImpl implements AiClient {

    private final OpenAIClient client;
    private final String model;
    private final long maxTokens;

    public OpenAiClientImpl(OpenAiProperties props) {
        this.client = OpenAIOkHttpClient.builder().apiKey(props.getApiKey()).build();
        this.model = props.getModel();
        this.maxTokens = props.getMaxTokens();
    }

    @Override
    public String chat(String systemPrompt, String userMessage) {
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
            .model(model)
            .maxCompletionTokens(maxTokens)
            .addSystemMessage(systemPrompt)
            .addUserMessage(userMessage)
            .build();

        ChatCompletion completion = client.chat().completions().create(params);
        return completion.choices().getFirst().message().content()
            .orElseThrow(() -> new AiClientException("Resposta vazia do OpenAI"));
    }
}

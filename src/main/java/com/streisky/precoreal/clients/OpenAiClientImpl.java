package com.streisky.precoreal.clients;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.streisky.precoreal.clients.interfaces.AiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
@ConditionalOnProperty(name = "ai.provider", havingValue = "openai")
public class OpenAiClientImpl implements AiClient {

    private final OpenAIClient client;

    public OpenAiClientImpl(@Value("${openai.api-key}") String apiKey) {
        if (apiKey.isBlank()) {
            throw new IllegalStateException(
                "Chave OpenAI não encontrada. Configure a variável de ambiente OPENAI_API_KEY ou a propriedade openai.api-key.");
        }
        this.client = OpenAIOkHttpClient.builder().apiKey(apiKey).build();
    }

    @Override
    public String chat(String systemPrompt, String userMessage) {
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
            .model(ChatModel.GPT_4O_MINI)
            .maxTokens(256L)
            .addSystemMessage(systemPrompt)
            .addUserMessage(userMessage)
            .build();

        ChatCompletion completion = client.chat().completions().create(params);
        return completion.choices().get(0).message().content()
            .orElseThrow(() -> new RuntimeException("Resposta vazia do OpenAI"));
    }
}

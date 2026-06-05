package com.streisky.precoreal.clients;

import com.openai.client.OpenAIClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.streisky.precoreal.config.OpenAiProperties;
import com.streisky.precoreal.exceptions.AiClientException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OpenAiClientImplTest {

    private OpenAiClientImpl openAiClient;
    private OpenAIClient mockOpenAiClient;

    @BeforeEach
    void setUp() {
        OpenAiProperties props = new OpenAiProperties();
        props.setApiKey("test-api-key");
        props.setModel("gpt-4o");
        props.setMaxTokens(1024);

        openAiClient = new OpenAiClientImpl(props);

        mockOpenAiClient = mock(OpenAIClient.class, RETURNS_DEEP_STUBS);
        ReflectionTestUtils.setField(openAiClient, "client", mockOpenAiClient);
    }

    @Test
    void chat_whenResponseIsEmpty_throwsAiClientException() {
        ChatCompletion mockCompletion = mock(ChatCompletion.class, RETURNS_DEEP_STUBS);
        when(mockOpenAiClient.chat().completions().create(any(ChatCompletionCreateParams.class))).thenReturn(mockCompletion);
        when(mockCompletion.choices().getFirst().message().content()).thenReturn(Optional.empty());

        assertThatThrownBy(() -> openAiClient.chat("system prompt", "user message"))
                .isInstanceOf(AiClientException.class)
                .hasMessage("Resposta vazia do OpenAI");
    }
}

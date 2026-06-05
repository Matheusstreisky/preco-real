package com.streisky.precoreal.clients;

import com.anthropic.client.AnthropicClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.streisky.precoreal.config.AnthropicProperties;
import com.streisky.precoreal.exceptions.AiClientException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ClaudeAiClientImplTest {

    private ClaudeAiClientImpl claudeClient;
    private AnthropicClient mockAnthropicClient;

    @BeforeEach
    void setUp() {
        AnthropicProperties props = new AnthropicProperties();
        props.setApiKey("test-api-key");
        props.setModel("claude-3-5-sonnet-20241022");
        props.setMaxTokens(1024);

        claudeClient = new ClaudeAiClientImpl(props);

        mockAnthropicClient = mock(AnthropicClient.class, RETURNS_DEEP_STUBS);
        ReflectionTestUtils.setField(claudeClient, "client", mockAnthropicClient);
    }

    @Test
    void chat_whenResponseIsEmpty_throwsAiClientException() {
        Message mockMessage = mock(Message.class);
        when(mockAnthropicClient.messages().create(any(MessageCreateParams.class))).thenReturn(mockMessage);
        when(mockMessage.content()).thenReturn(List.of());

        assertThatThrownBy(() -> claudeClient.chat("system prompt", "user message"))
                .isInstanceOf(AiClientException.class)
                .hasMessage("Resposta vazia do Claude");
    }
}

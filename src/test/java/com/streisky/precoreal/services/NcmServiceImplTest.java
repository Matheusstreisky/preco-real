package com.streisky.precoreal.services;

import com.streisky.precoreal.clients.interfaces.AiClient;
import com.streisky.precoreal.dtos.NcmResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NcmServiceImplTest {

    @Mock
    private AiClient aiClient;

    @InjectMocks
    private NcmServiceImpl service;

    @Test
    void findNcm_whenValidJson_returnsDto() {
        String json = """
                {"ncm":"85171299","descricao":"Aparelhos telefônicos","confianca":"alta"}
                """;
        when(aiClient.chat(any(), contains("Smartphone"))).thenReturn(json);

        NcmResponseDto result = service.findNcm("Smartphone");

        assertThat(result.getNcm()).isEqualTo("85171299");
        assertThat(result.getDescricao()).isEqualTo("Aparelhos telefônicos");
        assertThat(result.getConfianca()).isEqualTo("alta");
    }

    @Test
    void findNcm_includesProductNameInUserMessage() {
        String json = """
                {"ncm":"84713012","descricao":"Máquinas automáticas","confianca":"alta"}
                """;
        when(aiClient.chat(any(), contains("Notebook"))).thenReturn(json);

        NcmResponseDto result = service.findNcm("Notebook");

        assertThat(result.getNcm()).isEqualTo("84713012");
    }

    @Test
    void findNcm_whenInvalidJson_throwsRuntimeException() {
        when(aiClient.chat(any(), any())).thenReturn("not valid json");

        assertThatThrownBy(() -> service.findNcm("Smartphone"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Resposta inválida do provedor de IA");
    }
}

package com.streisky.precoreal.clients;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IbptDownloadClientImplTest {

    @InjectMocks
    private IbptDownloadClientImpl client;

    @Test
    void download_whenUrlTemplateIsBlank_throwsIllegalStateException() {
        ReflectionTestUtils.setField(client, "urlTemplate", "");

        assertThatThrownBy(() -> client.download("SP"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("ibpt.download.url-template");
    }

    @Test
    void download_whenUrlTemplateIsNull_throwsIllegalStateException() {
        ReflectionTestUtils.setField(client, "urlTemplate", null);

        assertThatThrownBy(() -> client.download("SP"))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void download_replacesUfInUrlAndReturnsBody() {
        RestClient mockRestClient = mock(RestClient.class, RETURNS_DEEP_STUBS);
        ReflectionTestUtils.setField(client, "restClient", mockRestClient);
        ReflectionTestUtils.setField(client, "urlTemplate", "https://example.com/{uf}/data");
        when(mockRestClient.get().uri("https://example.com/SP/data").retrieve().body(String.class))
                .thenReturn("[{}]");

        String result = client.download("SP");

        assertThat(result).isEqualTo("[{}]");
    }
}

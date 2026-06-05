package com.streisky.precoreal.services;

import com.streisky.precoreal.clients.interfaces.IbptDownloadClient;
import com.streisky.precoreal.dtos.SyncResultDto;
import com.streisky.precoreal.factories.IbptFactory;
import com.streisky.precoreal.models.Ibpt;
import com.streisky.precoreal.parsers.interfaces.IbptParser;
import com.streisky.precoreal.services.interfaces.IbptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IbptSyncServiceImplTest {

    @Mock private IbptDownloadClient ibptDownloadClient;
    @Mock private IbptParser ibptParser;
    @Mock private IbptService ibptService;

    @InjectMocks
    private IbptSyncServiceImpl service;

    private final IbptFactory ibptFactory = new IbptFactory();

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "ufs", new String[]{"SP", "RJ"});
    }

    @Test
    void syncByUf_downloadsAndSaves() {
        String content = "[{}]";
        List<Ibpt> entries = List.of(ibptFactory.build("12345678", "SP"));
        when(ibptDownloadClient.download("SP")).thenReturn(content);
        when(ibptParser.parse(content, "SP")).thenReturn(entries);
        when(ibptService.saveIbpt("SP", entries)).thenReturn(1);

        int result = service.syncByUf("SP");

        assertThat(result).isEqualTo(1);
    }

    @Test
    void syncByUfAndCsv_parsesAndSaves() {
        String content = "csv content";
        List<Ibpt> entries = List.of(ibptFactory.build("12345678", "SP"));
        when(ibptParser.parse(content, "SP")).thenReturn(entries);
        when(ibptService.saveIbpt("SP", entries)).thenReturn(1);

        int result = service.syncByUfAndCsv("SP", content);

        assertThat(result).isEqualTo(1);
    }

    @Test
    void syncAll_processesAllConfiguredUfs() {
        when(ibptDownloadClient.download(any())).thenReturn("[]");
        when(ibptParser.parse(any(), any())).thenReturn(List.of());
        when(ibptService.saveIbpt(any(), any())).thenReturn(0);

        SyncResultDto result = service.syncAll();

        assertThat(result.getUfsProcessed()).isEqualTo(2);
        assertThat(result.getUfsWithErrors()).isEqualTo(0);
        assertThat(result.getTotalEntriesSynced()).isEqualTo(0);
        assertThat(result.getErrors()).isEmpty();
    }

    @Test
    void syncAll_whenUfFails_countsAsError() {
        when(ibptDownloadClient.download("SP")).thenThrow(new RuntimeException("timeout"));
        when(ibptDownloadClient.download("RJ")).thenReturn("[]");
        when(ibptParser.parse(any(), eq("RJ"))).thenReturn(List.of());
        when(ibptService.saveIbpt(eq("RJ"), any())).thenReturn(0);

        SyncResultDto result = service.syncAll();

        assertThat(result.getUfsProcessed()).isEqualTo(1);
        assertThat(result.getUfsWithErrors()).isEqualTo(1);
        assertThat(result.getErrors()).hasSize(1)
                .first().asString().contains("SP");
    }
}

package com.streisky.precoreal.controllers;

import com.streisky.precoreal.factories.SyncResultFactory;
import com.streisky.precoreal.services.interfaces.IbptService;
import com.streisky.precoreal.services.interfaces.IbptSyncService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IbptSyncControllerImpl.class)
class IbptSyncControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IbptService ibptService;

    @MockitoBean
    private IbptSyncService ibptSyncService;

    private final SyncResultFactory syncResultFactory = new SyncResultFactory();

    @Test
    void findAllByUf_returnsOk() throws Exception {
        when(ibptService.findAllByUf(eq("SP"), any())).thenReturn(Page.empty());

        mockMvc.perform(get("/api/ibpt").param("uf", "SP"))
                .andExpect(status().isOk());
    }

    @Test
    void syncAll_returnsOkWithResult() throws Exception {
        when(ibptSyncService.syncAll()).thenReturn(syncResultFactory.build());

        mockMvc.perform(post("/api/ibpt/sync"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ufsProcessed").value(2))
                .andExpect(jsonPath("$.totalEntriesSynced").value(100))
                .andExpect(jsonPath("$.ufsWithErrors").value(0));
    }

    @Test
    void syncByUf_returnsEntriesSynced() throws Exception {
        when(ibptSyncService.syncByUf("SP")).thenReturn(500);

        mockMvc.perform(post("/api/ibpt/sync/SP"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uf").value("SP"))
                .andExpect(jsonPath("$.entriesSynced").value(500));
    }

    @Test
    void syncByUfAndCsv_withTextPlain_returnsEntriesSynced() throws Exception {
        when(ibptSyncService.syncByUfAndCsv(eq("SP"), any())).thenReturn(300);

        mockMvc.perform(post("/api/ibpt/sync/SP")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("csv data here"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uf").value("SP"))
                .andExpect(jsonPath("$.entriesSynced").value(300));
    }
}

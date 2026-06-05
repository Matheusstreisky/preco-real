package com.streisky.precoreal.controllers;

import com.streisky.precoreal.factories.NcmFactory;
import com.streisky.precoreal.services.interfaces.NcmService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NcmControllerImpl.class)
class NcmControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NcmService ncmService;

    private final NcmFactory ncmFactory = new NcmFactory();

    @Test
    void findNcm_returnsOkWithDto() throws Exception {
        when(ncmService.findNcm("Smartphone")).thenReturn(ncmFactory.build());

        mockMvc.perform(get("/api/products/ncm").param("productName", "Smartphone"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ncm").value("85171299"))
                .andExpect(jsonPath("$.descricao").value("Aparelhos telefônicos"))
                .andExpect(jsonPath("$.confianca").value("alta"));
    }

    @Test
    void findNcm_whenServiceThrowsNotFound_returns404() throws Exception {
        when(ncmService.findNcm(any())).thenThrow(new NoSuchElementException("NCM não encontrado"));

        mockMvc.perform(get("/api/products/ncm").param("productName", "ProdutoDesconhecido"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NCM não encontrado"));
    }

    @Test
    void findNcm_whenServiceThrowsRuntime_returns500() throws Exception {
        when(ncmService.findNcm(any())).thenThrow(new RuntimeException("Erro da IA"));

        mockMvc.perform(get("/api/products/ncm").param("productName", "Qualquer"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Erro da IA"));
    }
}

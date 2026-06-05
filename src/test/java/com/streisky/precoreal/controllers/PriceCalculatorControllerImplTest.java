package com.streisky.precoreal.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.streisky.precoreal.factories.PriceCalculationFactory;
import com.streisky.precoreal.services.interfaces.PriceCalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceCalculatorControllerImpl.class)
class PriceCalculatorControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PriceCalculatorService priceCalculatorService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PriceCalculationFactory priceCalculationFactory = new PriceCalculationFactory();

    @Test
    void calculate_returnsOkWithResponse() throws Exception {
        when(priceCalculatorService.calculate(any())).thenReturn(priceCalculationFactory.buildResponse());

        mockMvc.perform(post("/api/taxes/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(priceCalculationFactory.buildRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalPrice").value(100.00))
                .andExpect(jsonPath("$.priceWithoutTax").value(85.47))
                .andExpect(jsonPath("$.taxValue").value(14.53))
                .andExpect(jsonPath("$.totalTaxRate").value(17.00));
    }

    @Test
    void calculate_whenNcmNotFound_returns404() throws Exception {
        when(priceCalculatorService.calculate(any()))
                .thenThrow(new NoSuchElementException("NCM 99999999 não encontrado para UF SP"));

        mockMvc.perform(post("/api/taxes/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(priceCalculationFactory.buildRequest())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NCM 99999999 não encontrado para UF SP"));
    }
}

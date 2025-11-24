package com.dor.quoteapi.controller;

import com.dor.quoteapi.service.QuoteService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuoteController.class)
class QuoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuoteService quoteService;

    @Test
    void shouldReturnQuoteInJson() throws Exception {
        when(quoteService.getRandomQuote()).thenReturn("test quote");

        mockMvc.perform(get("/api/quote"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quote").value("test quote"));
    }
}


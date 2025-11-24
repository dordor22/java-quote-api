package com.dor.quoteapi.controller;

import com.dor.quoteapi.service.QuoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("/api/quote")
    public Map<String, String> getQuote() {
        return Map.of("quote", quoteService.getRandomQuote());
    }
}


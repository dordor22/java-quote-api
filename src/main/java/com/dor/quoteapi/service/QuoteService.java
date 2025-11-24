package com.dor.quoteapi.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class QuoteService {

    private final List<String> quotes = List.of(
            "Simplicity is the ultimate sophistication.",
            "First, solve the problem. Then, write the code.",
            "Make it work, make it right, make it fast.",
            "Code is like humor. When you have to explain it, it’s bad.",
            "The best error message is the one that never shows up."
    );

    private final Random random = new Random();

    public String getRandomQuote() {
        return quotes.get(random.nextInt(quotes.size()));
    }
}


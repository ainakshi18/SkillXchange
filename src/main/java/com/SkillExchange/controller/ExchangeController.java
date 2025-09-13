package com.SkillExchange.controller;

import com.SkillExchange.model.Exchange;
import com.SkillExchange.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exchanges")
public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;

    // Get all exchanges
    @GetMapping
    public List<Exchange> getAllExchanges() {
        return exchangeService.browseAllExchanges();
    }

    // Get exchange by ID
    @GetMapping("/{id}")
    public Exchange getExchangeById(@PathVariable String id) {
        return exchangeService.getExchangeById(id);
    }

    // Create a new exchange
    @PostMapping
    public Exchange createExchange(@RequestBody Exchange exchange) {
        return exchangeService.postExchangeRequest(exchange);
    }

    // Update an exchange (status, progress, etc.)
    @PutMapping("/{id}")
    public Exchange updateExchange(
            @PathVariable String id,
            @RequestBody Exchange exchange
    ) {
        return exchangeService.updateExchange(id, exchange);
    }

    // Delete an exchange
    @DeleteMapping("/{id}")
    public void deleteExchange(@PathVariable String id) {
        exchangeService.deleteExchange(id);
    }
}

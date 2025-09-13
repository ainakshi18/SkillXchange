package com.SkillExchange.service;

import com.SkillExchange.model.Exchange;
import com.SkillExchange.repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeService {

    @Autowired
    private ExchangeRepository exchangeRepository;

    // Get all exchanges
    public List<Exchange> browseAllExchanges() {
        return exchangeRepository.findAll();
    }

    // Get by ID
    public Exchange getExchangeById(String id) {
        return exchangeRepository.findById(id).orElse(null);
    }

    // Create exchange
    public Exchange postExchangeRequest(Exchange exchange) {
        return exchangeRepository.save(exchange);
    }

    // Update exchange
    public Exchange updateExchange(String id, Exchange updatedExchange) {
        return exchangeRepository.findById(id).map(exchange -> {

            if (updatedExchange.getRequesterId() != null)
                exchange.setRequesterId(updatedExchange.getRequesterId());

            if (updatedExchange.getProviderId() != null)
                exchange.setProviderId(updatedExchange.getProviderId());

            if (updatedExchange.getSkillOffered() != null)
                exchange.setSkillOffered(updatedExchange.getSkillOffered());

            if (updatedExchange.getSkillRequested() != null)
                exchange.setSkillRequested(updatedExchange.getSkillRequested());

            if (updatedExchange.getStatus() != null)
                exchange.setStatus(updatedExchange.getStatus());

            if (updatedExchange.getStartDate() != null)
                exchange.setStartDate(updatedExchange.getStartDate());

            if (updatedExchange.getEndDate() != null)
                exchange.setEndDate(updatedExchange.getEndDate());

            if (updatedExchange.getProgress() != null)
                exchange.setProgress(updatedExchange.getProgress());

            // for int, check manually (avoid overwriting 0 unintentionally)
            if (updatedExchange.getKarmaTransfer() != 0)
                exchange.setKarmaTransfer(updatedExchange.getKarmaTransfer());

            if (updatedExchange.getFeedbackId() != null)
                exchange.setFeedbackId(updatedExchange.getFeedbackId());

            return exchangeRepository.save(exchange);
        }).orElse(null);
    }


    // Delete exchange
    public void deleteExchange(String id) {
        exchangeRepository.deleteById(id);
    }
}

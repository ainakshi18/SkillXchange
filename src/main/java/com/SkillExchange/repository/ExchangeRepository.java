package com.SkillExchange.repository;

import com.SkillExchange.model.Exchange;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ExchangeRepository extends MongoRepository<Exchange, String> {
    List<Exchange> findByRequesterId(String requesterId);
    List<Exchange> findByProviderId(String providerId);
    List<Exchange> findByStatus(String status);
}

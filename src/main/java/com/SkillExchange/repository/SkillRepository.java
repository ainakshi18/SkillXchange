package com.SkillExchange.repository;

import com.SkillExchange.model.Skill;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SkillRepository extends MongoRepository<Skill, String> {
    Optional<Skill> findBySkillName(String skillName);  // extra query if needed
}

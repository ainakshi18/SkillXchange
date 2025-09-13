package com.SkillExchange.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.SkillExchange.model.BaseUser;
import com.SkillExchange.model.User;

@Repository
public interface BaseUserRepository extends MongoRepository<BaseUser, String> {
    Optional<BaseUser> findByEmail(String email);
}
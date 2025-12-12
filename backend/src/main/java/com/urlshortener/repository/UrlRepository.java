package com.urlshortener.repository;

import com.urlshortener.model.UrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends MongoRepository<UrlEntity, String> {
    Optional<UrlEntity> findByShortCode(String shortCode);
    boolean existsByShortCode(String shortCode);
}


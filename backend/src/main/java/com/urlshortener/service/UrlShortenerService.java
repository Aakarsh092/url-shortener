package com.urlshortener.service;

import com.urlshortener.dto.AnalyticsResponse;
import com.urlshortener.dto.ShortenUrlRequest;
import com.urlshortener.dto.ShortenUrlResponse;
import com.urlshortener.model.UrlEntity;
import com.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlShortenerService {

    private final UrlRepository urlRepository;
    private final Random random = new Random();

    @Value("${app.base-url}")
    private String baseUrl;

    public UrlShortenerService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public ShortenUrlResponse shortenUrl(ShortenUrlRequest request) {
        String originalUrl = request.getOriginalUrl();
        
        // Check if URL already exists
        Optional<UrlEntity> existingUrl = urlRepository.findAll().stream()
                .filter(url -> url.getOriginalUrl().equals(originalUrl))
                .findFirst();

        if (existingUrl.isPresent()) {
            UrlEntity entity = existingUrl.get();
            return new ShortenUrlResponse(
                    baseUrl + "/" + entity.getShortCode(),
                    entity.getShortCode(),
                    entity.getOriginalUrl()
            );
        }

        // Generate unique short code
        String shortCode = generateShortCode();
        while (urlRepository.existsByShortCode(shortCode)) {
            shortCode = generateShortCode();
        }

        // Create and save URL entity
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setOriginalUrl(originalUrl);
        urlEntity.setShortCode(shortCode);
        urlEntity.setClickCount(0L);
        urlEntity.setCreatedAt(LocalDateTime.now());
        urlEntity.setLastAccessedAt(null);

        urlRepository.save(urlEntity);

        return new ShortenUrlResponse(
                baseUrl + "/" + shortCode,
                shortCode,
                originalUrl
        );
    }

    public Optional<String> getOriginalUrl(String shortCode) {
        Optional<UrlEntity> urlEntity = urlRepository.findByShortCode(shortCode);
        
        if (urlEntity.isPresent()) {
            UrlEntity entity = urlEntity.get();
            // Update click count and last accessed time
            entity.setClickCount(entity.getClickCount() + 1);
            entity.setLastAccessedAt(LocalDateTime.now());
            urlRepository.save(entity);
            return Optional.of(entity.getOriginalUrl());
        }
        
        return Optional.empty();
    }

    public Optional<AnalyticsResponse> getAnalytics(String shortCode) {
        Optional<UrlEntity> urlEntity = urlRepository.findByShortCode(shortCode);
        
        if (urlEntity.isPresent()) {
            UrlEntity entity = urlEntity.get();
            return Optional.of(new AnalyticsResponse(
                    entity.getShortCode(),
                    entity.getOriginalUrl(),
                    entity.getClickCount(),
                    entity.getCreatedAt(),
                    entity.getLastAccessedAt()
            ));
        }
        
        return Optional.empty();
    }

    private String generateShortCode() {
        // Generate a random 6-character alphanumeric code
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }
        return code.toString();
    }
}


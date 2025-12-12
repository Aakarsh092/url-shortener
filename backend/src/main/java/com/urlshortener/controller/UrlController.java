package com.urlshortener.controller;

import com.urlshortener.dto.AnalyticsResponse;
import com.urlshortener.dto.ShortenUrlRequest;
import com.urlshortener.dto.ShortenUrlResponse;
import com.urlshortener.service.UrlShortenerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UrlController {

    private final UrlShortenerService urlShortenerService;

    public UrlController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<ShortenUrlResponse> shortenUrl(@Valid @RequestBody ShortenUrlRequest request) {
        ShortenUrlResponse response = urlShortenerService.shortenUrl(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{shortCode}")
    public RedirectView redirect(@PathVariable String shortCode) {
        Optional<String> originalUrl = urlShortenerService.getOriginalUrl(shortCode);
        
        if (originalUrl.isPresent()) {
            return new RedirectView(originalUrl.get());
        }
        
        // If not found, redirect to frontend with error
        return new RedirectView("http://localhost:3000?error=notfound");
    }

    @GetMapping("/analytics/{shortCode}")
    public ResponseEntity<AnalyticsResponse> getAnalytics(@PathVariable String shortCode) {
        Optional<AnalyticsResponse> analytics = urlShortenerService.getAnalytics(shortCode);
        
        if (analytics.isPresent()) {
            return ResponseEntity.ok(analytics.get());
        }
        
        return ResponseEntity.notFound().build();
    }
}


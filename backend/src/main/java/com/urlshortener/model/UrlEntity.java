package com.urlshortener.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "urls")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlEntity {
    @Id
    private String id;
    private String originalUrl;
    private String shortCode;
    private Long clickCount;
    private LocalDateTime createdAt;
    private LocalDateTime lastAccessedAt;
}


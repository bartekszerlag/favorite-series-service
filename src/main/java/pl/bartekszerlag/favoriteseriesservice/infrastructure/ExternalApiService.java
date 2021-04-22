package pl.bartekszerlag.favoriteseriesservice.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
class ExternalApiService {

    private final Logger logger = LoggerFactory.getLogger(ExternalApiService.class);
    private final RestTemplate restTemplate;

    @Value("${omdb.uri}")
    private String omdbUri;

    @Value("${omdb.key}")
    private String omdbKey;

    @Value("${bored.uri}")
    private String boredUri;

    ExternalApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    Double getSeriesRating(String title) {
        URI uri = getTargetUri(title);
        String response = restTemplate.getForObject(uri, String.class);
        Double rating = null;

        try {
            rating = new ObjectMapper().readTree(response).path("imdbRating").asDouble();
        } catch (JsonProcessingException e) {
            logger.info("--- Fetching series rating failed ---");
        }

        return rating;
    }

    String generateSecretMessage() {
        URI uri = URI.create(boredUri);
        String response = restTemplate.getForObject(uri, String.class);
        String secretMessage = "If you are bored of watching TV series try: ";

        try {
            secretMessage += new ObjectMapper().readTree(response).path("activity").asText();
        } catch (JsonProcessingException e) {
            secretMessage += "Bored API service unavailable. Please try later";
        }

        return secretMessage;
    }

    private URI getTargetUri(String title) {
        return UriComponentsBuilder.fromUriString(omdbUri)
                .queryParam("apikey", omdbKey)
                .queryParam("t", title)
                .build()
                .encode()
                .toUri();
    }
}
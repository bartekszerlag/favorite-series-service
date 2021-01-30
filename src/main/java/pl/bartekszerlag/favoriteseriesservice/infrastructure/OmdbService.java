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
class OmdbService {

    private final Logger logger = LoggerFactory.getLogger(OmdbService.class);

    @Value("${omdb.host}")
    private String host;

    @Value("${api.key}")
    private String apiKey;

    Double getSeriesRating(String title) {
        RestTemplate restTemplate = new RestTemplate();
        URI targetUrl = UriComponentsBuilder.fromUriString(host)
                .queryParam("apikey", apiKey)
                .queryParam("t", title)
                .build()
                .encode()
                .toUri();

        Double rating = null;
        String response = restTemplate.getForObject(targetUrl, String.class);
        try {
            rating = new ObjectMapper().readTree(response).path("imdbRating").asDouble();
        } catch (JsonProcessingException e) {
            logger.debug("--- Fetching series rating failed ---");
        }
        return rating;
    }
}
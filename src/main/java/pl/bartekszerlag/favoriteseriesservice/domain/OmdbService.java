package pl.bartekszerlag.favoriteseriesservice.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@Service
class OmdbService {

    private final RestTemplate restTemplate;

    @Value("${omdb.host}")
    private String host;

    @Value("${api.key}")
    private String apiKey;

    OmdbService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    JsonNode getSeriesDetails(String title) throws IOException {
        URI targetUrl = UriComponentsBuilder.fromUriString(host)
                .queryParam("apikey", apiKey)
                .queryParam("t", title)
                .build()
                .encode()
                .toUri();

        String response = restTemplate.getForObject(targetUrl, String.class);

        return new ObjectMapper().readTree(response);
    }
}
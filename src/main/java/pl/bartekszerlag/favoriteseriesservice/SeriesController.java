package pl.bartekszerlag.favoriteseriesservice;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
class SeriesController {

    private final OmdbService omdbService;
    private final SeriesService seriesService;

    SeriesController(OmdbService omdbService, SeriesService service) {
        this.omdbService = omdbService;
        this.seriesService = service;
    }

    @GetMapping("/series")
    ResponseEntity<List<Series>> getAllSeries() {
        try {
            return ResponseEntity.ok(seriesService.findAll());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/series/{id}")
    ResponseEntity<JsonNode> getSeriesDetails(@PathVariable Integer id) throws IOException {
        try {
            String title = seriesService.getTitle(id);
            return ResponseEntity.ok(omdbService.getSeriesDetails(title));
        } catch (SeriesNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/series")
    ResponseEntity<Series> addSeries(@RequestBody Series series) {
        try {
            seriesService.add(series);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (SeriesLimitExceededException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    @PutMapping("/series/{id}")
    ResponseEntity<Series> updateSeries(@PathVariable Integer id, @RequestBody Series series) {
        try {
            return ResponseEntity.ok(seriesService.update(id, series));
        } catch (SeriesNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/series/{id}")
    ResponseEntity<Void> deleteSeries(@PathVariable Integer id) {
        try {
            seriesService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (SeriesNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
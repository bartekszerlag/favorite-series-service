package pl.bartekszerlag.favoriteseriesservice.infrastructure.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.bartekszerlag.favoriteseriesservice.domain.Series;
import pl.bartekszerlag.favoriteseriesservice.domain.SeriesAlreadyExistException;
import pl.bartekszerlag.favoriteseriesservice.domain.SeriesLimitExceededException;
import pl.bartekszerlag.favoriteseriesservice.domain.SeriesNotFoundException;
import pl.bartekszerlag.favoriteseriesservice.dto.SeriesDto;
import pl.bartekszerlag.favoriteseriesservice.infrastructure.SeriesService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
class SeriesController {

    private final SeriesService seriesService;

    SeriesController(SeriesService service) {
        this.seriesService = service;
    }

    @GetMapping("/series")
    ResponseEntity<List<SeriesDto>> getAllSeries() {
        return ResponseEntity.ok(seriesService.getAllSeries());
    }

    @PostMapping("/series")
    ResponseEntity<SeriesDto> addSeries(@RequestBody Series series) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(seriesService.addSeries(series));
        } catch (SeriesLimitExceededException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (SeriesAlreadyExistException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/series/{id}")
    ResponseEntity<Void> deleteSeries(@PathVariable Integer id) {
        try {
            seriesService.deleteSeries(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (SeriesNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value = "/secret")
    public ResponseEntity<String> getSecretMessage() {
        return ResponseEntity.status(HttpStatus.OK).body(seriesService.getSecretMessage());
    }
}
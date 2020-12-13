package pl.bartekszerlag.favoriteseriesservice.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bartekszerlag.favoriteseriesservice.domain.*;
import pl.bartekszerlag.favoriteseriesservice.dto.SeriesDto;

import java.util.ArrayList;
import java.util.List;

@RestController
class SeriesController {

    private final SeriesService seriesService;

    SeriesController(SeriesService service) {
        this.seriesService = service;
    }

    @GetMapping("/series")
    ResponseEntity<List<SeriesDto>> getAllSeries() {
        try {
            List<SeriesDto> seriesDtoList = new ArrayList<>();
            for (Series s : seriesService.findAll()) {
                SeriesDto dto = seriesService.toSeriesDto(s);
                seriesDtoList.add(dto);
            }
            return ResponseEntity.ok(seriesDtoList);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/series")
    ResponseEntity<Series> addSeries(@RequestBody Series series) {
        try {
            seriesService.add(series);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (SeriesLimitExceededException | SeriesAlreadyExistException e) {
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
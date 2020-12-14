package pl.bartekszerlag.favoriteseriesservice.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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
        List<SeriesDto> seriesDtoList = new ArrayList<>();
        for (Series s : seriesService.findAll()) {
            SeriesDto dto = seriesService.toSeriesDto(s);
            seriesDtoList.add(dto);
        }
        return ResponseEntity.ok(seriesDtoList);
    }

    @PostMapping("/series")
    ResponseEntity<Series> addSeries(@RequestBody Series series) {
        try {
            seriesService.add(series);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (SeriesLimitExceededException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (SeriesAlreadyExistException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/series/{id}")
    ResponseEntity<Void> deleteSeries(@PathVariable Integer id) {
        try {
            seriesService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (SeriesNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
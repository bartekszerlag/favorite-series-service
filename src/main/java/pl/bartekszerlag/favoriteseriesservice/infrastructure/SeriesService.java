package pl.bartekszerlag.favoriteseriesservice.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.bartekszerlag.favoriteseriesservice.domain.*;
import pl.bartekszerlag.favoriteseriesservice.dto.SeriesDto;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static pl.bartekszerlag.favoriteseriesservice.domain.Platform.*;

@Service
public
class SeriesService {

    @Value("${rank.limit}")
    private Integer rankLimit;
    private final SeriesRepository repository;
    private final ExternalApiService externalApiService;

    public SeriesService(SeriesRepository repository, ExternalApiService externalApiService) {
        this.repository = repository;
        this.externalApiService = externalApiService;
    }

    public List<SeriesDto> getAllSeries() {
        return repository.findAll()
                .stream()
                .map(this::toSeriesDto)
                .collect(Collectors.toList());
    }

    public SeriesDto addSeries(Series series) {
        if (getAllSeries().size() >= rankLimit) {
            throw new SeriesLimitExceededException(format("Series limit is: %d", rankLimit));
        }

        if (seriesExist(series)) {
            throw new SeriesAlreadyExistException(format("Series with title: %s already exist", series.getTitle()));
        }

        repository.save(series);

        return toSeriesDto(series);
    }

    public void deleteSeries(Integer id) {
        Series series = repository
                .findById(id)
                .orElseThrow(() -> new SeriesNotFoundException(format("Series with id: %d not exist", id)));

        repository.delete(series);
    }

    public String getSecretMessage() {
        return externalApiService.generateSecretMessage();
    }

    private SeriesDto toSeriesDto(Series series) {
        Platform platform = OTHER;
        Double rating = externalApiService.getSeriesRating(series.getTitle());
        String userPlatform = series.getPlatform().toUpperCase();

        if (userPlatform.equals(NETFLIX.getName())) {
            platform = NETFLIX;
        }
        if (userPlatform.equals(HBO.getName())) {
            platform = HBO;
        }

        return new SeriesDto(series.getId(), series.getTitle(), rating, platform);
    }

    private boolean seriesExist(Series series) {
        return getAllSeries()
                .stream()
                .anyMatch(s -> s.getTitle().equalsIgnoreCase(series.getTitle()));
    }
}
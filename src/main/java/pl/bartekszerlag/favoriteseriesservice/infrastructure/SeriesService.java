package pl.bartekszerlag.favoriteseriesservice.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.bartekszerlag.favoriteseriesservice.domain.*;
import pl.bartekszerlag.favoriteseriesservice.dto.SeriesDto;

import java.util.List;

import static java.lang.String.format;
import static pl.bartekszerlag.favoriteseriesservice.domain.Platform.*;

@Service
public
class SeriesService {

    @Value("${rank.limit}")
    private Integer rankLimit;

    private final SeriesRepository repository;
    private final OmdbService omdbService;

    public SeriesService(SeriesRepository repository, OmdbService omdbService) {
        this.repository = repository;
        this.omdbService = omdbService;
    }

    public List<Series> getAllSeries() {
        return repository.findAll();
    }

    public void addSeries(Series series) {
        if (getAllSeries().size() >= rankLimit) {
            throw new SeriesLimitExceededException(format("Series limit is: %d", rankLimit));
        }

        getAllSeries()
                .stream()
                .filter(s -> s.getTitle().equalsIgnoreCase(series.getTitle()))
                .forEach(s -> {
                    throw new SeriesAlreadyExistException(format("Series with title: %s already exist", series.getTitle()));
                });

        repository.save(series);
    }

    public void deleteSeries(Integer id) {
        Series series = repository
                .findById(id)
                .orElseThrow(() -> new SeriesNotFoundException(format("Series with id: %d not exist", id)));

        repository.delete(series);
    }

    public SeriesDto toSeriesDto(Series series) {
        Platform platform = OTHER;
        Double rating = omdbService.getSeriesRating(series.getTitle());
        String userPlatform = series.getPlatform().toUpperCase();

        if (userPlatform.equals(NETFLIX.getName())) {
            platform = NETFLIX;
        } else if (userPlatform.equals(HBO.getName())) {
            platform = HBO;
        }

        return new SeriesDto(series.getId(), series.getTitle(), rating, platform);
    }
}
package pl.bartekszerlag.favoriteseriesservice.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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

    public List<Series> findAll() {
        return repository.findAll();
    }

    public void add(Series series) {
        if (findAll().size() >= rankLimit) {
            throw new SeriesLimitExceededException(format("Series limit is: %d", rankLimit));
        }
        for (Series s : findAll()) {
            if (s.getTitle().toLowerCase().equals(series.getTitle().toLowerCase()))
                throw new SeriesAlreadyExistException(format("Series with title: %s already exist", series.getTitle()));
        }
        repository.save(series);
    }

    public void delete(Integer id) {
        Series series = repository.findById(id).orElseThrow(
                () -> new SeriesNotFoundException(format("Series with id: %d not exist", id))
        );
        repository.delete(series);
    }

    public SeriesDto toSeriesDto(Series series) {
        Platform platform = OTHER;
        Double rating = omdbService.getSeriesRating(series.getTitle());
        String platformName = series.getPlatform().toUpperCase();
        if (platformName.equals("NETFLIX")) {
            platform = NETFLIX;
        } else if (platformName.equals("HBO")) {
            platform = HBO;
        }
        return new SeriesDto(series.getId(), series.getTitle(), rating, platform);
    }
}
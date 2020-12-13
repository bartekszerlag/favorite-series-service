package pl.bartekszerlag.favoriteseriesservice.domain;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import pl.bartekszerlag.favoriteseriesservice.dto.SeriesDto;

import java.io.IOException;
import java.util.List;

import static pl.bartekszerlag.favoriteseriesservice.domain.Platform.*;

@Service
public
class SeriesService {

    public final static int RANK_LIMIT = 10;

    private final SeriesRepository repository;
    private final OmdbService omdbService;

    public SeriesService(SeriesRepository repository, OmdbService omdbService) {
        this.repository = repository;
        this.omdbService = omdbService;
    }

    public List<Series> findAll() {
        return repository.findAll();
    }

    public Series add(Series series) {
        if (findAll().size() >= RANK_LIMIT) {
            throw new SeriesLimitExceededException();
        }
        for (Series s : findAll()) {
            if (s.getTitle().toLowerCase().equals(series.getTitle().toLowerCase()))
                throw new SeriesAlreadyExistException();
        }
        return repository.save(series);
    }

    public Series update(Integer id, Series oldSeries) {
        Series series = findById(id);
        series.setTitle(oldSeries.getTitle());
        series.setPlatform(oldSeries.getPlatform());

        return repository.save(series);
    }

    public void delete(Integer id) {
        Series series = findById(id);
        repository.delete(series);
    }

    public SeriesDto toSeriesDto(Series series) {
        double rating = 0;
        Platform platform = OTHER;
        try {
            JsonNode seriesDetails = omdbService.getSeriesDetails(series.getTitle());
            rating = seriesDetails.path("imdbRating").asDouble();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String platformName = series.getPlatform().toUpperCase();
        if (platformName.equals("NETFLIX")) {
            platform = NETFLIX;
        } else if (platformName.equals("HBO")) {
            platform = HBO;
        }
        return new SeriesDto(series.getId(), series.getTitle(), rating, platform);
    }

    private Series findById(Integer id) {
        return repository.findById(id).orElseThrow(SeriesNotFoundException::new);
    }
}
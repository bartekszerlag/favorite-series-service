package pl.bartekszerlag.favoriteseriesservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bartekszerlag.favoriteseriesservice.entity.Series;
import pl.bartekszerlag.favoriteseriesservice.exception.SeriesLimitExceededException;
import pl.bartekszerlag.favoriteseriesservice.exception.SeriesNotFoundException;
import pl.bartekszerlag.favoriteseriesservice.repository.SeriesRepository;

import java.util.List;

@Service
public class SeriesService {

    public final static int RANK_LIMIT = 10;

    private final SeriesRepository repository;

    @Autowired
    SeriesService(SeriesRepository repository) {
        this.repository = repository;
    }

    public List<Series> findAll() {
        return repository.findAll();
    }

    public void add(Series series) {
        if (findAll().size() >= RANK_LIMIT) {
            throw new SeriesLimitExceededException();
        }
        repository.save(series);
    }

    public Series update(Integer id, Series oldSeries) {
        Series series = findById(id);
        series.setTitle(oldSeries.getTitle());
        series.setRate(oldSeries.getRate());
        series.setPlatform(oldSeries.getPlatform());

        return repository.save(series);
    }

    public void delete(Integer id) {
        Series series = findById(id);
        repository.delete(series);
    }

    public String getTitle(Integer id) {
        Series series = findById(id);
        return series.getTitle();
    }

    private Series findById(Integer id) {
        return repository.findById(id).orElseThrow(SeriesNotFoundException::new);
    }
}
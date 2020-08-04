package pl.bartekszerlag.favoriteseriesservice;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class SeriesService {

    public final static int RANK_LIMIT = 10;

    private final SeriesRepository repository;

    SeriesService(SeriesRepository repository) {
        this.repository = repository;
    }

    List<Series> findAll() {
        return repository.findAll();
    }

    void add(Series series) {
        if (findAll().size() >= RANK_LIMIT) {
            throw new SeriesLimitExceededException();
        }
        repository.save(series);
    }

    Series update(Integer id, Series oldSeries) {
        Series series = findById(id);
        series.setTitle(oldSeries.getTitle());
        series.setRate(oldSeries.getRate());
        series.setPlatform(oldSeries.getPlatform());

        return repository.save(series);
    }

    void delete(Integer id) {
        Series series = findById(id);
        repository.delete(series);
    }

    String getTitle(Integer id) {
        Series series = findById(id);
        return series.getTitle();
    }

    private Series findById(Integer id) {
        return repository.findById(id).orElseThrow(SeriesNotFoundException::new);
    }
}
package pl.bartekszerlag.favoriteseriesservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bartekszerlag.favoriteseriesservice.entity.Series;

public interface SeriesRepository extends JpaRepository<Series, Integer> {
}

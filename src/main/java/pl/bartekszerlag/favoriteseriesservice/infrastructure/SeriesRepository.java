package pl.bartekszerlag.favoriteseriesservice.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bartekszerlag.favoriteseriesservice.domain.Series;

interface SeriesRepository extends JpaRepository<Series, Integer> {
}
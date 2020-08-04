package pl.bartekszerlag.favoriteseriesservice;

import org.springframework.data.jpa.repository.JpaRepository;

interface SeriesRepository extends JpaRepository<Series, Integer> {
}

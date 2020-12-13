package pl.bartekszerlag.favoriteseriesservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

interface SeriesRepository extends JpaRepository<Series, Integer> {
}
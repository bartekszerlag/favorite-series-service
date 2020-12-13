package pl.bartekszerlag.favoriteseriesservice.dto;

import pl.bartekszerlag.favoriteseriesservice.domain.Platform;

public class SeriesDto {

    private Integer id;
    private String title;
    private Double imdbRating;
    private Platform platform;

    public SeriesDto(Integer id, String title, Double imdbRating, Platform platform) {
        this.id = id;
        this.title = title;
        this.imdbRating = imdbRating;
        this.platform = platform;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(Double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }
}
package pl.bartekszerlag.favoriteseriesservice.domain;

public class SeriesNotFoundException extends RuntimeException {
    public SeriesNotFoundException(String message) {
        super(message);
    }
}
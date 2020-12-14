package pl.bartekszerlag.favoriteseriesservice.domain;

public class SeriesLimitExceededException extends RuntimeException {
    public SeriesLimitExceededException(String message) {
        super(message);
    }
}
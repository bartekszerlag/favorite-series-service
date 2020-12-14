package pl.bartekszerlag.favoriteseriesservice.domain;

public class SeriesAlreadyExistException extends RuntimeException {
    public SeriesAlreadyExistException(String message) {
        super(message);
    }
}
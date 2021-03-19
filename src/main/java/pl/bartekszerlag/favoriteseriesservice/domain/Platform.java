package pl.bartekszerlag.favoriteseriesservice.domain;

public enum Platform {
    NETFLIX("NETFLIX"),
    HBO("HBO"),
    OTHER("OTHER");

    private String name;

    Platform(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
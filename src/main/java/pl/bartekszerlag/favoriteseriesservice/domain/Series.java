package pl.bartekszerlag.favoriteseriesservice.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "series")
public class Series {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private Integer id;
    private String title;
    private String platform;

    @SuppressWarnings("JPA needs it")
    public Series() {
    }

    private Series(Integer id, String title, String platform) {
        this.id = id;
        this.title = title;
        this.platform = platform;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public static class Builder {
        private Integer id;
        private String title;
        private String platform;

        public Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return  this;
        }

        public Builder withPlatform(String platform) {
            this.platform = platform;
            return  this;
        }

        public Series build() {
            return new Series(id, title, platform);
        }
    }
}
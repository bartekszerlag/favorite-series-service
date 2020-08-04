package pl.bartekszerlag.favoriteseriesservice;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "series")
class Series {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private Integer id;
    private String title;
    private Double rate;
    private String platform;

    @SuppressWarnings("JPA needs it")
    public Series() {
    }

    public Series(Integer id, String title, Double rate, String platform) {
        this.id = id;
        this.title = title;
        this.rate = rate;
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

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
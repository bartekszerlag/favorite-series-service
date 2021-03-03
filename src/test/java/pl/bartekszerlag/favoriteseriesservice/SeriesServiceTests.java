package pl.bartekszerlag.favoriteseriesservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import pl.bartekszerlag.favoriteseriesservice.domain.Series;
import pl.bartekszerlag.favoriteseriesservice.domain.SeriesAlreadyExistException;
import pl.bartekszerlag.favoriteseriesservice.domain.SeriesNotFoundException;
import pl.bartekszerlag.favoriteseriesservice.infrastructure.SeriesService;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class SeriesServiceTests {

    @Resource
    private SeriesService seriesService;

    @Test
    public void shouldAddSeries() {
        //given
        Series series = new Series.Builder()
                .withTitle("Test")
                .withPlatform("HBO")
                .build();

        //when
        seriesService.add(series);

        //then
        Series addedSeries = seriesService.findAll().get(0);

        assertEquals(1, seriesService.findAll().size());
        assertEquals(1, addedSeries.getId());
        assertEquals("Test", addedSeries.getTitle());
        assertEquals("HBO", addedSeries.getPlatform());
    }

    @Test
    public void shouldRemoveSeries() {
        //given
        Series series = new Series.Builder()
                .withTitle("Test")
                .withPlatform("HBO")
                .build();

        seriesService.add(series);

        //when
        int seriesId = seriesService.findAll().get(0).getId();
        seriesService.delete(seriesId);

        //then
        assertEquals(0, seriesService.findAll().size());
    }

    @Test
    public void shouldNotAddTwoSeriesWithTheSameTitle() {
        //when
        Series series = new Series.Builder()
                .withTitle("Test")
                .withPlatform("HBO")
                .build();

        //then
        assertThrows(SeriesAlreadyExistException.class, () -> {
            seriesService.add(series);
            seriesService.add(series);
        });
        assertEquals(1, seriesService.findAll().size());
    }

    @Test
    public void shouldNotRemoveSeriesByNotExistingId() {
        //when
        int seriesId = 1;

        //then
        assertThrows(SeriesNotFoundException.class, () -> {
            seriesService.delete(seriesId);
        });
    }

    //TODO: Test with series limit exception - how to change property value in test
}
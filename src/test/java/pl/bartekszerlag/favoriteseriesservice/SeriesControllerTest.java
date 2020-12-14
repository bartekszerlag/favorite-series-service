package pl.bartekszerlag.favoriteseriesservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.bartekszerlag.favoriteseriesservice.domain.Series;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class SeriesControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getAllSeries_shouldResponse200StatusCode() throws Exception {
        //when
        MvcResult result = mvc.perform(get("/series")).andReturn();
        //then
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void addSeries_shouldResponse201StatusCode() throws Exception {
        //given
        Series series = new Series(2, "Test", "Netflix");
        //when
        MvcResult result = mvc.perform(post("/series")
                .content(asJsonString(series))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        //then
        assertEquals(201, result.getResponse().getStatus());
    }

    @Test
    void deleteSeries_shouldResponse200StatusCode() throws Exception {
        //given
        Series series = new Series(2, "Test", "Netflix");
        //and
        mvc.perform(post("/series")
                .content(asJsonString(series))
                .contentType(MediaType.APPLICATION_JSON));
        //when
        MvcResult result = mvc.perform(delete("/series/2")).andReturn();
        //then
        assertEquals(200, result.getResponse().getStatus());
    }

    private String asJsonString(Series series) {
        try {
            return new ObjectMapper().writeValueAsString(series);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
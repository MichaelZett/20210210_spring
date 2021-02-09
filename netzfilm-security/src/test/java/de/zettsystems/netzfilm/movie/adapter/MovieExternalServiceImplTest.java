package de.zettsystems.netzfilm.movie.adapter;

import de.zettsystems.netzfilm.movie.application.MovieExternalService;
import de.zettsystems.netzfilm.movie.values.MovieDbEntry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RestClientTest(MovieExternalService.class)
@AutoConfigureWebClient(registerRestTemplate = true)
class MovieExternalServiceImplTest {

    @Autowired
    private MovieExternalService movieExternalService;

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Test
    public void shouldReturnQuoteFromRemoteSystem() {
        String response =
                "[" +
                        "{\"title\":\"The movie\",\"releaseDate\":\"2020-03-20\"}" +
                        "]";

        this.mockRestServiceServer
                .expect(MockRestRequestMatchers.requestTo("/movie/"))
                .andRespond(MockRestResponseCreators.withSuccess(response, MediaType.APPLICATION_JSON));

        List<MovieDbEntry> movies = movieExternalService.collectNewMovies();

        assertThat(movies.get(0)).isEqualTo(new MovieDbEntry("The movie", LocalDate.of(2020, 3, 20)));
    }
}
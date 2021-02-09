package de.zettsystems.netzfilm.movie.adapter;

import de.zettsystems.netzfilm.movie.application.MovieExternalService;
import de.zettsystems.netzfilm.movie.values.MovieDbEntry;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MovieExternalServiceImpl implements MovieExternalService {
    private static final ParameterizedTypeReference<List<MovieDbEntry>> TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };
    private final RestTemplate movieDbRestTemplate;

    public MovieExternalServiceImpl(RestTemplate movieDbRestTemplate) {
        this.movieDbRestTemplate = movieDbRestTemplate;
    }

    @Override
    public List<MovieDbEntry> collectNewMovies() {
        // Wegen type erasure können wir nicht direkt eine Liste benutzen, daher diese Spring-Spezialklasse, um den Typ zu retten. Es ginge auch array oder explizites wrapper-Objekt
        return movieDbRestTemplate.exchange("/movie/", HttpMethod.GET, null, TYPE_REFERENCE).getBody();
    }
}

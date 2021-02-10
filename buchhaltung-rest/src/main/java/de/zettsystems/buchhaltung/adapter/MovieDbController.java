package de.zettsystems.buchhaltung.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/movie")
class MovieDbController {
    private Map<String, MovieDbEntry> MOVIES = Map.of("The Smurfs 2", new MovieDbEntry("The Smurfs 2", LocalDate.of(2013, 7, 31)));

    @GetMapping("/{title}")
    public ResponseEntity<MovieDbEntry> findByTitle(@PathVariable String title) {
        final MovieDbEntry movieDbEntry = MOVIES.get(title);

        return ResponseEntity.of(Optional.ofNullable(movieDbEntry));
    }

    @GetMapping("/")
    public Collection<MovieDbEntry> findMovies() {
        return MOVIES.values();
    }

}
package de.zettsystems.buchhaltung.adapter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/movie")
class MovieDbController {
    private Map<String, MovieDbEntry> MOVIES = Map.of("The Smurfs 2", new MovieDbEntry("The Smurfs 2", LocalDate.of(2013, 7, 31)));

    @GetMapping("/{title}")
    public MovieDbEntry findByTitle(@PathVariable String title) {
        return MOVIES.get(title);
    }

    @GetMapping("/")
    public Collection<MovieDbEntry> findMovies() {
        return MOVIES.values();
    }

}
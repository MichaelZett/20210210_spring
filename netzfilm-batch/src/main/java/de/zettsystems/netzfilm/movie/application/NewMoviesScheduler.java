package de.zettsystems.netzfilm.movie.application;

import de.zettsystems.netzfilm.movie.domain.Movie;
import de.zettsystems.netzfilm.movie.domain.MovieRepository;
import de.zettsystems.netzfilm.movie.values.MovieDbEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class NewMoviesScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(NewMoviesScheduler.class);
    private final MovieExternalService movieExternalService;
    private final MovieRepository movieRepository;

    public NewMoviesScheduler(MovieExternalService movieExternalService, MovieRepository movieRepository) {
        this.movieExternalService = movieExternalService;
        this.movieRepository = movieRepository;
    }

    @Transactional
    @Scheduled(cron = "0 0 10 * * ?")
    public void checkForNewMovies() {
        final List<MovieDbEntry> movieDbEntries = movieExternalService.collectNewMovies();
        movieDbEntries
                .stream()
                .filter(m -> movieRepository.findByTitle(m.getTitle()).isEmpty())
                .forEach(m -> {
                            movieRepository.save(new Movie(m.getTitle(), m.getReleaseDate()));
                            LOG.info("Added new movie {}", m);
                        }
                );
    }
}

package de.zettsystems.netzfilm.movie.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class LoadMovieData {
    private static final Logger LOG = LoggerFactory.getLogger(LoadMovieData.class);
    private final MovieRepository movieRepository;

    public LoadMovieData(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void createMovies() {
        movieRepository.save(new Movie("A new hope", LocalDate.of(1977, 5, 25)));
        movieRepository.save(new Movie("The Empire strikes back", LocalDate.of(1980, 5, 17)));
        final Movie returnOfTheJedi = new Movie("Return of the Jedi", LocalDate.of(1983, 5, 25));
        movieRepository.save(returnOfTheJedi);
        final Optional<Movie> byUuid = movieRepository.findByUuid(returnOfTheJedi.getUuid());
        LOG.info("Found one by uuid: {}", byUuid);
        LOG.info("Found one by title: {}", movieRepository.findByTitle("A new hope"));
        LOG.info("Found all: {}", movieRepository.findAll());
    }


}

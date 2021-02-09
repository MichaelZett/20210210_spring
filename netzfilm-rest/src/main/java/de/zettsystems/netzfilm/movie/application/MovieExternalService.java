package de.zettsystems.netzfilm.movie.application;

import de.zettsystems.netzfilm.movie.values.MovieDbEntry;

import java.util.List;

public interface MovieExternalService {
    List<MovieDbEntry> collectNewMovies();
}

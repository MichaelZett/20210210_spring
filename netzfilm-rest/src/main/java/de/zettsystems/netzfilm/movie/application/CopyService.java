package de.zettsystems.netzfilm.movie.application;

import de.zettsystems.netzfilm.movie.domain.Copy;
import de.zettsystems.netzfilm.movie.domain.Movie;
import de.zettsystems.netzfilm.movie.values.RentableCopy;

import java.util.List;

public interface CopyService {
    List<Movie> findAllMoviesWithoutFreeCopies();

    List<RentableCopy> findAllRentableCopies();

    String getTitleAndFormat(long id);

    Copy createCopy(long movieId, String type);
}

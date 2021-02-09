package de.zettsystems.netzfilm.movie.application;

import de.zettsystems.netzfilm.movie.domain.*;
import de.zettsystems.netzfilm.movie.values.RentableCopy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CopyServiceImpl implements CopyService {
    private final CopyRepository copyRepository;
    private final MovieRepository movieRepository;

    public CopyServiceImpl(CopyRepository copyRepository, MovieRepository movieRepository) {
        this.copyRepository = copyRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> findAllMoviesWithoutFreeCopies() {
        return movieRepository.findAll().stream()
                .filter(movie -> copyRepository.countAllByMovieAndLentFalse(movie) == 0L)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<RentableCopy> findAllRentableCopies() {
        final List<Copy> allByLentFalse = copyRepository.findAllByLentFalse();
        return allByLentFalse.stream().map(c -> new RentableCopy(c.getId(), getTitleAndFormat(c))).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public String getTitleAndFormat(long id) {
        return getTitleAndFormat(copyRepository.findById(id).orElseThrow());
    }

    @Override
    @Transactional
    public Copy createCopy(long movieId, String type) {
        final Movie movie = movieRepository.findById(movieId).orElseThrow();
        return copyRepository.save(new Copy(CopyType.valueOf(type), movie));
    }

    private String getTitleAndFormat(Copy c) {
        return c.getMovie().getTitle() + " - " + c.getType().toString();
    }

}

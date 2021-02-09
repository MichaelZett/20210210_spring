package de.zettsystems.netzfilm.movie.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByUuid(String uuid);

    Optional<Movie> findByTitle(String title);
}

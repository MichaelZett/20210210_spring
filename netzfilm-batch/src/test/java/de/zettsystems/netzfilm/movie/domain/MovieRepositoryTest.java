package de.zettsystems.netzfilm.movie.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MovieRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MovieRepository movieRepository;
    private Movie movie;

    @BeforeEach
    private void setup() {
        // given
        movie = new Movie("Movie", LocalDate.now());
        entityManager.persist(movie);
        entityManager.flush();
    }

    @Test
    void findByUuid() {

        // when
        Movie found = movieRepository.findByUuid(movie.getUuid()).orElseThrow();
        // then
        assertThat(found.getTitle()).isEqualTo(movie.getTitle());
        assertThat(found.getReleaseDate()).isEqualTo(movie.getReleaseDate());
    }

    @Test
    void findByTitle() {

        // when
        Movie found = movieRepository.findByTitle(movie.getTitle()).orElseThrow();
        // then
        assertThat(found.getTitle()).isEqualTo(movie.getTitle());
        assertThat(found.getReleaseDate()).isEqualTo(movie.getReleaseDate());
    }

}
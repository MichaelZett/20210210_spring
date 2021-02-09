package de.zettsystems.netzfilm.movie.application;

import de.zettsystems.netzfilm.movie.domain.Copy;
import de.zettsystems.netzfilm.movie.domain.CopyType;
import de.zettsystems.netzfilm.movie.domain.Movie;
import de.zettsystems.netzfilm.movie.domain.MovieRepository;
import de.zettsystems.netzfilm.movie.values.CopyDelivery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class CopyDeliveryToCopyMapper implements ItemProcessor<CopyDelivery, Copy> {
    private static final Logger LOG = LoggerFactory.getLogger(CopyDeliveryToCopyMapper.class);
    private final MovieRepository movieRepository;

    public CopyDeliveryToCopyMapper(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Copy process(final CopyDelivery deliveredCopy) throws Exception {
        final Movie movie = movieRepository.findByTitle(deliveredCopy.getTitle()).orElseThrow();

        Copy copy = new Copy(CopyType.valueOf(deliveredCopy.getType()), movie);
        LOG.info("Converting ( {} ) into ( {} )", deliveredCopy, copy);

        return copy;
    }
}

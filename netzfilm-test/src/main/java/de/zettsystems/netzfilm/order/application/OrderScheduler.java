package de.zettsystems.netzfilm.order.application;

import de.zettsystems.netzfilm.movie.application.CopyService;
import de.zettsystems.netzfilm.movie.domain.CopyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(OrderScheduler.class);
    private final CopyService copyService;
    private final OrderSender orderSender;

    public OrderScheduler(CopyService copyService, OrderSender orderSender) {
        this.copyService = copyService;
        this.orderSender = orderSender;
    }

    @Scheduled(cron = "0 0 18 * * ?")
    @Transactional(readOnly = true)
    public void checkForOrders() {
        copyService.findAllMoviesWithoutFreeCopies().forEach(movie -> {
                    orderSender.sendCopyOrder(movie.getTitle(), movie.getReleaseDate(), CopyType.DVD);
                    orderSender.sendCopyOrder(movie.getTitle(), movie.getReleaseDate(), CopyType.VHS);
                }
        );
    }

}

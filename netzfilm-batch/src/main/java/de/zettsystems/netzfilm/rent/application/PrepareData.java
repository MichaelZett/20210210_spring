package de.zettsystems.netzfilm.rent.application;

import de.zettsystems.netzfilm.customer.domain.LoadCustomerData;
import de.zettsystems.netzfilm.movie.application.NewMoviesScheduler;
import de.zettsystems.netzfilm.movie.domain.CopyRepository;
import de.zettsystems.netzfilm.movie.domain.LoadCopyData;
import de.zettsystems.netzfilm.order.application.OrderScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
public class PrepareData {
    private static final Logger LOG = LoggerFactory.getLogger(PrepareData.class);
    private final LoadCopyData loadCopyData;
    private final LoadCustomerData loadCustomerData;
    private final RentService rentService;
    private final CopyRepository copyRepository;
    private final OrderScheduler orderScheduler;
    private final NewMoviesScheduler newMoviesScheduler;

    public PrepareData(LoadCopyData loadCopyData, LoadCustomerData loadCustomerData, RentService rentService, CopyRepository copyRepository, OrderScheduler orderScheduler, NewMoviesScheduler newMoviesScheduler) {
        this.loadCopyData = loadCopyData;
        this.loadCustomerData = loadCustomerData;
        this.rentService = rentService;
        this.copyRepository = copyRepository;
        this.orderScheduler = orderScheduler;
        this.newMoviesScheduler = newMoviesScheduler;
    }

    @PostConstruct
    @Transactional(readOnly = true)
    public void createCopies() {
        LOG.info("Preparing data...");
        loadCustomerData.createCustomer();
        loadCopyData.createCopies();
//        copyRepository.findAllByLentFalse().stream().forEach(
//                c -> rentService.rentAMovie(1L, c.getId(), LocalDate.now(), 5L)
//        );
//        LOG.info("...finished.");
//        LOG.info("Checking for orders...");
//        orderScheduler.checkForOrders();
//        LOG.info("...finished.");
//        // Create copies again
//        loadCopyData.createMoreCopies();
        LOG.info("Checking for new Movies...");
        newMoviesScheduler.checkForNewMovies();
        LOG.info("...finished.");
    }

}

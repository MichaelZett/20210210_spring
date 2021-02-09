package de.zettsystems.netzfilm.customer.application;

import de.zettsystems.netzfilm.customer.domain.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BirthdayScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(BirthdayScheduler.class);
    private final CustomerRepository customerRepository;

    public BirthdayScheduler(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Scheduled(cron = "0 0 8 * * ?")
    public void checkForBirthday() {
        customerRepository.findAll().stream().filter(c -> LocalDate.now().equals(c.getBirthdate())).forEach(
                c -> LOG.info("Wir senden einen Geburtstagsbrief an {}", c));
    }
}

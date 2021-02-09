package de.zettsystems.netzfilm.customer.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class LoadCustomerData {
    private static final Logger LOG = LoggerFactory.getLogger(LoadCustomerData.class);
    private final CustomerRepository customerRepository;

    public LoadCustomerData(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void createCustomer() {
        customerRepository.save(new Customer("Petra", "Meyer", LocalDate.of(1963, 7, 11)));
        customerRepository.save(new Customer("Frank", "Schuhmacher", LocalDate.of(1976, 4, 3)));
        final Customer customer = new Customer("Wiebke", "Müller", LocalDate.now());
        customerRepository.save(customer);
        final Optional<Customer> byUuid = customerRepository.findByUuid(customer.getUuid());
        LOG.info("Found one by uuid: {}", byUuid);
        LOG.info("Found one by lastname: {}", customerRepository.findByLastName("Meyer"));
        LOG.info("Found all: {}", customerRepository.findAll());
    }


}

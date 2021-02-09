package de.zettsystems.netzfilm.rent.application;

import de.zettsystems.netzfilm.bookkeeping.application.BookkeepingSender;
import de.zettsystems.netzfilm.customer.domain.Customer;
import de.zettsystems.netzfilm.customer.domain.CustomerRepository;
import de.zettsystems.netzfilm.movie.domain.Copy;
import de.zettsystems.netzfilm.movie.domain.CopyRepository;
import de.zettsystems.netzfilm.movie.domain.CopyType;
import de.zettsystems.netzfilm.rent.domain.Rent;
import de.zettsystems.netzfilm.rent.domain.RentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class RentServiceImpl implements RentService {
    private static final BigDecimal TWO_EURO = new BigDecimal("2.0");
    private final CustomerRepository customerRepository;
    private final CopyRepository copyRepository;
    private final RentRepository rentRepository;
    private final BookkeepingSender bookkeepingSender;

    public RentServiceImpl(CustomerRepository customerRepository, CopyRepository copyRepository, RentRepository rentRepository, BookkeepingSender bookkeepingSender) {
        this.customerRepository = customerRepository;
        this.copyRepository = copyRepository;
        this.rentRepository = rentRepository;
        this.bookkeepingSender = bookkeepingSender;
    }

    @Override
    @Transactional
    public void rentAMovie(long customerId, long copyId, LocalDate start, long numberOfDays) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        Copy copy = copyRepository.findById(copyId).orElseThrow(() -> new RuntimeException("Copy not found"));

        BigDecimal amount = calculateAmount(copy.getType(), numberOfDays);

        Rent newRent = new Rent(copy, customer, amount, start, start.plusDays(numberOfDays));
        rentRepository.save(newRent);
        // die Transaktion ist noch nicht geschlossen, Änderung an JPA-ManagedBeans werden
        // ohne weiteres "save" übernommen und persistiert
        copy.lend();
        bookkeepingSender.sendRentAmount(newRent.getUuid(), newRent.getAmount());
    }

    private static BigDecimal calculateAmount(CopyType type, long numberOfDays) {
        if (CopyType.VHS == type) {
            return BigDecimal.ONE.multiply(BigDecimal.valueOf(numberOfDays));
        } else {
            return TWO_EURO.multiply(BigDecimal.valueOf(numberOfDays));
        }
    }
}

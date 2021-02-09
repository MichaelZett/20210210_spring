package de.zettsystems.netzfilm.rent.domain;

import de.zettsystems.netzfilm.customer.domain.Customer;
import de.zettsystems.netzfilm.movie.domain.Copy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Rent {
    @Id
    @GeneratedValue
    private long id;
    @Version
    private long version;
    private String uuid;
    @OneToOne
    private Copy copy;
    @OneToOne
    private Customer customer;
    private BigDecimal amount;
    private LocalDate start;
    private LocalDate end;

    //needed for jpa
    protected Rent() {
        super();
    }

    public Rent(Copy copy, Customer customer, BigDecimal amount, LocalDate start, LocalDate end) {
        this();
        this.uuid = UUID.randomUUID().toString();
        this.copy = copy;
        this.customer = customer;
        this.amount = amount;
        this.start = start;
        this.end = end;
    }

    public long getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public String getUuid() {
        return uuid;
    }

    public Copy getCopy() {
        return copy;
    }

    public Customer getCustomer() {
        return customer;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }
}

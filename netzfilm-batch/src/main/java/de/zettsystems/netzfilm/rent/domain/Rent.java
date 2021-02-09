package de.zettsystems.netzfilm.rent.domain;

import de.zettsystems.netzfilm.customer.domain.Customer;
import de.zettsystems.netzfilm.movie.domain.Copy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotBlank
    private String uuid;
    @NotNull
    @OneToOne
    private Copy copy;
    @NotNull
    @OneToOne
    private Customer customer;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private LocalDate start;
    @NotNull
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

package de.zettsystems.netzfilm.customer.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUuid(String uuid);

    Optional<Customer> findByLastName(String lastName);

}

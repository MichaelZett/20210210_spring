package de.zettsystems.netzfilm.rent.application;

import java.time.LocalDate;

public interface RentService {
    void rentAMovie(long customerId, long copyId, LocalDate start, long numberOfDays);
}

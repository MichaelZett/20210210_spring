package de.zettsystems.netzfilm.bookkeeping.application;

import java.math.BigDecimal;

public interface BookkeepingSender {
    void sendRentAmount(String uuid, BigDecimal amount);
}

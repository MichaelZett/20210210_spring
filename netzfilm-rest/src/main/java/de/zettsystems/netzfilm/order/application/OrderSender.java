package de.zettsystems.netzfilm.order.application;

import de.zettsystems.netzfilm.movie.domain.CopyType;

import java.time.LocalDate;

public interface OrderSender {
    void sendCopyOrder(String title, LocalDate releaseDate, CopyType type);
}

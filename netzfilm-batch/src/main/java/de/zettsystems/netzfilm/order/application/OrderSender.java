package de.zettsystems.netzfilm.order.application;

import de.zettsystems.netzfilm.movie.domain.CopyType;
import de.zettsystems.netzfilm.movie.values.CopyToOrder;

import java.time.LocalDate;

public interface OrderSender {
    void sendCopyOrder(String title, LocalDate releaseDate, CopyType type);

    // needed here so java proxy suffices - alternative'd be to use CGLIB
    void onCopyOrder(CopyToOrder event);
}

package de.zettsystems.buchhaltung.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BookkeepingListener {
    private static final Logger LOG = LoggerFactory.getLogger(BookkeepingListener.class);

    @JmsListener(destination = "bookkeeping")
    public void processRent(Map<String, String> content) {
        // Annahme: das kommt in eine Buchhaltung
        // hier nur der Nachweis, dass die Nachricht ankommt
        LOG.info("Received message: {}", content);
    }

    @JmsListener(destination = "order")
    public void processOrder(Map<String, String> content) {
        // Annahme: Gehört in ein order System
        LOG.info("Received message: {}", content);
    }
}
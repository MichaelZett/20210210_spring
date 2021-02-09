package de.zettsystems.netzfilm.bookkeeping.adapter;

import de.zettsystems.netzfilm.bookkeeping.application.BookkeepingSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class BookkeepingSenderImpl implements BookkeepingSender {
    private static final Logger LOG = LoggerFactory.getLogger(BookkeepingSenderImpl.class);

    private final JmsTemplate jmsTemplate;

    public BookkeepingSenderImpl(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    @Async
    public void sendRentAmount(String uuid, BigDecimal amount) {
        Map<String, Object> map = Map.of("uuid", uuid, "amount", amount.toString());
        jmsTemplate.convertAndSend("bookkeeping", map);
        LOG.info("Sent message for uuid {}.", uuid);
    }

}
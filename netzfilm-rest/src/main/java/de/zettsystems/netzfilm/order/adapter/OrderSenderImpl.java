package de.zettsystems.netzfilm.order.adapter;

import de.zettsystems.netzfilm.movie.domain.CopyType;
import de.zettsystems.netzfilm.order.application.OrderSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
public class OrderSenderImpl implements OrderSender {
    private static final Logger LOG = LoggerFactory.getLogger(OrderSenderImpl.class);

    private final JmsTemplate jmsTemplate;

    public OrderSenderImpl(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void sendCopyOrder(String title, LocalDate releaseDate, CopyType type) {
        Map<String, Object> map = Map.of("title", title, "releaseDate", releaseDate.toString(), "type", type.toString());
        jmsTemplate.convertAndSend("order", map);
        LOG.info("Sent order message for title {}.", title);
    }
}

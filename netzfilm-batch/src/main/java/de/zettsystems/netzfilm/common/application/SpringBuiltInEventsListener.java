package de.zettsystems.netzfilm.common.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class SpringBuiltInEventsListener {
    private static final Logger LOG = LoggerFactory.getLogger(SpringBuiltInEventsListener.class);

    @EventListener
    public void onApplicationEvent(SpringApplicationEvent event) {
        LOG.info("Spring sent new application event: {}", event);
    }

    @EventListener
    public void onApplicationContextEvent(ApplicationContextEvent event) {
        LOG.info("Spring sent new context event: {}", event);

    }
}
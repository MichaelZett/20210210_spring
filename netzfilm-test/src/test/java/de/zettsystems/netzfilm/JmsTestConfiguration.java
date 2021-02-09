package de.zettsystems.netzfilm;

import de.zettsystems.netzfilm.movie.application.MovieExternalService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.core.JmsTemplate;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class JmsTestConfiguration {

    @Bean
    public JmsTemplate jmsTemplate() {
        return mock(JmsTemplate.class);
    }

    @Bean
    @Primary
    public MovieExternalService movieExternalService() {
        return mock(MovieExternalService.class);
    }
}

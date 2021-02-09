package de.zettsystems.netzfilm.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertyResolver;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestConfig {
    private static final int DEFAULT_CONNECTION_TIMEOUT_MS = 2_000;
    private static final int DEFAULT_READ_TIMEOUT_MS = 5_000;

    @Bean
    public RestTemplate movieDbRestTemplate(RestTemplateBuilder builder, PropertyResolver propertyResolver) {
        String url = propertyResolver.getProperty("moviedb.url", String.class, "http://localhost:9010");
        String apiPath = propertyResolver.getProperty("moviedb.api.path", String.class, "/api");

        return setTimeouts(builder, propertyResolver).rootUri(url + apiPath).build();
    }

    private static RestTemplateBuilder setTimeouts(RestTemplateBuilder builder, PropertyResolver propertyResolver) {
        int connectionTimeout = propertyResolver.getProperty("rest.connectionTimeout", Integer.class, DEFAULT_CONNECTION_TIMEOUT_MS);
        int readTimeout = propertyResolver.getProperty("rest.readTimeout", Integer.class, DEFAULT_READ_TIMEOUT_MS);
        return builder.setReadTimeout(Duration.ofMillis((long) readTimeout))
                .setConnectTimeout(Duration.ofMillis((long) connectionTimeout));
    }
}

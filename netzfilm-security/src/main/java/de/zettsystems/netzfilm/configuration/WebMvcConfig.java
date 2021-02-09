package de.zettsystems.netzfilm.configuration;

import de.zettsystems.netzfilm.common.adapter.LoggerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggerInterceptor());
    }

    /**
     * Damit spring security die Controller und iews findet braucht man diese config, die mit spring-boot eingentlich nicht mehr nötig ist
     */
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/movie").setViewName("movie");
        registry.addViewController("/rent").setViewName("rent");
    }
}
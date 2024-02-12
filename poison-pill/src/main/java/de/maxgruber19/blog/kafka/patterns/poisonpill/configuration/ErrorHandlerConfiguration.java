package de.maxgruber19.blog.kafka.patterns.poisonpill.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.CommonLoggingErrorHandler;


/**
 * ErrorHandler will log exceptions and make the listener continue reading.
 */
@Configuration
public class ErrorHandlerConfiguration {

    @Bean
    public CommonLoggingErrorHandler errorHandler() {
        return new CommonLoggingErrorHandler();
    }

}
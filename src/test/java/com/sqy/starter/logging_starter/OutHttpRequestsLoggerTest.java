package com.sqy.starter.logging_starter;

import java.util.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;

@TestPropertySource(locations = "classpath:/httpOut.properties")
public class OutHttpRequestsLoggerTest extends AbstractLoggerTest {
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testLevelAndFormat() {
        restTemplate.getForObject("https://httpbin.org/get", Map.class);
        boolean exists = memoryAppender.list.stream()
            .filter(event -> getMemoryAppenderLevel().equals(event.getLevel()))
            .map(ILoggingEvent::getFormattedMessage)
            .anyMatch(message -> message.matches("request was [0-9]+ ms\\. long\n200 GET https://httpbin\\.org/get"));
        Assertions.assertTrue(exists);
    }

    @Override
    public Level getMemoryAppenderLevel() {
        return Level.DEBUG;
    }
}

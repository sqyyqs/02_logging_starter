package com.sqy.starter.logging_starter;

import com.sqy.starter.logging_starter.configuration.MemoryAppender;
import com.sqy.starter.logging_starter.logger.HttpLogger;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

@SpringBootTest
public abstract class AbstractLoggerTest {
    public MemoryAppender memoryAppender = null;

    @BeforeEach
    public void setup() {
        Logger logger = (Logger) LoggerFactory.getLogger(HttpLogger.class);
        memoryAppender = new MemoryAppender();
        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(getMemoryAppenderLevel());
        logger.addAppender(memoryAppender);
        memoryAppender.start();
    }

    public abstract Level getMemoryAppenderLevel();
}

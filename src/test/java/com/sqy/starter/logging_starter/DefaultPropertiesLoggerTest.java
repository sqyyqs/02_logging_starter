package com.sqy.starter.logging_starter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import ch.qos.logback.classic.Level;

@AutoConfigureMockMvc(addFilters = false)
public class DefaultPropertiesLoggerTest extends AbstractLoggerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLevelAndFormat() throws Exception {
        mockMvc.perform(get("/test")).andExpect(status().isOk());
        Assertions.assertFalse(memoryAppender.list.isEmpty());
    }

    @Override
    public Level getMemoryAppenderLevel() {
        return Level.INFO;
    }
}

package com.sqy.starter.logging_starter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sqy.starter.logging_starter.configuration.LoggingAutoConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import ch.qos.logback.classic.Level;

@TestPropertySource(locations = "classpath:/disabled.properties")
@AutoConfigureMockMvc(addFilters = false)
public class DisabledLoggerTest extends AbstractLoggerTest {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testDisabledLogger() throws Exception {
        mockMvc.perform(get("/test")).andExpect(status().isOk());
        Assertions.assertTrue(memoryAppender.list.isEmpty());
    }

    @Test
    public void testNoConditionalBeansInApplicationContext() {
        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(LoggingAutoConfiguration.class));
    }

    @Override
    public Level getMemoryAppenderLevel() {
        return Level.TRACE;
    }
}

package com.sqy.starter.logging_starter.logger;

import java.util.*;

import com.sqy.starter.logging_starter.configuration.LoggingProperties;
import com.sqy.starter.logging_starter.domain.LoggingParams;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class LogMessageBuilder {
    private final LoggingProperties loggingProperties;

    public LogMessageBuilder(LoggingProperties loggingProperties) {
        this.loggingProperties = loggingProperties;
    }

    public String buildLogMessage(LoggingParams loggingParams) {
        return loggingProperties.getLogFormat()
            .replaceAll("%n", System.lineSeparator())
            .replaceAll("%M", loggingParams.method())
            .replaceAll("%u", loggingParams.url())
            .replaceAll("%h", mapToString(loggingParams.requestHeaders()))
            .replaceAll("%H", mapToString(loggingParams.requestHeaders()))
            .replaceAll("%r", loggingParams.status().name())
            .replaceAll("%R", String.valueOf(loggingParams.status().value()))
            .replaceAll("%t", loggingParams.requestTime());
    }

    private static String mapToString(Map<?, ?> map) {
        if (CollectionUtils.isEmpty(map)) {
            return "EMPTY_HEADERS";
        }
        return Arrays.toString(map.entrySet().toArray());
    }
}

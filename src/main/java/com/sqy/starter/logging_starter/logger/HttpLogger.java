package com.sqy.starter.logging_starter.logger;

import java.time.Duration;
import java.time.Instant;

import com.sqy.starter.logging_starter.configuration.LoggingAutoConfiguration;
import com.sqy.starter.logging_starter.configuration.LoggingProperties;
import com.sqy.starter.logging_starter.domain.LoggingParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@ConditionalOnBean(LoggingAutoConfiguration.class)
public class HttpLogger {
    // Логирование должно включать в себя метод запроса,
    // URL,
    // заголовки запроса и ответа,
    // код ответа,
    // время обработки запроса
    // %M - method, %u - url, %h - req headers, %H, resp - headers, %r - код ответа(строка), %R – код ответа(цифра), %t - время обработки запроса.

    //internal logger
    private static final Logger _logger = LoggerFactory.getLogger(HttpLogger.class);

    private final LoggerInvoker logger;
    private final LoggingProperties loggingProperties;

    public HttpLogger(LoggingProperties loggingProperties) {
        this.loggingProperties = loggingProperties;
        this.logger = LoggerInvoker.from(loggingProperties.getLogLevel(), _logger);
    }

    public void log(HttpServletRequest request, HttpServletResponse response) {
        LoggingParams params = LoggingParams.builder().httpServletRequest(request)
            .httpServletResponse(response)
            .requestTime(requestDuration(request))
            .build();
        String logMessage = buildLogMessage(params);
        logger.log(logMessage);
    }

    public void log(HttpRequest request, ClientHttpResponse response, Duration duration) {
        LoggingParams params = LoggingParams.builder()
            .httpRequest(request)
            .httpResponse(response)
            .requestTime(duration)
            .build();
        String logMessage = buildLogMessage(params);
        logger.log(logMessage);
    }

    private String buildLogMessage(LoggingParams loggingParams) {
        return loggingProperties.getLogFormat()
            .replaceAll("%n", System.lineSeparator())
            .replaceAll("%M", loggingParams.method())
            .replaceAll("%u", loggingParams.url())
            .replaceAll("%h", loggingParams.requestHeaders().toString())
            .replaceAll("%H", loggingParams.responseHeaders().toString())
            .replaceAll("%r", loggingParams.status().name())
            .replaceAll("%R", String.valueOf(loggingParams.status().value()))
            .replaceAll("%t", loggingParams.requestTime());
    }

    private Duration requestDuration(HttpServletRequest request) {
        Long attribute = (Long) request.getAttribute(loggingProperties.getTimeRequestParameter());
        return Duration.between(Instant.ofEpochMilli(attribute), Instant.now());
    }
}

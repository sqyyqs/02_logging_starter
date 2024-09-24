package com.sqy.starter.logging_starter.logger;

import java.io.*;
import java.time.Duration;
import java.time.Instant;

import com.sqy.starter.logging_starter.configuration.LoggingAutoConfiguration;
import com.sqy.starter.logging_starter.configuration.LoggingProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@ConditionalOnBean(LoggingAutoConfiguration.class)
public class HttpLoggingInterceptor implements HandlerInterceptor, ClientHttpRequestInterceptor {
    private final HttpLogger httpLogger;
    private final LoggingProperties loggingProperties;

    public HttpLoggingInterceptor(HttpLogger httpLogger, LoggingProperties loggingProperties) {
        this.httpLogger = httpLogger;
        this.loggingProperties = loggingProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(loggingProperties.getTimeRequestParameter(), System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) {
        httpLogger.log(request, response);
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        Instant start = Instant.now();
        ClientHttpResponse response = execution.execute(request, body);
        Instant end = Instant.now();
        httpLogger.log(request, response, Duration.between(start, end));
        return response;
    }
}

package com.sqy.starter.logging_starter.domain;

import java.io.*;
import java.net.*;
import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public record LoggingParams(
    String method,
    String url,
    Map<String, String> requestHeaders,
    Map<String, String> responseHeaders,
    HttpStatus status,
    String requestTime
) {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String method;
        private String url;
        private Map<String, String> requestHeaders;
        private Map<String, String> responseHeaders;
        private HttpStatus status;
        private String requestTime;

        private Builder() {
        }

        public Builder httpServletRequest(HttpServletRequest request) {
            this.method = request.getMethod();
            this.url = request.getRequestURL().toString();
            this.requestHeaders = getHeaders(request);
            return this;
        }

        public Builder httpRequest(HttpRequest request) {
            this.method = request.getMethod().name();
            this.requestHeaders = request.getHeaders().toSingleValueMap();
            try {
                this.url = request.getURI().toURL().toString();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Builder httpResponse(ClientHttpResponse response) {
            this.responseHeaders = getHeaders(response);
            try {
                this.status = HttpStatus.valueOf(response.getStatusCode().value());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Builder httpServletResponse(HttpServletResponse response) {
            this.responseHeaders = getHeaders(response);
            this.status = HttpStatus.valueOf(response.getStatus());
            return this;
        }

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder requestHeaders(Map<String, String> requestHeaders) {
            this.requestHeaders = requestHeaders;
            return this;
        }

        public Builder responseHeaders(Map<String, String> responseHeaders) {
            this.responseHeaders = responseHeaders;
            return this;
        }

        public Builder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public Builder requestTime(Duration requestTime) {
            this.requestTime = String.valueOf(requestTime.toMillis());
            return this;
        }

        public LoggingParams build() {
            return new LoggingParams(method, url, requestHeaders, responseHeaders, status, requestTime);
        }

        private static Map<String, String> getHeaders(HttpServletRequest req) {
            HashMap<String, String> headersResult = new HashMap<>();
            req.getHeaderNames().asIterator().forEachRemaining(headerName -> headersResult.put(
                headerName,
                req.getHeader(headerName)
            ));
            return headersResult;
        }

        private static Map<String, String> getHeaders(HttpServletResponse response) {
            return response.getHeaderNames().stream().collect(Collectors.toMap(
                Function.identity(),
                response::getHeader,
                (value1, value2) -> value2
            ));
        }

        private static Map<String, String> getHeaders(ClientHttpResponse response) {
            return response.getHeaders()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> entry.getValue().stream().findAny().orElseThrow(),
                    (value1, value2) -> value2
                ));
        }
    }
}

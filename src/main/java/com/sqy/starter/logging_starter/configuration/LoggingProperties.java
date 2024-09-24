package com.sqy.starter.logging_starter.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "http-logging.configuration")
public class LoggingProperties {
    private String timeRequestParameter = "startTime";
    private String logLevel = "info";
    private String logFormat = "%n%M %u %nHeaders:%n%h%n%H%n%R %r %tms.";

    public String getTimeRequestParameter() {
        return timeRequestParameter;
    }

    public void setTimeRequestParameter(String timeRequestParameter) {
        this.timeRequestParameter = timeRequestParameter;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getLogFormat() {
        return logFormat;
    }

    public void setLogFormat(String logFormat) {
        this.logFormat = logFormat;
    }
}

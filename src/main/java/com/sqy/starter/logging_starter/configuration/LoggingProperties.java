package com.sqy.starter.logging_starter.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "http-logging.configuration")
public class LoggingProperties {
    //todo add deafult
    private String timeRequestParameter = "startTime";
    private String logLevel = "info";
    private String logFormat = "";//todo

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

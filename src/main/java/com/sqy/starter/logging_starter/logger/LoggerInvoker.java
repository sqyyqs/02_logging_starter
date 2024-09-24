package com.sqy.starter.logging_starter.logger;

import java.util.function.Consumer;

import org.slf4j.Logger;

public class LoggerInvoker {
    private final Consumer<String> logFunction;

    private LoggerInvoker(Consumer<String> logFunction) {
        this.logFunction = logFunction;
    }

    public static LoggerInvoker from(String logLevel, Logger logger) {
        Consumer<String> logFunction = switch (logLevel) {
            case "trace" -> logger::trace;
            case "debug" -> logger::debug;
            case "info" -> logger::info;
            case "warn" -> logger::warn;
            case "error" -> logger::error;
            default -> throw new IllegalStateException("Unexpected value: " + logLevel);
        };
        return new LoggerInvoker(logFunction);
    }

    public void log(String logMessage) {
        logFunction.accept(logMessage);
    }
}

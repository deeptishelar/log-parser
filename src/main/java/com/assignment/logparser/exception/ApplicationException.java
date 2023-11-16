package com.assignment.logparser.exception;

import lombok.Getter;
import org.slf4j.Logger;

/**
 * Custom exception handler Not doping much at this time, but can be of help as the app grows
 */
@Getter
public class ApplicationException extends RuntimeException {

    private final String message;
    private final Logger LOG;
    private final Throwable exception;


    public ApplicationException(final String message, Logger LOG, final Throwable exception) {
        super(message, exception);
        this.LOG = LOG;
        this.exception = exception;
        this.message = message;
    }

}

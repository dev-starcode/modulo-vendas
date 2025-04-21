package com.starcode.erp_vendas_caixa.domain.exceptions;

public class NoStacktraceException extends RuntimeException {
    public NoStacktraceException(final String message) {
        super(message, null);
    }

    public NoStacktraceException(final String message, final Throwable cause) {
        super(message, cause, true, false);
    }
}

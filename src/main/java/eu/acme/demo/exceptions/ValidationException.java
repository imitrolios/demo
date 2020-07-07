package eu.acme.demo.exceptions;

import javax.servlet.http.HttpServletResponse;

public class ValidationException extends ApplicationException {

    private static final String MESSAGE = "Validation error occurred";
    private static final int ERROR_STATUS = HttpServletResponse.SC_BAD_REQUEST;
    private static final long serialVersionUID = -5800282692667353570L;


    public ValidationException() {
        super(MESSAGE, ERROR_STATUS);
    }

    public ValidationException(final String message) {
        super(message, ERROR_STATUS);
    }

    public ValidationException(final Throwable cause) {
        super(MESSAGE, cause, ERROR_STATUS);
    }

    public ValidationException(final String message, final Throwable cause) {
        super(message, cause, ERROR_STATUS);
    }

}
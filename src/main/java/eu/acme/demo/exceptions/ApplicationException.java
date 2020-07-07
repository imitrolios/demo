package eu.acme.demo.exceptions;

public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = -2480619476796668125L;
    private final int errorStatus;

    ApplicationException(final String message, final int errorStatus) {
        super(message);
        this.errorStatus = errorStatus;
    }

    ApplicationException(final String message, final Throwable cause, final int errorStatus) {
        super(message, cause);
        this.errorStatus = errorStatus;
    }

    public int getErrorStatus() {
        return errorStatus;
    }
}

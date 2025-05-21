package exceptions;

/**
 * Exception thrown when an invalid severity level is specified.
 */
public class InvalidSeverityException extends ReportException {

    /**
     * Constructor for InvalidSeverityException.
     *
     * @param message The error message
     */
    public InvalidSeverityException(String message) {
        super(message);
    }
}
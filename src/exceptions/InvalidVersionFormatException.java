package exceptions;

/**
 * Exception thrown when an invalid software version format is specified.
 */
public class InvalidVersionFormatException extends ReportException {

    /**
     * Constructor for InvalidVersionFormatException.
     *
     * @param message The error message
     */
    public InvalidVersionFormatException(String message) {
        super(message);
    }
}
package exceptions;

/**
 * Base class for custom exceptions in the report management system.
 */
public class ReportException extends Exception {

    /**
     * Constructor for ReportException.
     *
     * @param message The error message
     */
    public ReportException(String message) {
        super(message);
    }
}
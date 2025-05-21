package exceptions;

/**
 * Exception thrown when an invalid serial number is specified.
 */
public class InvalidSerialNumberException extends ReportException {

  /**
   * Constructor for InvalidSerialNumberException.
   *
   * @param message The error message
   */
  public InvalidSerialNumberException(String message) {
    super(message);
  }
}
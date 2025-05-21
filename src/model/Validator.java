package model;

import exceptions.InvalidSerialNumberException;
import exceptions.InvalidSeverityException;
import exceptions.InvalidVersionFormatException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for validating input data.
 * Contains static methods to validate severity types, serial numbers, and software versions.
 */
public class Validator {

    /**
     * Validates the severity type string.
     *
     * @param severityStr The severity type string to validate
     * @return The corresponding Severity enum value
     * @throws InvalidSeverityException If the severity type is invalid
     */
    public static Severity validateSeverity(String severityStr) throws InvalidSeverityException {
        try {
            return Severity.valueOf(severityStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidSeverityException("Tipo de severidad inválido. Debe ser ALTO, MEDIO o BAJO.");
        }
    }

    /**
     * Validates that a serial number is a positive integer.
     *
     * @param serialNumberStr The serial number string to validate
     * @return The parsed serial number as an integer
     * @throws InvalidSerialNumberException If the serial number is not a positive integer
     */
    public static int validateSerialNumber(String serialNumberStr) throws InvalidSerialNumberException {
        try {
            int serialNumber = Integer.parseInt(serialNumberStr);
            if (serialNumber <= 0) {
                throw new InvalidSerialNumberException("El número serial debe ser un entero positivo.");
            }
            return serialNumber;
        } catch (NumberFormatException e) {
            throw new InvalidSerialNumberException("El número serial debe ser un número entero.");
        }
    }

    /**
     * Validates that a software version follows the A.B.C format.
     *
     * @param version The version string to validate
     * @throws InvalidVersionFormatException If the version format is invalid
     */
    public static void validateVersionFormat(String version) throws InvalidVersionFormatException {
        if (version == null || !version.matches("\\d+\\.\\d+\\.\\d+")) {
            throw new InvalidVersionFormatException("El formato de la versión debe ser A.B.C, donde A, B y C son números.");
        }
    }

    /**
     * Validates a date string in the format YYYY/MM/DD.
     *
     * @param dateStr The date string to validate
     * @return The parsed LocalDate object
     * @throws DateTimeParseException If the date format is invalid
     */
    public static LocalDate validateDate(String dateStr) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return LocalDate.parse(dateStr, formatter);
    }
}
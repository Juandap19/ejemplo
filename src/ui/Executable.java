package ui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import model.Controller;
import model.Hardware;
import exceptions.InvalidSerialNumberException;
import exceptions.InvalidSeverityException;
import exceptions.InvalidVersionFormatException;
import model.Report;
import model.Severity;
import model.Software;
import model.Validator;

/**
 * Main executable class for the Electronic Equipment Report Management System.
 * Provides a console-based user interface.
 */
public class Executable {

    private static Scanner scanner = new Scanner(System.in);
    private static Controller controller = new Controller();
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public static void main(String[] args) {
        boolean exit = false;

        System.out.println("Sistema de Gestión de Reportes de Equipos Electrónicos");

        while (!exit) {
            showMainMenu();
            int option = readOption();

            switch (option) {
                case 1:
                    registerReport();
                    break;
                case 2:
                    queryReportsByEquipmentId();
                    break;
                case 3:
                    queryReportsBySeverity();
                    break;
                case 4:
                    queryReportsByDate();
                    break;
                case 5:
                    generateReport();
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }

        System.out.println("Gracias por utilizar el sistema. ¡Hasta pronto!");
        scanner.close();
    }

    /**
     * Displays the main menu options.
     */
    private static void showMainMenu() {
        System.out.println("\n----- MENÚ PRINCIPAL -----");
        System.out.println("1. Registrar nuevo reporte");
        System.out.println("2. Consultar reportes por ID de equipo");
        System.out.println("3. Consultar reportes por nivel de severidad");
        System.out.println("4. Consultar reportes desde una fecha determinada");
        System.out.println("5. Generar informe de reportes");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
    }

    /**
     * Reads an integer option from the console.
     *
     * @return The selected option as an integer
     */
    private static int readOption() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Handles the report registration process.
     */
    private static void registerReport() {
        System.out.println("\n----- REGISTRO DE REPORTE -----");
        System.out.println("Tipo de reporte:");
        System.out.println("1. Hardware");
        System.out.println("2. Software");
        System.out.print("Seleccione una opción: ");
        int reportType = readOption();

        if (reportType != 1 && reportType != 2) {
            System.out.println("Tipo de reporte inválido.");
            return;
        }

        try {
            // Common information for all reports
            System.out.print("ID del equipo: ");
            String idGadget = scanner.nextLine();

            System.out.print("Descripción: ");
            String description = scanner.nextLine();

            System.out.print("Nivel de severidad (ALTO, MEDIO, BAJO): ");
            String severityStr = scanner.nextLine();
            Severity severity = Validator.validateSeverity(severityStr);

            System.out.print("Fecha de reporte (YYYY/MM/DD): ");
            String dateStr = scanner.nextLine();
            LocalDate date = Validator.validateDate(dateStr);

            // Specific information based on report type
            if (reportType == 1) {
                // Hardware report
                System.out.print("Tipo de componente: ");
                String componentType = scanner.nextLine();

                System.out.print("Número serial del componente: ");
                String serialNumberStr = scanner.nextLine();
                int serialNumber = Validator.validateSerialNumber(serialNumberStr);

                System.out.print("¿Requiere cambio de componente? (S/N): ");
                String replaceStr = scanner.nextLine();
                boolean hasToReplace = replaceStr.equalsIgnoreCase("S");

                Hardware hardwareReport = new Hardware(idGadget, description, severity, date,
                        componentType, serialNumber, hasToReplace);
                controller.addReport(hardwareReport);

            } else {
                // Software report
                System.out.print("Sistema operativo: ");
                String systemOperation = scanner.nextLine();

                System.out.print("Nombre del software: ");
                String softwareName = scanner.nextLine();

                System.out.print("Versión del software (A.B.C): ");
                String version = scanner.nextLine();
                Validator.validateVersionFormat(version);

                Software softwareReport = new Software(idGadget, description, severity, date,
                        systemOperation, softwareName, version);
                controller.addReport(softwareReport);
            }

            System.out.println("Reporte registrado exitosamente.");

        } catch (InvalidSeverityException | InvalidSerialNumberException |
                 InvalidVersionFormatException | DateTimeParseException e) {
            System.out.println("Error al registrar el reporte: " + e.getMessage());
        }
    }

    /**
     * Handles the query of reports by equipment ID.
     */
    private static void queryReportsByEquipmentId() {
        System.out.println("\n----- CONSULTA DE REPORTES POR ID DE EQUIPO -----");

        List<Report> allReports = controller.getListReports();
        if (allReports.isEmpty()) {
            System.out.println("No hay reportes registrados en el sistema.");
            return;
        }

        System.out.println("Reportes disponibles:");
        List<String> equipmentIds = controller.getEquipmentIds();

        for (String id : equipmentIds) {
            System.out.println("ID de equipo: " + id);
            for (Report report : allReports) {
                if (report.getId_gadget().equals(id)) {
                    System.out.println("  - Severidad: " + report.getSeverityType());
                }
            }
        }

        System.out.print("\nIngrese el ID del equipo a consultar: ");
        String idGadget = scanner.nextLine();

        List<Report> results = controller.searchReportsByid(idGadget);

        if (results.isEmpty()) {
            System.out.println("No se encontraron reportes para el ID de equipo indicado.");
        } else {
            System.out.println("\nReportes encontrados:");
            displayReportsList(results);
        }
    }

    /**
     * Handles the query of reports by severity level.
     */
    private static void queryReportsBySeverity() {
        System.out.println("\n----- CONSULTA DE REPORTES POR NIVEL DE SEVERIDAD -----");

        System.out.println("Niveles de severidad disponibles:");
        System.out.println("1. ALTO");
        System.out.println("2. MEDIO");
        System.out.println("3. BAJO");

        System.out.print("Seleccione un nivel de severidad: ");
        int severityOption = readOption();

        if (severityOption < 1 || severityOption > 3) {
            System.out.println("Opción inválida.");
            return;
        }

        List<Report> results = controller.searchBySeverityType(severityOption);

        if (results.isEmpty()) {
            System.out.println("No se encontraron reportes con el nivel de severidad indicado.");
        } else {
            System.out.println("\nReportes encontrados:");
            displayReportsList(results);
        }
    }

    /**
     * Handles the query of reports from a specific date.
     */
    private static void queryReportsByDate() {
        System.out.println("\n----- CONSULTA DE REPORTES DESDE UNA FECHA -----");

        LocalDate earliestDate = controller.getEarliestDate();
        LocalDate latestDate = controller.getLatestDate();

        if (earliestDate == null || latestDate == null) {
            System.out.println("No hay reportes registrados en el sistema.");
            return;
        }

        System.out.println("Rango de fechas disponibles: " +
                earliestDate.format(dateFormatter) + " a " +
                latestDate.format(dateFormatter));

        System.out.print("Ingrese la fecha desde la cual consultar (YYYY/MM/DD): ");
        String dateStr = scanner.nextLine();

        try {
            LocalDate date = Validator.validateDate(dateStr);
            List<Report> results = controller.searchSinceDeterminateDate(date);

            if (results.isEmpty()) {
                System.out.println("No se encontraron reportes desde la fecha indicada.");
            } else {
                System.out.println("\nReportes encontrados:");
                displayReportsList(results);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. Use el formato YYYY/MM/DD.");
        }
    }

    /**
     * Handles the generation of hardware or software reports.
     */
    private static void generateReport() {
        System.out.println("\n----- GENERACIÓN DE INFORMES -----");
        System.out.println("Tipo de informe:");
        System.out.println("1. Hardware");
        System.out.println("2. Software");
        System.out.print("Seleccione una opción: ");
        int reportType = readOption();

        try {
            String filename;

            if (reportType == 1) {
                filename = controller.generateHardwareReport();
                System.out.println("Informe de Hardware generado exitosamente: " + filename);
            } else if (reportType == 2) {
                filename = controller.generateSoftwareReport();
                System.out.println("Informe de Software generado exitosamente: " + filename);
            } else {
                System.out.println("Opción inválida.");
            }
        } catch (IOException e) {
            System.out.println("Error al generar el informe: " + e.getMessage());
        }
    }

    /**
     * Displays a list of reports in a formatted way.
     *
     * @param reports The list of reports to display
     */
    private static void displayReportsList(List<Report> reports) {
        int count = 1;

        for (Report report : reports) {
            System.out.println(count + ". " + report.getReportInfo());
            count++;
        }
    }
}
package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class that manages the operations related to reports.
 * This class implements the main business logic of the application.
 */
public class Controller implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String DATA_FILE = "data/databaseReports.dat";
    private static final String REPORTS_FOLDER = "reports/";

    // List to store reports
    private List<Report> listReports;

    /**
     * Constructor for the Controller class.
     * Initializes the list of reports and loads any existing data from the database file.
     */
    public Controller() {
        listReports = new ArrayList<>();
        loadData();
    }

    /**
     * Adds a new report to the list and saves the data.
     *
     * @param report The report to be added
     */
    public void addReport(Report report) {
        listReports.add(report);
        saveData();
    }

    /**
     * Searches for reports by their equipment ID.
     *
     * @param idGadget The ID of the equipment to search for
     * @return A list of reports with the specified equipment ID
     */
    public List<Report> searchReportsByid(String idGadget) {
        List<Report> results = new ArrayList<>();
        for (Report report : listReports) {
            if (report.getId_gadget().equals(idGadget)) {
                results.add(report);
            }
        }
        return results;
    }

    /**
     * Searches for reports by their severity type.
     *
     * @param severityType The severity type to search for
     * @return A list of reports with the specified severity type
     */
    public List<Report> searchBySeverityType(int severityType) {
        Severity severity;
        switch (severityType) {
            case 1:
                severity = Severity.ALTO;
                break;
            case 2:
                severity = Severity.MEDIO;
                break;
            case 3:
                severity = Severity.BAJO;
                break;
            default:
                return new ArrayList<>();
        }

        List<Report> results = new ArrayList<>();
        for (Report report : listReports) {
            if (report.getSeverityType() == severity) {
                results.add(report);
            }
        }
        return results;
    }

    /**
     * Searches for reports created since a specific date.
     *
     * @param date The date from which to search for reports
     * @return A list of reports created on or after the specified date
     */
    public List<Report> searchSinceDeterminateDate(LocalDate date) {
        List<Report> results = new ArrayList<>();
        for (Report report : listReports) {
            if (report.getDate_report().isEqual(date) || report.getDate_report().isAfter(date)) {
                results.add(report);
            }
        }
        return results;
    }

    /**
     * Gets a list of all unique equipment IDs in the reports.
     *
     * @return A list of equipment IDs
     */
    public List<String> getEquipmentIds() {
        List<String> ids = new ArrayList<>();
        for (Report report : listReports) {
            String id = report.getId_gadget();
            if (!ids.contains(id)) {
                ids.add(id);
            }
        }
        return ids;
    }

    /**
     * Gets the earliest date from all reports.
     *
     * @return The earliest date of any report, or null if no reports exist
     */
    public LocalDate getEarliestDate() {
        if (listReports.isEmpty()) {
            return null;
        }

        LocalDate earliest = listReports.get(0).getDate_report();
        for (Report report : listReports) {
            if (report.getDate_report().isBefore(earliest)) {
                earliest = report.getDate_report();
            }
        }
        return earliest;
    }

    /**
     * Gets the latest date from all reports.
     *
     * @return The latest date of any report, or null if no reports exist
     */
    public LocalDate getLatestDate() {
        if (listReports.isEmpty()) {
            return null;
        }

        LocalDate latest = listReports.get(0).getDate_report();
        for (Report report : listReports) {
            if (report.getDate_report().isAfter(latest)) {
                latest = report.getDate_report();
            }
        }
        return latest;
    }

    /**
     * Gets the list of all reports.
     *
     * @return The list of all reports
     */
    public List<Report> getListReports() {
        return listReports;
    }

    /**
     * Generates a report file with all hardware reports.
     *
     * @return The name of the generated file
     * @throws IOException If an error occurs while writing the file
     */
    public String generateHardwareReport() throws IOException {
        return generateReport("Hardware");
    }

    /**
     * Generates a report file with all software reports.
     *
     * @return The name of the generated file
     * @throws IOException If an error occurs while writing the file
     */
    public String generateSoftwareReport() throws IOException {
        return generateReport("Software");
    }

    /**
     * Helper method to generate a report file of a specific type.
     *
     * @param type The type of report to generate ("Hardware" or "Software")
     * @return The name of the generated file
     * @throws IOException If an error occurs while writing the file
     */
    private String generateReport(String type) throws IOException {
        // Create reports directory if it doesn't exist
        File reportsDir = new File(REPORTS_FOLDER);
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
        }

        // Generate filename with current date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timestamp = LocalDate.now().atStartOfDay().format(formatter);
        String filename = REPORTS_FOLDER + "Reporte_" + type + "_" + timestamp + ".txt";

        // Write report contents to file
        try (FileOutputStream fos = new FileOutputStream(filename);
             java.io.PrintWriter writer = new java.io.PrintWriter(fos)) {

            writer.println("Reporte de " + type + " generado el " + LocalDate.now());
            writer.println("----------------------------------------");

            for (Report report : listReports) {
                if ((type.equals("Hardware") && report instanceof Hardware) ||
                        (type.equals("Software") && report instanceof Software)) {
                    writer.println(report.getReportInfo());
                }
            }
        }

        return filename;
    }

    /**
     * Loads data from the serialized file into the list of reports.
     */
    @SuppressWarnings("unchecked")
    private void loadData() {
        File file = new File(DATA_FILE);

        // If the file doesn't exist, create the directory structure
        if (!file.exists()) {
            File dataDir = new File("data");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
            return;
        }

        try (FileInputStream fis = new FileInputStream(DATA_FILE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            listReports = (List<Report>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    /**
     * Saves the list of reports to the serialized file.
     */
    private void saveData() {
        try (FileOutputStream fos = new FileOutputStream(DATA_FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(listReports);

        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }
}
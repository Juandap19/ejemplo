package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Abstract class that represents a report of an electronic device.
 * This is the base class for different types of reports such as hardware and software.
 */
public abstract class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    // Attributes
    protected String id_gadget;
    protected String description;
    protected Severity severityType;
    protected LocalDate date_report;

    /**
     * Constructor for the Report class.
     *
     * @param id_gadget The ID of the electronic device
     * @param description The description of the report
     * @param severityType The severity level of the reported issue
     * @param date_report The date when the report was created
     */
    public Report(String id_gadget, String description, Severity severityType, LocalDate date_report) {
        this.id_gadget = id_gadget;
        this.description = description;
        this.severityType = severityType;
        this.date_report = date_report;
    }

    /**
     * Gets the ID of the electronic device.
     *
     * @return The ID of the electronic device
     */
    public String getId_gadget() {
        return id_gadget;
    }

    /**
     * Sets the ID of the electronic device.
     *
     * @param id_gadget The new ID of the electronic device
     */
    public void setId_gadget(String id_gadget) {
        this.id_gadget = id_gadget;
    }

    /**
     * Gets the description of the report.
     *
     * @return The description of the report
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the report.
     *
     * @param description The new description of the report
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the severity level of the reported issue.
     *
     * @return The severity level
     */
    public Severity getSeverityType() {
        return severityType;
    }

    /**
     * Sets the severity level of the reported issue.
     *
     * @param severityType The new severity level
     */
    public void setSeverityType(Severity severityType) {
        this.severityType = severityType;
    }

    /**
     * Gets the date when the report was created.
     *
     * @return The date of the report
     */
    public LocalDate getDate_report() {
        return date_report;
    }

    /**
     * Sets the date when the report was created.
     *
     * @param date_report The new date of the report
     */
    public void setDate_report(LocalDate date_report) {
        this.date_report = date_report;
    }

    /**
     * Abstract method to get the report information as a formatted string.
     * Each concrete report type will implement this method according to its specific attributes.
     *
     * @return A string with the formatted report information
     */
    public abstract String getReportInfo();
}
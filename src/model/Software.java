package model;

import java.io.Serializable;

/**
 * Class that represents a software report.
 * This class extends from Report and adds specific software information.
 */
public class Software extends Report implements Serializable {

    private static final long serialVersionUID = 1L;

    // Software specific attributes
    private String systemOperation;
    private String softwareName;
    private String version;

    /**
     * Constructor for the Software class.
     *
     * @param id_gadget The ID of the electronic device
     * @param description The description of the report
     * @param severityType The severity level of the reported issue
     * @param date_report The date when the report was created
     * @param systemOperation The operating system of the device
     * @param softwareName The name of the software with issues
     * @param version The version of the software
     */
    public Software(String id_gadget, String description, Severity severityType,
                    java.time.LocalDate date_report, String systemOperation,
                    String softwareName, String version) {
        super(id_gadget, description, severityType, date_report);
        this.systemOperation = systemOperation;
        this.softwareName = softwareName;
        this.version = version;
    }

    /**
     * Gets the operating system of the device.
     *
     * @return The operating system
     */
    public String getSystemOperation() {
        return systemOperation;
    }

    /**
     * Sets the operating system of the device.
     *
     * @param systemOperation The new operating system
     */
    public void setSystemOperation(String systemOperation) {
        this.systemOperation = systemOperation;
    }

    /**
     * Gets the name of the software with issues.
     *
     * @return The software name
     */
    public String getSoftwareName() {
        return softwareName;
    }

    /**
     * Sets the name of the software with issues.
     *
     * @param softwareName The new software name
     */
    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    /**
     * Gets the version of the software.
     *
     * @return The software version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the version of the software.
     *
     * @param version The new software version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Gets the report information as a formatted string.
     *
     * @return A string with the software report information formatted with dashes between fields
     */
    @Override
    public String getReportInfo() {
        return id_gadget + "-" + description + "-" + severityType + "-" +
                date_report.toString() + "-" + systemOperation + "-" +
                softwareName + "-" + version;
    }
}
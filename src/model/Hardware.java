package model;

import java.io.Serializable;

/**
 * Class that represents a hardware report.
 * This class extends from Report and adds specific hardware information.
 */
public class Hardware extends Report implements Serializable {

    private static final long serialVersionUID = 1L;

    // Hardware specific attributes
    private String component_type;
    private int serialNumber;
    private boolean hasToReplace;

    /**
     * Constructor for the Hardware class.
     *
     * @param id_gadget The ID of the electronic device
     * @param description The description of the report
     * @param severityType The severity level of the reported issue
     * @param date_report The date when the report was created
     * @param component_type The type of the hardware component
     * @param serialNumber The serial number of the component
     * @param hasToReplace Indicates if the component needs to be replaced
     */
    public Hardware(String id_gadget, String description, Severity severityType,
                    java.time.LocalDate date_report, String component_type,
                    int serialNumber, boolean hasToReplace) {
        super(id_gadget, description, severityType, date_report);
        this.component_type = component_type;
        this.serialNumber = serialNumber;
        this.hasToReplace = hasToReplace;
    }

    /**
     * Gets the component type.
     *
     * @return The component type
     */
    public String getComponent_type() {
        return component_type;
    }

    /**
     * Sets the component type.
     *
     * @param component_type The new component type
     */
    public void setComponent_type(String component_type) {
        this.component_type = component_type;
    }

    /**
     * Gets the serial number of the component.
     *
     * @return The serial number
     */
    public int getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the serial number of the component.
     *
     * @param serialNumber The new serial number
     */
    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * Checks if the component needs to be replaced.
     *
     * @return True if the component needs to be replaced, false otherwise
     */
    public boolean isHasToReplace() {
        return hasToReplace;
    }

    /**
     * Sets if the component needs to be replaced.
     *
     * @param hasToReplace The new value indicating if replacement is needed
     */
    public void setHasToReplace(boolean hasToReplace) {
        this.hasToReplace = hasToReplace;
    }

    /**
     * Gets the report information as a formatted string.
     *
     * @return A string with the hardware report information formatted with dashes between fields
     */
    @Override
    public String getReportInfo() {
        return id_gadget + "-" + description + "-" + severityType + "-" +
                date_report.toString() + "-" + component_type + "-" +
                serialNumber + "-" + (hasToReplace ? "Sí" : "No");
    }
}
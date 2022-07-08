package model;

import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * This is the Appointment class for creating the Appointment objects.
 */
public class Appointments {

    private int id; //    Appointment_ID INT(10) (PK)
    private String title; //    Title VARCHAR(50)
    private String description;  //    Description VARCHAR(50)
    private String location; //    Location VARCHAR(50)
    private int contactId; //    Contact_ID INT(10) (FK)
    private String type; //    Type VARCHAR(50)
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int customerId;//    Customer_ID INT(10) (FK)
    private int userId; //    User_ID INT(10) (FK)

    private LocalDateTime createDateTime; //    Create_Date DATETIME
    private String createdBy; //    Created_By VARCHAR(50)
    private LocalDateTime lastUpdate; //    Last_Update TIMESTAMP
    private String lastUpdatedBy; //    Last_Updated_By VARCHAR(50)

    public Appointments(int id, String title, String description,
                        String location, String type,
                        LocalDateTime startDateTime,
                        LocalDateTime endDateTime, LocalDateTime createDateTime,
                        String createdBy,
                        LocalDateTime lastUpdate, String lastUpdatedBy,
                        int customerId, int userId, int contactId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.createDateTime = createDateTime;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    public Appointments (int id, String type){
        this.id = id;
        this.type = type;
    }

    /**
     *
     * @return Returns the appointment id.
     */
    public int getID() {
        return id;
    }

    /**
     *
     * @param id sets the id for the object
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     *
     * @return returns the appointment title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
      * @param title sets the title for the object
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return returns the description for the object
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description sets the description for the object
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return returns the location for the object
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location sets the location for the object
     */

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return returns the type for the object
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type sets the type  for the object
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return returns the start date time for the onject
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     *
     * @param startDateTime sets the start date time for the object
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     *
     * @return returns the end date time for the object.
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     *
     * @param endDateTime sets the end date time for the object
     */
    public void setEndDate(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     *
     * @return returns the create date time for the object
     */
    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    /**
     *
     * @param createDateTime sets the create date time for the object
     */
    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    /**
     *
     * @return retrieves the created by string for the appointment
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @param createdBy sets the created by string for the appointment
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

   /**
     *
     * @param lastUpdate sets the last update Local Date Time for the appointment
     */
    public void setLastUpdate (LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     *
     * @param lastUpdate gets the last update  Local Date Time for the appointment
     */
    public void getLastUpdate (LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     *
     * @return gets the last update by string for the appointment
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     *
     * @param lastUpdatedBy sets the last update by for the appointment
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     *
     * @return returns the customer id for the appointment
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     *
     * @param customerId sets the customer id for the appointment
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     *
     * @return returns the user id for the appointment
     */
    public int getUserId() {
        return userId;
    }

    /**
     *
     * @param userId sets the user id for the appointment
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     *
     * @return returns the contact id for the object
     */
    public int getContactId() {
        return contactId;
    }

    /**
     *
     * @param contactId sets the contact id for the object
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

}

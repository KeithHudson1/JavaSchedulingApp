package model;

import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 */
public class Appointments {


    private int id; //    Appointment_ID INT(10) (PK)
    private String title; //    Title VARCHAR(50)
    private String description;  //    Description VARCHAR(50)
    private String location; //    Location VARCHAR(50)
    private int contactId; //    Contact_ID INT(10) (FK)
    private String type; //    Type VARCHAR(50)
    private LocalDateTime startDateTime;
//    private LocalDate startDate; //    Start DATETIME
//    private LocalTime startTime;
//    private ObservableList<LocalTime> startTime;
    private LocalDateTime endDateTime;
//    private LocalDate endDate; //    End DATETIME
//    private LocalTime endTime;
    private int customerId;//    Customer_ID INT(10) (FK)
    private int userId; //    User_ID INT(10) (FK)

    private LocalDateTime createDateTime; //    Create_Date DATETIME
    private String createdBy; //    Created_By VARCHAR(50)
    private LocalDateTime lastUpdate; //    Last_Update TIMESTAMP
    private String lastUpdatedBy; //    Last_Updated_By VARCHAR(50)

//    private static ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
//    private static ObservableList<Customers> allCustomers = FXCollections.observableArrayList();
//    private static ObservableList<Contacts> allContacts = FXCollections.observableArrayList();
//    private static ObservableList<Users> allUsers= FXCollections.observableArrayList();


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
//        this.startDate = startDate;
//        this.startTime = startTime;
        this.endDateTime = endDateTime;
//        this.endDate = endDate;
//        this.endTime = endTime;
        this.createDateTime = createDateTime;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     *
     * @return
     */
    public int getID() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
      * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     */

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     *
     * @param startDateTime
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

//    public LocalTime getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(LocalTime startTime) {
//        this.startTime = startTime;
//    }


    /**
     *
     * @return
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     *
     * @param endDateTime
     */
    public void setEndDate(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

//    public LocalTime getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(LocalTime endTime){
//        this.endTime = endTime;
//    }

    /**
     *
     * @return
     */
    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    /**
     *
     * @param createDateTime
     */
    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    /**
     *
     * @return
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

   /**
     *
     * @param lastUpdate
     */
    public void setLastUpdate (LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     *
     * @param lastUpdate
     */
    public void getLastUpdate (LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     *
     * @return
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     *
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     *
     * @return
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     *
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     *
     * @return
     */
    public int getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     */
    public int getContactId() {
        return contactId;
    }

    /**
     *
     * @param contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     *
     * @param newAppt
     */
    public static void addAppointment (Appointments newAppt){

        // allAppointments.add(newAppt);
    }
}

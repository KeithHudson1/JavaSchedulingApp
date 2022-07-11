package model;

import DAO.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * This is the Customers class for creating the Customer object.
 */
public class Customers {

    private int customerId;  // Customer_ID INT(10) (PK)
    private String name; //Customer_Name VARCHAR(50)
    private String address; // Address VARCHAR(100)
    private int countryId; //Division_ID INT(10) (FK)
    private int divisionId; //Division_ID INT(10) (FK)
    private String postalCode; // Postal_Code VARCHAR(50)
    private String phone; // Phone VARCHAR(50)
    private LocalDateTime createDate; // Create_Date DATETIME
    private String createdBy; // Created_By VARCHAR(50)
    private LocalDateTime lastUpdate; // Last_Update TIMESTAMP
    private String lastUpdatedBy; // Last_Updated_By VARCHAR(50)


//    private static ObservableList<Customers> allCustomers = FXCollections.observableArrayList();;
//    private static ObservableList<FirstLevelDivisions> allFirstLevelDivisions = FXCollections.observableArrayList();

    public Customers(int customerId, String name, String address, String postalCode,
                     String phone, LocalDateTime createDate, String createdBy,
                     LocalDateTime lastUpdate, String lastUpdatedBy,  int divisionId) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
//        this.countryId = countryId;
        this.divisionId = divisionId;
    }
    public String toString() {return customerId + " " + name; }

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
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     *
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     *
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     *
     * @param createDate
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
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
     * @return
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     *
     * @param lastUpdate
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
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
    public int getDivisionId() {
        return divisionId;
    }

    /**
     *
     * @param divisionId
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

}

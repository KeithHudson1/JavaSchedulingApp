package model;

import DAO.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 */
public class Users {
    private int id; // User_ID INT(10) (PK)
    private String userName; //User_Name VARCHAR(50) (UNIQUE)
    private String password; // Password TEXT
    private String createDate; // Create_Date DATETIME
    private String createdBy; //Created_By VARCHAR(50)
    private String lastUpdate; //Last_Update TIMESTAMP
    private String lastUpdatedBy; //Last_Updated_By VARCHAR(50)

//    private static ObservableList<Users> allUsers = FXCollections.observableArrayList();

    public Users(int id, String userName, String password, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String toString() {
        return id + " " + userName;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     *
     * @param createDate
     */
    public void setCreateDate(String createDate) {
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
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     *
     * @param lastUpdate
     */
    public void setLastUpdate(String lastUpdate) {
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
}

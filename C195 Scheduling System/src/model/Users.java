package model;

import DAO.DBConnection;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 */
public class Users {
    private ObservableValue<Integer> id; // User_ID INT(10) (PK)
    private ObservableValue<String> userName; //User_Name VARCHAR(50) (UNIQUE)
    private ObservableValue<String> password; // Password TEXT
    private ObservableValue<String> createDate; // Create_Date DATETIME
    private ObservableValue<String> createdBy; //Created_By VARCHAR(50)
    private ObservableValue<String> lastUpdate; //Last_Update TIMESTAMP
    private ObservableValue<String> lastUpdatedBy; //Last_Updated_By VARCHAR(50)

//    private static ObservableList<Users> allUsers = FXCollections.observableArrayList();

    public Users(ObservableValue<Integer> id, ObservableValue<String> userName, ObservableValue<String> password, ObservableValue<String> createDate, ObservableValue<String> createdBy, ObservableValue<String> lastUpdate, ObservableValue<String> lastUpdatedBy) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String toString() {
        String idString = id.getValue().toString();
        String userNameString = userName.getValue().toString();
        String completeString = (idString + " " + userNameString);
        return (completeString);
    }

    /**
     *
     * @return
     */
    public ObservableValue<Integer> getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(ObservableValue<Integer> id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public ObservableValue<String> getUserName() {
        return userName;
    }

    /**
     *
     * @param userName
     */
    public void setUserName(ObservableValue<String> userName) {
        this.userName = userName;
    }

    /**
     *
     * @return
     */
    public ObservableValue<String> getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(ObservableValue<String> password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public ObservableValue<String> getCreateDate() {
        return createDate;
    }

    /**
     *
     * @param createDate
     */
    public void setCreateDate(ObservableValue<String> createDate) {
        this.createDate = createDate;
    }

    /**
     *
     * @return
     */
    public ObservableValue<String> getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @param createdBy
     */
    public void setCreatedBy(ObservableValue<String> createdBy) {
        this.createdBy = createdBy;
    }

    /**
     *
     * @return
     */
    public ObservableValue<String> getLastUpdate() {
        return lastUpdate;
    }

    /**
     *
     * @param lastUpdate
     */
    public void setLastUpdate(ObservableValue<String> lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     *
     * @return
     */
    public ObservableValue<String> getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     *
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(ObservableValue<String> lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}

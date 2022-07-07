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
public class Contacts {
    private int id; //Contact_ID INT(10) (PK)
    private String contactName; //Contact_Name VARCHAR(50)
    private String email; // Email VARCHAR(50)


    public Contacts(int id, String contactName, String email) {
        this.id = id;
        this.contactName = contactName;
        this.email = email;
    }

    @Override
    public String toString() {
        return id + " " + contactName;
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
    public String getContactName() {
        return contactName;
    }

    /**
     *
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }


}

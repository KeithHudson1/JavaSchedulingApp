package model;

import DAO.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is the Contact class for creating the Contact objects.
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

    /**
     *
     * @return a formatted string for the combo box
     */
    @Override
    public String toString() {
        return id + " " + contactName;
    }

    /**
     *
     * @return returns the contact id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id sets the id for the contact
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return returns the name for the contact
     */
    public String getContactName() {
        return contactName;
    }

    /**
     *
     * @param contactName sets the contact name for the contact
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     *
     * @return returns the email for the contact
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email sets the email for the contact
     */
    public void setEmail(String email) {
        this.email = email;
    }


}

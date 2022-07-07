package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * In this class we will have the Create, Read, Update and Delete files for the contacts table from the MySQL server.
 */
public class ContactsDaoImpl {
    private static ObservableList<Contacts> allContacts = FXCollections.observableArrayList();


    /**
     * This method obtains all of the contacts from the contacts table.
     * @return Returns a list of all contacts.
     */
    public static ObservableList<Contacts> getAllContacts() {
        allContacts.clear(); //Need to clear the list, otherwise the list gets duplicated.
        try{
            String sql = "SELECT * FROM client_schedule.contacts";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Contact_ID"); // Contact_ID INT(10) (PK)
                String contactName = rs.getString("Contact_Name"); // Contact_Name VARCHAR(50)
                String email = rs. getString("Email"); // Email VARCHAR(50)

                Contacts c = new Contacts(id, contactName, email);
                allContacts.add(c);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allContacts;
    }

    /**
     * This method retrieves a specific contact from the contacts table based on provided contact id.
     * @param id The contact id of the searched contact.
     * @return Returns the specific contact.
     */
    public static Contacts getContact(int id) {
        for (Contacts c : allContacts) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }
}

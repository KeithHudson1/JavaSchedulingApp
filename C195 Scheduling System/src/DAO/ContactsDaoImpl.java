package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsDaoImpl {
    private static ObservableList<Contacts> allContacts = FXCollections.observableArrayList();


    /**
     *
     * @return
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

    public static Contacts getContact(int id) {
        for (Contacts c : allContacts) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }
}

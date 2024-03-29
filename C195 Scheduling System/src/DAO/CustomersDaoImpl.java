package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * In this class we will have the Create, Read, Update and Delete files for the customers table from the MySQL server.
 */
public class CustomersDaoImpl {

    /**
     * Inserts the Customer into the customer table in the MySQL database.
     * @param name customer name
     * @param address customer address
     * @param postalCode  customer postal code
     * @param phone customer phone
     * @param createDateTime  customer create date and time
     * @param createdBy  customer created by value
     * @param lastUpdateDateTime  customer last update date and time
     * @param lastUpdatedBy  customer last update by value
     * @param divisionId  customer division id
     * @throws SQLException
     */
    public static int insert(String name, String address,
                             String postalCode, String phone,
                             LocalDateTime createDateTime,
                             String createdBy, LocalDateTime lastUpdateDateTime,
                             String lastUpdatedBy, int divisionId) throws SQLException {

        try {
            String sql = "INSERT INTO customers (Customer_Name, Address, " +
                    "Postal_Code, Phone, Create_Date, Created_By, " +
                    "Last_Update, Last_Updated_By, " +
                    "Division_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            //        ps.setString(DNE, id); // This is set by MySQL and it
            //        auto-incremented there.
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);

            ps.setTimestamp(5, Timestamp.valueOf(createDateTime));//Create_Date DATETIME;
            ps.setString(6, createdBy);
            ps.setTimestamp(7, Timestamp.valueOf(lastUpdateDateTime));// Last_Update TIMESTAMP;
            ps.setString(8, lastUpdatedBy);
            ps.setInt(9, divisionId); // This is a foreign key

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Insert Successful!");
            } else {
                System.out.println("INSERT FAILED!");
            }
            return rowsAffected;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
       return 0;
    }

    /**
     * This method updates a specific customer in the MySQL database.
     * @param id customer id
     * @param name  customer name
     * @param address  customer address
     * @param postalCode  customer postal code
     * @param phone customer phone number
     * @param createDateTime  customer create date and time
     * @param createdBy customer created by value
     * @param lastUpdateDateTime  customer last updated date and time
     * @param lastUpdatedBy  customer last update by value
     * @param divisionId  customer division id
     * @throws SQLException
     */
    public static int update(int id, String name, String address,
                             String postalCode, String phone,
                             LocalDateTime createDateTime,
                             String createdBy, LocalDateTime lastUpdateDateTime,
                             String lastUpdatedBy, int divisionId) throws SQLException{

        try {
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, " +
                    "Postal_Code = ?, Phone = ?, Create_Date = ?, Created_By = ?, " +
                    "Last_Update = ?, Last_Updated_By = ?, " +
                    "Division_ID = ? WHERE " +
                    "Customer_ID = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setTimestamp(5, Timestamp.valueOf(createDateTime));//Create_Date DATETIME;
            ps.setString(6, createdBy);
            ps.setTimestamp(7, Timestamp.valueOf(lastUpdateDateTime));// Last_Update TIMESTAMP;
            ps.setString(8, lastUpdatedBy);
            ps.setInt(9, divisionId); // This is a foreign key
            ps.setInt(10, id);

            int rowsAffected = ps.executeUpdate();
            if(rowsAffected >0) {
                System.out.println("Insert Successful!");
            }
            else{
                System.out.println("INSERT FAILED!");
            }
            return rowsAffected;
        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return 0;
    }

    /**
     * This method deletes the customer from the customer table in the mySQL database based on the provided id.
     * @param customerId customer id which you are wanting to delete
     * @throws SQLException
     */
    public static int delete(int customerId) throws SQLException {
        try {
            String sql = "DELETE FROM Customers WHERE Customer_ID = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setInt(1, customerId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Delete Successful!");
            } else {
                System.out.println("Delete FAILED!");
            }
            return rowsAffected;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    /**
     * Retrieves the customer object based on the customer id in teh MySQL database.
     * @param customerId customer id being searched for
     */
    public static Customers getCustomer (int customerId) {
        ObservableList<Customers> allCustomers =
                CustomersDaoImpl.getAllCustomers();

        for (int i = 0; i < allCustomers.size(); i++) {
            Customers searchedCustomer = allCustomers.get(i);
            if(searchedCustomer.getCustomerId() == customerId ){
                return searchedCustomer;
            }
        }
        return null;
    }

    /**
     * Retrieves a list of all customers form the MySQL database.
     * @return a list of all customers
     */
    public static ObservableList<Customers> getAllCustomers() {
        ObservableList<Customers> allCustomers = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Create_Date, customers.Created_By, customers.Last_Update, customers.Last_Updated_By, customers.Division_ID, Country_ID FROM customers, first_level_divisions WHERE customers.Division_ID=first_level_divisions.Division_ID";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("Customer_ID"); // Customer_ID INT(10) (PK)
                String customerName = rs.getString("Customer_Name"); // VARCHAR(50)
                String address = rs.getString("Address"); // VARCHAR(100)
                String postalCode = rs.getString("Postal_Code"); // VARCHAR(50)
                String phone = rs.getString("Phone"); //  VARCHAR(50)
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime(); // Create_Date  DATETIME
                String createdBy = rs.getString("Created_By"); // Created_By VARCHAR(50)
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime(); // Last_Update TIMESTAMP
                String lastUpdatedBy = rs.getString("Last_Updated_By"); // Last_Updated_By VARCHAR(50)
                int countryId = rs.getInt("Country_ID");
                int divisionId = rs.getInt("Division_ID"); // Division_ID INT(10) (FK)

                Customers c = new Customers(id, customerName,address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, countryId, divisionId);
                allCustomers.add(c);
            }
        }
        catch (NumberFormatException | SQLException e) {
            System.out.println("Wrong values detected!");
            System.out.print("Exception: " + e);
            System.out.print("Exception: " + e.getMessage());
        }
        return allCustomers;
    }
}

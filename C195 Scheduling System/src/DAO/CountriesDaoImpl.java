package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * In this class we will have the Create, Read, Update and Delete files for the countries table from the MySQL server.
 */
public class CountriesDaoImpl {

    public static ObservableList<Countries> allCountries =
            FXCollections.observableArrayList();

    /**
     *
     * @return
     */
    public static ObservableList<Countries> allCountries() {
        return allCountries;
    }

    /**
     * Retrieves a country based on the provided id.
     * @param id
     * @return
     */
    public static Countries getCountry(int id) {
        for(Countries c : allCountries){
            if(c.getCountryId() == id) {
                return c;
            }
        }
        return null;
    }

    /**
     * This method retrieves all of the Countries from the MySQL Database.
     * @return a list of all the countries
     */
    public static ObservableList<Countries> getAllCountries() {

        allCountries.clear(); //Need to clear the list, otherwise the list gets duplicated.
        try {
            String sql = "SELECT * FROM client_schedule.countries";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                String createDate = rs.getString("Create_Date");
                String createdBy = rs.getString("Created_By");
                String lastUpdate = rs.getString("Last_Update"); //    Last_Update TIMESTAMP
                String lastUpdatedBy = rs.getString("Last_Updated_By"); //    Last_Updated_By VARCHAR(50)

                Countries C = new Countries(id, country, createDate, createdBy, lastUpdate, lastUpdatedBy);
                allCountries.add(C);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allCountries;
    }
}

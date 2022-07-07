package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;
import model.FirstLevelDivisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class retrieves First Level Country data from the MySQL server.
 */
public class FirstLevelDivisionsDaoImpl {

    public static ObservableList<FirstLevelDivisions> allDivisions =
            FXCollections.observableArrayList();

    /**
     * This retrieves a specific first level division with the provided id.
     * @param id first level division id you are searching for
     */
    public static FirstLevelDivisions getFirstLevelDivision (int id) {
        for(FirstLevelDivisions div : allDivisions){
            if(div.getId() == id) {
                return div;
            }
        }
        return null;
    }

    /**
     * This returns a list of all the first level divisions for the drop down box.
     * @return list of first level divisions
     */
    public static ObservableList<FirstLevelDivisions> getAllFirstLevelDivisions () {
        allDivisions.clear(); //Need to clear the list, otherwise the list gets
        // duplicated.
        try{
            String sql = "SELECT * FROM client_schedule.first_level_divisions";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                String createDate = rs.getString("Create_Date");
                String createdBy = rs.getString("Created_By");
                String lastUpdate = rs.getString("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryId = rs.getInt("Country_ID");

                FirstLevelDivisions fld = new FirstLevelDivisions(id, division, createDate, createdBy, lastUpdate, lastUpdatedBy, countryId);
                allDivisions.add(fld);
            }
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return allDivisions;
    }

}

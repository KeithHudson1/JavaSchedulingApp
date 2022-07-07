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
public class FirstLevelDivisions {

    private int id; //Division_ID INT(10) (PK)
    private String division; // Division VARCHAR(50)
    private String createDate; //Create_Date DATETIME
    private String createdBy; // Created_By VARCHAR(50)
    private String lastUpdate; // Last_Update TIMESTAMP
    private String lastUpdatedBy; // Last_Updated_By VARCHAR(50)
    private int countryId; // Country_ID INT(10) (FK)

    private static ObservableList<FirstLevelDivisions> allFirstLevelDivisions = FXCollections.observableArrayList();

    public FirstLevelDivisions(int id, String division, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy, int countryId) {
        this.id = id;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
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
    public String getDivision() {
        return division;
    }

    /**
     *
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
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

    /**
     *
     * @return
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     *
     * @param countryId
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     *
     * @return
     */
    /*public static ObservableList<FirstLevelDivisions> getAllFirstLevelDivisions () {
        allFirstLevelDivisions.clear(); //Need to clear the list, otherwise the list gets duplicated.
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
                allFirstLevelDivisions.add(fld);
            }
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return allFirstLevelDivisions;
    }*/

    @Override
    public String toString () {
        return ("" + Integer.toString(id) + " " + division + " " + countryId);
    }
}

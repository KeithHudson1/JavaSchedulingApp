package model;

import DAO.CountriesDaoImpl;
import DAO.CustomersDaoImpl;
import DAO.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 */
public class Countries {
    private int countryId; // Country_ID INT(10) (PK)
    private String country; // Country VARCHAR(50)
    private String createDate; //Create_Date DATETIME
    private String createdBy; // Created_By VARCHAR(50)
    private String lastUpdate; //Last_Update TIMESTAMP
    private String lastUpdatedBy; // Last_Updated_By VARCHAR(50)

//    private static ObservableList<Countries> allCountries = FXCollections.observableArrayList();

    public Countries(int countryId, String country, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy) {
        this.countryId = countryId;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
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
     * @param id
     */
    public void setCountryId(int id) {
        this.countryId = id;
    }

    /**
     *
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
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
    public static ObservableList<Countries> allCountries() {
        return CountriesDaoImpl.allCountries;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString () {
        return ("" + Integer.toString(countryId) + " " + country + " " );
    }
}

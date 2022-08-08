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
 * This is the Countries class for creating the Countries object.
 */
public class Countries {
    private int countryId; // Country_ID INT(10) (PK)
    private String country; // Country VARCHAR(50)
    private String createDate; //Create_Date DATETIME
    private String createdBy; // Created_By VARCHAR(50)
    private String lastUpdate; //Last_Update TIMESTAMP
    private String lastUpdatedBy; // Last_Updated_By VARCHAR(50)


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
     * @return formatted string for the country combo boxes
     */
    @Override
    public String toString () {
        return ("" + Integer.toString(countryId) + " " + country + " " );
    }

    /**
     * @return returns the id for the country
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     *
     * @param id sets the id for the country
     */
    public void setCountryId(int id) {
        this.countryId = id;
    }

    /**
     *
     * @return returns the country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country sets the country value
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return returns the create date for the Country
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate sets the create date for the country
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     *
     * @return returns the created by string for teh country
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @param createdBy sets the created by string for the country
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     *
     * @return returns the last update string for the country.
     */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     *
     * @param lastUpdate sets the last update string for the country
     */
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     *
     * @return returns the last update by string
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     *
     * @param lastUpdatedBy sets the last update by string
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /** @return returns a list of countries */
    public static ObservableList<Countries> allCountries() {
        return CountriesDaoImpl.allCountries;
    }


}

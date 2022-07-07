package controller;


import DAO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is the controller for the report page of the app.
 */
public class ReportPage implements Initializable {
    public Button backButton;
    public Button exitButton;

    public TableView<Users> usersTable;
    public TableColumn<?,?> usersUserIdCol;
    public TableColumn<?,?> usersUserNameCol;
    public TableColumn<?,?> usersPasswordCol;
    public TableColumn<?,?> usersCreateDateCol;
    public TableColumn<?,?> usersCreatedByCol;
    public TableColumn<?,?> usersLastUpdateCol;
    public TableColumn<?,?> usersLastUpdatedByCol;

    public TableView<Contacts> contactsTable;
    public TableColumn<?,?> contactIdCol;
    public TableColumn<?,?> contactNameCol;
    public TableColumn<?,?> contactEmailCol;

    public TableView<Countries> countriesTable;
    public TableColumn<?,?> countryIdCol;
    public TableColumn<?,?> countryNameCol;
    public TableColumn<?,?> createDateCol;
    public TableColumn<?,?> createdByCol;
    public TableColumn<?,?> lastUpdateDateCol;
    public TableColumn<?,?> lastUpdatedByCol;

    public TableView<FirstLevelDivisions> divisionsTable;
    public TableColumn<?,?> divisionIdCol;
    public TableColumn<?,?> divisionNameCol;
    public TableColumn<?,?> divisionCreateDateCol;
    public TableColumn<?,?> divisionCreatedByCol;
    public TableColumn<?,?> divisionLastUpdateCol;
    public TableColumn<?,?> divisionLastUpdatedByCol;
    public TableColumn<?,?> divisionCountryIdCol;

    public TableView<Customers> customersTable;
    public TableColumn<?,?> customerIdCol;
    public TableColumn<?,?> customerNameCol;
    public TableColumn<?,?> addressCol;
    public TableColumn<?,?> postalCodeCol;
    public TableColumn<?,?> phoneCol;
    public TableColumn<?,?> customerCreateDateCol;
    public TableColumn<?,?> customerCreatedByCol;
    public TableColumn<?,?> customerLastUpdateDateCol;
    public TableColumn<?,?> customerLastUpdatedByCol;
    public TableColumn<?,?> customerDivisionIdCol;

    public TabPane customerAppointmentTable;

    public TabPane contactAppointmentTable;
    public Tab customersTab1;
    public TableView<Appointments> customersAppointmentTable;
    public TableColumn apptContactIdCol1;
    public TableColumn apptIdCol1;
    public TableColumn apptTitleCol1;
    public TableColumn apptDescriptionCol1;
    public TableColumn apptTypeCol1;
    public TableColumn apptStartDateAndTimeCol1;
    public TableColumn apptEndDateAndTimeCol1;
    public TableColumn apptCustomerIdCol1;


    /**
     * This handles the intiialize and table loading for the report page.
     * @param url location
     * @param resourceBundle resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(getClass().getName() + "in initialize.");

        usersTable.setItems(UserDaoImpl.getAllUsers());

        usersUserIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        usersUserNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        usersPasswordCol.setText("P******");
        usersCreateDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        usersCreatedByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        usersLastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        usersLastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));

        contactsTable.setItems(ContactsDaoImpl.getAllContacts());

        contactIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        contactNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        contactEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        countriesTable.setItems(CountriesDaoImpl.getAllCountries());

        countryIdCol.setCellValueFactory(new PropertyValueFactory<>("countryId"));
        countryNameCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateDateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));

        divisionsTable.setItems(FirstLevelDivisionsDaoImpl.getAllFirstLevelDivisions());

        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        divisionNameCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        divisionCreateDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        divisionCreatedByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        divisionLastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        divisionLastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        divisionCountryIdCol.setCellValueFactory(new PropertyValueFactory<>("countryId"));

        customersTable.setItems(CustomersDaoImpl.getAllCustomers());

        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerCreateDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        customerCreatedByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        customerLastUpdateDateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        customerLastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        customerDivisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));


        ObservableList<Appointments> customerAppointmentList =
                FXCollections.observableArrayList();

        apptContactIdCol1.setCellValueFactory(new PropertyValueFactory<>(
                "contactId"));
        apptIdCol1.setCellValueFactory(new PropertyValueFactory<>("ID"));
        apptTitleCol1.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescriptionCol1.setCellValueFactory(new PropertyValueFactory<>("description"));

        apptTypeCol1.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartDateAndTimeCol1.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        apptEndDateAndTimeCol1.setCellValueFactory(new PropertyValueFactory<>(
                "endDateTime"));
        apptCustomerIdCol1.setCellValueFactory(new PropertyValueFactory<>(
                "customerId"));

        try {
//            customerAppointmentList.addAll(AppointmentsDaoImpl.getAllAppointments());
//            customersAppointmentTable.setItems(customerAppointmentList);
            ObservableList<Appointments> allAppointments =
                    FXCollections.observableArrayList();
            allAppointments.addAll(AppointmentsDaoImpl.getAllAppointments());
            ObservableList<Contacts> allContacts =
                    FXCollections.observableArrayList();
            allContacts.addAll(ContactsDaoImpl.getAllContacts());
            int i = 1 ;
            for (Contacts c : allContacts) {
                contactAppointmentTable.getTabs().get(i-1).setText("Contact " + c.getId() + " Appointments");
                i++;
            }
        } catch (Exception e){
            Logger.getLogger(AppointmentView.class.getName()).log(Level.SEVERE,
                    null, e);
        }
    }

    /**
     * This handles the change to the Menu View.
     * @param actionEvent from the Back button click
     * @throws IOException .
     */
    public void onBackButton(ActionEvent actionEvent) throws IOException {
        System.out.println(getClass().getName() + " :Back Button clicked.");

        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view" +
                "/MenuView.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * This handles the closing of te app.
     * @param actionEvent from the Exit button click
     */
    public void onExitButton(ActionEvent actionEvent) {
        System.out.println(getClass().getName() + " :Exit Button clicked.");
        Alert exit = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to exit?", ButtonType.YES);
        Optional<ButtonType> result = exit.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.YES) {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        }
    }

}

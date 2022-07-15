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
    public TableColumn<Users,Integer> usersUserIdCol;
    public TableColumn<Users,String> usersUserNameCol;
    public TableColumn<Users,String> usersPasswordCol;
    public TableColumn<Users,String> usersCreateDateCol;
    public TableColumn<Users,String> usersCreatedByCol;
    public TableColumn<Users,String> usersLastUpdateCol;
    public TableColumn<Users,String> usersLastUpdatedByCol;

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


    public Tab customersTab1;
    public TableView<Appointments> customersAppointmentTable;
    public TableColumn<?,?> apptContactIdCol1;
    public TableColumn<?,?> apptIdCol1;
    public TableColumn<?,?> apptTitleCol1;
    public TableColumn<?,?> apptDescriptionCol1;
    public TableColumn<?,?> apptTypeCol1;
    public TableColumn<?,?> apptStartDateAndTimeCol1;
    public TableColumn<?,?> apptEndDateAndTimeCol1;
    public TableColumn<?,?> apptCustomerIdCol1;
    public TabPane contactAppointmentTabPane;
    public TableView<Appointments> contactsAppointmentTable;
    public TableView<?> contactsAppointmentTable2;
    public TableView<?> contactsAppointmentTable3;
    public TableView<?> contactsAppointmentTable5;
    public TableView<?> contactsAppointmentTable4;
    public ComboBox<Contacts> contactFilterComboBox;
    public Label appointmentNumberLbl;
    public ComboBox monthSelectorBox;
    public TableView<Appointments> appointmentByTypeTable;
    public TableColumn<?,?>typeColumn;
    public TableColumn<?,?>countColumn;
    public Label monthFilterLbl;


    /**
     * This handles the intiialize and table loading for the report page.
     * LAMBDA EXPRESSION: These made it possible to associate the object get functions and assign them to a column value.
     * This helps utilize code already written and aid maintenance down the road.
     * @param url location
     * @param resourceBundle resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(getClass().getName() + "in initialize.");

        usersTable.setItems(UserDaoImpl.getAllUsers());

        usersUserIdCol.setCellValueFactory(cellData -> {
            return cellData.getValue().getId();
        });
        usersUserNameCol.setCellValueFactory(cellData -> {
            return cellData.getValue().getUserName();
        });
        usersPasswordCol.setText("P******");
        usersCreateDateCol.setCellValueFactory(cellData -> {
            return cellData.getValue().getCreateDate();
        });
        usersCreatedByCol.setCellValueFactory(cellData -> {
            return cellData.getValue().getCreatedBy();
        });
        usersLastUpdateCol.setCellValueFactory(cellData -> {
            return cellData.getValue().getLastUpdate();
        });
        usersLastUpdatedByCol.setCellValueFactory(cellData -> {
            return cellData.getValue().getLastUpdatedBy();
        });


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


        apptContactIdCol1.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        apptIdCol1.setCellValueFactory(new PropertyValueFactory<>("ID"));
        apptTitleCol1.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescriptionCol1.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptTypeCol1.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartDateAndTimeCol1.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        apptEndDateAndTimeCol1.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        apptCustomerIdCol1.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        contactFilterComboBox.setItems(ContactsDaoImpl.getAllContacts());

        ObservableList<String> monthsList = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December");
        monthSelectorBox.setItems(monthsList);



        try {

            ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
            allAppointments.addAll(AppointmentsDaoImpl.getAllAppointments());
            ObservableList<Contacts> allContacts = FXCollections.observableArrayList();
            allContacts.addAll(ContactsDaoImpl.getAllContacts());
            int totalAppointments = allAppointments.size();
            appointmentNumberLbl.setText(String.valueOf(totalAppointments));

        } catch (Exception e){
            Logger.getLogger(AppointmentView.class.getName()).log(Level.SEVERE,
                    null, e);
        }
    }

    /**
     *
     * @param actionEvent
     * @throws Exception
     */
    public void onContactFilterComboBox(ActionEvent actionEvent) throws Exception {
        try {
            Contacts selectedContact = contactFilterComboBox.getSelectionModel().getSelectedItem();
            ObservableList<Appointments> contactsAppointments = AppointmentsDaoImpl.getAppointmentForContact(selectedContact.getId());
            contactsAppointmentTable.setItems(contactsAppointments);
        }
        catch (NullPointerException e) {
            System.out.println("Null values detected!");
            System.out.print("Exception: " + e);
            System.out.print("Exception: " + e.getMessage());
        }
    }

    /**
     *
     * @param actionEvent
     */
    public void onMonthSelectorBox(ActionEvent actionEvent) {
        int selectedMonthInt = monthSelectorBox.getSelectionModel().getSelectedIndex() +1;
        ObservableList<Appointments> resultAppointments = AppointmentsDaoImpl.getAppointmentsByMonthAndType(selectedMonthInt);
        if (resultAppointments.isEmpty()){
            monthFilterLbl.setText("No appointments found");
            appointmentByTypeTable.setItems(resultAppointments);
        }
        else {
            monthFilterLbl.setText("Appointments found");
            appointmentByTypeTable.setItems(resultAppointments);
//            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));  //works
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            countColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
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
        Parent root = FXMLLoader.load(getClass().getResource("/view/MenuView.fxml"));
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

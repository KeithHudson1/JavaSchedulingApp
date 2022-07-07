package controller;

import DAO.CountriesDaoImpl;
import DAO.CustomersDaoImpl;
import DAO.DBConnection;
import DAO.FirstLevelDivisionsDaoImpl;
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
import model.Countries;
import model.Customers;
import model.FirstLevelDivisions;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class CustomerView implements Initializable {

    public Button backButton;
    public Button exitButton;

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
    public Button deleteCustomerButton;
    public Button editCustomerButton;

    public TextField editCustomerIdText;
    public TextField editCustomerNameText;
    public TextField editCustomerAddressText;
    public TextField editCustomerPostalCodeText;
    public TextField editCustomerPhoneText;
//    public ComboBox editCustomerCountryComboBox;
    public ComboBox<Countries> editCustomerCountryComboBox;
//    public ComboBox editCustomerFirstDivisionComboBox;
    public ComboBox<FirstLevelDivisions> editCustomerFirstDivisionComboBox;

    public Button cancelCustomerChangeButton;
    public Button saveEditCustomerButton;


    public TextField newCustomerIdText;
    public TextField newCustomerNameText;
    public TextField newCustomerPhoneText;
    public TextField newCustomerAddressText;
    public TextField newCustomerPostalCodeText;
    public ComboBox<Countries> newCustomerCountryCombo;
    public ComboBox<FirstLevelDivisions> newCustomerDivisionCombo;
    public Button saveNewCustomerButton;
    public Button clearNewCustomerButton;
    public Label errorMessageLbl;

    ObservableList<Customers> allCustomers = FXCollections.observableArrayList();

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(getClass().getName() + " in initialize.");

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

        try {
            allCustomers.addAll(CustomersDaoImpl.getAllCustomers());

        } catch (Exception ex) {
            Logger.getLogger(CustomerView.class.getName()).log(Level.SEVERE, null,
                    ex);
        }

        customersTable.setItems(allCustomers);

        //Using Lambda for efficient selection off a tableview

        ObservableList<Countries> countries =
                CountriesDaoImpl.getAllCountries();
        editCustomerCountryComboBox.setItems(countries);
        newCustomerCountryCombo.setItems(countries);

    }

    /**
     *
     * @param actionEvent
     */
    public void onEditCustomerButton(ActionEvent actionEvent) {
        System.out.println(getClass().getName() + " :Edit Customer button clicked.");

        Customers selectedCustomer =
                customersTable.getSelectionModel().getSelectedItem();

        editCustomerIdText.setText(String.valueOf(selectedCustomer.getCustomerId()));
        editCustomerNameText.setText(selectedCustomer.getName());
        editCustomerAddressText.setText(selectedCustomer.getAddress());

        int customerDivisionId = selectedCustomer.getDivisionId();
        Countries customerCountry = null;

        for(FirstLevelDivisions div : FirstLevelDivisionsDaoImpl.getAllFirstLevelDivisions()){
            if(div.getId() == customerDivisionId) {
                int customerCountryId = div.getCountryId();

                customerCountry =
                        CountriesDaoImpl.getCountry(customerCountryId);
            }
        }

        editCustomerCountryComboBox.setValue(customerCountry);
        FirstLevelDivisions countryDivision =
                FirstLevelDivisionsDaoImpl.getFirstLevelDivision(customerDivisionId);

        editCustomerFirstDivisionComboBox.setValue(countryDivision);
        editCustomerPostalCodeText.setText(selectedCustomer.getPostalCode());
        editCustomerPhoneText.setText(selectedCustomer.getPhone());

    }

    /**
     *
     * @param actionEvent
     */
    public void onDeleteCustomerButton(ActionEvent actionEvent) throws SQLException {
        System.out.println(getClass().getName() + " :Delete Customer button clicked.");
        try {
            Customers selectedCustomer =
                    customersTable.getSelectionModel().getSelectedItem();
            String selectedCustomerId =
                    String.valueOf(selectedCustomer.getCustomerId());


            String sql = "SELECT * FROM appointments WHERE Customer_ID= ?";
            PreparedStatement ps =
                    DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, selectedCustomerId);

            ResultSet rs = ps.executeQuery();
            System.out.print(rs);



            if(!rs.next()) {
                String message = String.format("Are you sure you want to " +
                        "delete the customer %s?", selectedCustomer.getName());
                Alert deleteCustomer = new Alert(Alert.AlertType.CONFIRMATION
                        , message);
                Optional<ButtonType> result = deleteCustomer.showAndWait();
                if( result.isPresent() && result.get() == ButtonType.OK) {
                    CustomersDaoImpl.delete(selectedCustomer.getCustomerId());
                    customersTable.setItems(CustomersDaoImpl.getAllCustomers());
                    String errorMessage = String.format("Customer %s has been" +
                            " deleted" +
                            ".", selectedCustomer.getName());
                    errorMessageLbl.setText(errorMessage);
                }
            }
            else{
                String message = String.format("Customer %s still has an " +
                        "appointment active. " +
                        "Please delete it and try again.",
                        selectedCustomer.getName());

                Alert appointmentsActive = new Alert(Alert.AlertType.WARNING,
                        message);
                appointmentsActive.showAndWait();
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     *
     * @param actionEvent
     * @throws IOException
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
     *
     * @param actionEvent
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

    /**
     *
     * @param actionEvent
     */
    public void onSaveNewCustomerButton(ActionEvent actionEvent) {
        System.out.println(getClass().getName() + " :Save New Customer button clicked.");

        try {
            String customerName = newCustomerNameText.getText();
            String customerAddress = newCustomerAddressText.getText();
            Countries customerCountry = newCustomerCountryCombo.getSelectionModel().getSelectedItem();
            FirstLevelDivisions customerFirstDivision =
                    newCustomerDivisionCombo.getSelectionModel().getSelectedItem();
            int divisionId = customerFirstDivision.getId();
            String customerPostalCode = newCustomerPostalCodeText.getText();
            String customerPhone = newCustomerPhoneText.getText();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDate createDate = LocalDate.now();
            String createTime = LocalTime.now().format(timeFormatter);
            System.out.println(createDate);
            System.out.println(createTime);

            String createDateTimeString = (createDate + " " + createTime);
            System.out.println(createDateTimeString);
            LocalDateTime createDateTime = LocalDateTime.parse(createDateTimeString, formatter);

            String createdBy = "User";
            LocalDateTime lastUpdateDateTime = LocalDateTime.now();
            String lastUpdatedBy = "User";


            CustomersDaoImpl.insert(customerName, customerAddress,
                    customerPostalCode, customerPhone, createDateTime,
                    createdBy,
                    lastUpdateDateTime, lastUpdatedBy, divisionId);

            customersTable.setItems(CustomersDaoImpl.getAllCustomers());

            System.out.println(customerName + " added to customer list");

        }
        catch (NumberFormatException | SQLException e) {
            System.out.println("Wrong values detected!");
            System.out.print("Exception: " + e);
            System.out.print("Exception: " + e.getMessage());
        }
        newCustomerIdText.clear();
        newCustomerNameText.clear();
        newCustomerPhoneText.clear();
        newCustomerAddressText.clear();
        newCustomerPostalCodeText.clear();
        // These would cause an issue with the CountryCombo methods.
//        newCustomerCountryCombo.valueProperty().set(null);
//        newCustomerDivisionCombo.valueProperty().set(null);
    }

    /**
     *
     * @param actionEvent
     */
    public void onClearNewCustomerButton(ActionEvent actionEvent) {
        System.out.println(getClass().getName() + " :Clear button clicked.");
        newCustomerIdText.clear();
        newCustomerNameText.clear();
        newCustomerPhoneText.clear();
        newCustomerAddressText.clear();
        newCustomerPostalCodeText.clear();
        // These would cause an issue with the CountryCombo methods.
//        newCustomerCountryCombo.valueProperty().set(null);
//        newCustomerDivisionCombo.valueProperty().set(null);
    }

    /**
     *
     * @param actionEvent
     */
    public void onSaveEditCustomerButton(ActionEvent actionEvent) {
        System.out.println(getClass().getName() + " :Save New Customer button clicked.");

        try {
            int customerId = Integer.parseInt(editCustomerIdText.getText());
            Customers selectedCustomer = CustomersDaoImpl.getCustomer(customerId);
            String customerName = editCustomerNameText.getText();
            String customerAddress = editCustomerAddressText.getText();
            Countries customerCountry = editCustomerCountryComboBox.getSelectionModel().getSelectedItem();
            FirstLevelDivisions customerFirstDivision = editCustomerFirstDivisionComboBox.getSelectionModel().getSelectedItem();
            String customerPostalCode = editCustomerPostalCodeText.getText();
            String customerPhone = editCustomerPhoneText.getText();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime createDateTime = selectedCustomer.getCreateDate();

            String createdBy = selectedCustomer.getCreatedBy();
            LocalDateTime lastUpdateDateTime = LocalDateTime.now();
            String lastUpdatedBy = "User";
            int divisionId = customerFirstDivision.getId();

            CustomersDaoImpl.update (customerId, customerName, customerAddress,
                    customerPostalCode, customerPhone, createDateTime,
                    createdBy,
                    lastUpdateDateTime, lastUpdatedBy, divisionId);

            customersTable.setItems(CustomersDaoImpl.getAllCustomers());

            System.out.println(customerName + " added to customer list");

        } catch (NumberFormatException | SQLException e) {
            System.out.println("Wrong values detected!");
            System.out.print("Exception: " + e);
            System.out.print("Exception: " + e.getMessage());
        }

        editCustomerIdText.clear();
        editCustomerNameText.clear();
        editCustomerAddressText.clear();
        editCustomerPostalCodeText.clear();
        editCustomerPhoneText.clear();
        // These would cause an issue with the CountryCombo methods.
//        editCustomerCountryComboBox.valueProperty().set(null);
//        editCustomerFirstDivisionComboBox.valueProperty().set(null);
    }

    /**
     *
     * @param actionEvent
     */
    public void onCancelCustomerChangeButton(ActionEvent actionEvent) {
        editCustomerIdText.clear();
        editCustomerNameText.clear();
        editCustomerAddressText.clear();
        editCustomerPostalCodeText.clear();
        editCustomerPhoneText.clear();
        // These would cause an issue with the CountryCombo methods.
//        editCustomerCountryComboBox.valueProperty().set(null);
//        editCustomerFirstDivisionComboBox.valueProperty().set(null);

    }

    public void onNewCustomerCountryCombo(ActionEvent actionEvent) {
        ObservableList<FirstLevelDivisions> divisionsWithCountryId = FXCollections.observableArrayList();

        int divisionsToChoose = -1;

        Countries chosenCountry = newCustomerCountryCombo.getSelectionModel().getSelectedItem();
        divisionsToChoose = chosenCountry.getCountryId();

        for (FirstLevelDivisions div :
                FirstLevelDivisionsDaoImpl.getAllFirstLevelDivisions()) {

            if (divisionsToChoose == div.getCountryId()){
//                editCustomerFirstDivisionComboBox.setValue(div);
                divisionsWithCountryId.add(div);
                newCustomerDivisionCombo.setItems(divisionsWithCountryId);
            }
        }
    }

    public void onEditCustomerCountryComboBox(ActionEvent actionEvent) {
        ObservableList<FirstLevelDivisions> divisionsWithCountryId = FXCollections.observableArrayList();

        int divisionsToChoose = -1;

        Countries chosenCountry = editCustomerCountryComboBox.getSelectionModel().getSelectedItem();
        divisionsToChoose = chosenCountry.getCountryId();

        for (FirstLevelDivisions div :
                FirstLevelDivisionsDaoImpl.getAllFirstLevelDivisions()) {

            if (divisionsToChoose == div.getCountryId()){
//                editCustomerFirstDivisionComboBox.setValue(div);
                divisionsWithCountryId.add(div);
                editCustomerFirstDivisionComboBox.setItems(divisionsWithCountryId);
            }
        }
    }
}

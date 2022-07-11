package controller;

import DAO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * This class houses the needed things to initialize and load the Appointment View for the app.
 */
public class AppointmentView implements Initializable {

    public RadioButton thisMonthAppointmentsRadial;
    public RadioButton thisWeekAppointmentsRadial;
    public RadioButton allAppointmentsRadial;
    public ToggleGroup appointmentFilterGroup;

    public Button editAppointmentButton;
    public Button deleteAppointmentButton;

    public TextField editAppointmentIdText;
    public TextField editAppointmentTitleText;
    public TextField editAppointmentDescriptionText;
    public TextField editAppointmentLocationText;
    public ComboBox<String> editAppointmentTypeComboBox;
    public DatePicker editAppointmentStartDate;
    public ComboBox<LocalTime> editAppointmentStartTime;
    public DatePicker editAppointmentEndDate;
    public ComboBox<LocalTime> editAppointmentEndTime;
    public Button editAppointmentSaveChangesButton;
    public Button editAppointmentCancelChangesButton;

    public TextField newAppointmentIdText;
    public TextField newAppointmentTitleText;
    public TextField newAppointmentDescriptionText;
    public TextField newAppointmentLocationText;
    public ComboBox<String> newAppointmentTypeComboBox;
    public ComboBox<LocalTime> newAppointmentStartTime;
    public ComboBox<LocalTime> newAppointmentEndTime;
    public DatePicker newAppointmentStartDate;
    public DatePicker newAppointmentEndDate;
    public Button newAppointmentSaveButton;
    public Button newAppointmentClearButton;

    @FXML
    public TableView<Appointments> appointmentTableView;
    public TableColumn<?,?> apptIdCol;
    public TableColumn<?,?> apptLocationCol;
    @FXML
    public TableColumn<?,?> apptTitleCol;
    @FXML
    public TableColumn<?,?> apptDescriptionCol;
    @FXML
    public TableColumn<?,?> apptTypeCol;
    @FXML
    public TableColumn<?,?> apptStartDateAndTimeCol;
    @FXML
    public TableColumn<?,?> apptEndDateAndTimeCol;
    @FXML
    public TableColumn<?,?> apptCustomerIdCol;
    @FXML
    public TableColumn<?,?> apptUserIdCol;
    public Button backButton;
    public Button exitButton;
    public Label errorMessageLbl;
    public TableColumn apptContactCol;
    public ComboBox<Contacts> newAppointmentContactComboBox;
    public ComboBox<Contacts> editAppointmentContactComboBox;
    public ComboBox<Customers> editAppointmentCustomerCombo;
    public ComboBox<Users> editAppointmentUserCombo;
    public ComboBox<Customers> newAppointmentCustomerCombo;
    public ComboBox<Users> newAppointmentUserCombo;

    ObservableList<Appointments> appointmentList =
            FXCollections.observableArrayList();

     /**
     * This handles the initial loading of the appointment view window.
      * @param url location
      * @param resourceBundle resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(getClass().getName() + " in initialize.");

        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartDateAndTimeCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        apptEndDateAndTimeCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        ObservableList<String> typeOptions = FXCollections.observableArrayList();
        typeOptions.add("De-Briefing");
        typeOptions.add("Planning Session");
        typeOptions.add("One-on-One");
        editAppointmentTypeComboBox.setItems(typeOptions);
        newAppointmentTypeComboBox.setItems(typeOptions);

        LocalDate easternDate = LocalDate.now();
        LocalTime easternStartTime = LocalTime.of(8,0);
        ZoneId easternZoneId = ZoneId.of("America/New_York");
        ZonedDateTime easternStartZDT = ZonedDateTime.of(easternDate, easternStartTime, easternZoneId);
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        Instant easternToLocalInstance = easternStartZDT.toInstant();
        ZonedDateTime easternStartToLocalZDT = easternStartZDT.withZoneSameInstant(localZoneId);
        LocalTime localStartTimeFromEastern = easternStartToLocalZDT.toLocalTime();
        LocalTime start = easternStartToLocalZDT.toLocalTime();

        LocalTime easternEndTime = LocalTime.of(22,00);
        ZonedDateTime easternEndZDT = ZonedDateTime.of(easternDate, easternEndTime, easternZoneId);

        Instant easternToLocalInstanceEnd = easternEndZDT.toInstant();
        ZonedDateTime easternEndToLocalZDT = easternEndZDT.withZoneSameInstant(localZoneId);
        LocalTime localEndTimeFromEastern = easternEndToLocalZDT.toLocalTime();
        LocalTime end = easternEndToLocalZDT.toLocalTime();

//        while(start.isBefore(end.minusMinutes(15))) {
        while(start.isBefore(end)) {
            editAppointmentStartTime.getItems().add(start);
            editAppointmentEndTime.getItems().add(start.plusMinutes(15));
            newAppointmentStartTime.getItems().add(start);
            newAppointmentEndTime.getItems().add(start.plusMinutes(15));

            start = start.plusMinutes(15);
        }

        try {
            appointmentList.addAll(AppointmentsDaoImpl.getAllAppointments());
            appointmentTableView.setItems(appointmentList);

            editAppointmentContactComboBox.setItems(ContactsDaoImpl.getAllContacts());
            newAppointmentContactComboBox.setItems(ContactsDaoImpl.getAllContacts());
            editAppointmentCustomerCombo.setItems(CustomersDaoImpl.getAllCustomers());
            newAppointmentCustomerCombo.setItems(CustomersDaoImpl.getAllCustomers());
            editAppointmentUserCombo.setItems(UserDaoImpl.getAllUsers());
            newAppointmentUserCombo.setItems(UserDaoImpl.getAllUsers());

        } catch (Exception e) {
            Logger.getLogger(AppointmentView.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * This handles the filtering of the table view to the appointments for the next 30 days.
     * @param actionEvent from the all appointments radial
     */
    public void onAllAppointmentsRadial(ActionEvent actionEvent) {
        try {
            appointmentList.clear();
            appointmentList.addAll(AppointmentsDaoImpl.getAllAppointments());
            appointmentTableView.setItems(appointmentList);
        } catch (Exception e) {
            Logger.getLogger(AppointmentView.class.getName()).log(Level.SEVERE,
                    null, e);
        }
    }

    /**
     * This handles the filtering of the appointment table view to the next 30 days.
     * @param actionEvent from the Next 30 Buttons radial
     */
    public void onThisMonthAppointmentsRadial(ActionEvent actionEvent) {
        try {
            int daysAhead = 30;
            appointmentList.clear();
            appointmentList.addAll(AppointmentsDaoImpl.getAppointments(daysAhead));
            appointmentTableView.setItems(appointmentList);
        } catch (Exception e) {
            Logger.getLogger(AppointmentView.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * This handles the filtering of the appointment table view to this week.
     * @param actionEvent from the Next 7 Buttons radial
     */
    public void onThisWeekAppointmentsRadial(ActionEvent actionEvent) {
        try {
            int daysAhead = 7;
            appointmentList.clear();
            appointmentList.addAll(AppointmentsDaoImpl.getAppointments(daysAhead));
            appointmentTableView.setItems(appointmentList);
        } catch (Exception e) {
            Logger.getLogger(AppointmentView.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * This handles the loading of the edit appointment fields of the selected item in the table.
     * @param actionEvent from the edit appointment button click
     */
    public void onEditAppointmentButton(ActionEvent actionEvent) {
        System.out.println(getClass().getName() + " :Edit Appointment Button clicked.");

        Appointments selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();

        editAppointmentIdText.setText(String.valueOf(selectedAppointment.getID()));
        editAppointmentTitleText.setText(selectedAppointment.getTitle());
        editAppointmentDescriptionText.setText(selectedAppointment.getDescription());
        editAppointmentLocationText.setText(selectedAppointment.getLocation());
        editAppointmentContactComboBox.setValue(ContactsDaoImpl.getContact(selectedAppointment.getContactId()));
        editAppointmentTypeComboBox.setValue(selectedAppointment.getType());
        editAppointmentStartDate.setValue(selectedAppointment.getStartDateTime().toLocalDate());
        editAppointmentStartTime.setValue(selectedAppointment.getStartDateTime().toLocalTime());
        editAppointmentEndDate.setValue(selectedAppointment.getEndDateTime().toLocalDate());
        editAppointmentEndTime.setValue(selectedAppointment.getEndDateTime().toLocalTime());
        editAppointmentCustomerCombo.setValue(CustomersDaoImpl.getCustomer(selectedAppointment.getCustomerId()));
        editAppointmentUserCombo.setValue(UserDaoImpl.getUser(selectedAppointment.getUserId()));
    }

    /**
     * This handles the
     * @param actionEvent from the Delete Appointment button click
     */
    public void onDeleteAppointmentButton(ActionEvent actionEvent) throws SQLException {
        System.out.println(getClass().getName() + " :Delete Appointment Button clicked.");
        try {
            Appointments selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
            AppointmentsDaoImpl.delete(selectedAppointment.getID());
            appointmentTableView.setItems(AppointmentsDaoImpl.getAllAppointments());
            String errorMessage = String.format("Appointment %s %s has been " +
                    "deleted" +
                    ".", selectedAppointment.getID(), selectedAppointment.getType());
            errorMessageLbl.setText(errorMessage);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * This handles the loading of the menu page due to the back button click.
     * @param actionEvent from the back button click.
     * @throws IOException
     */
    public void onBackButton(ActionEvent actionEvent) throws IOException {
        System.out.println(getClass().getName() + " :Back Button clicked.");

        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/MenuView.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * THis handles the closing of the app.
     * @param actionEvent from the exit button click
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
     * This handles the date and time collision check for the edit appointment fields then
     * passes the data to the DAO for appointment creation.
     * @param actionEvent from the Save Changes button click
     */
    public void onEditAppointmentSaveChangesButton(ActionEvent actionEvent) {
        System.out.println(getClass().getName() + " :Save Changes Button clicked.");

        Save : try {
            int id = Integer.parseInt(editAppointmentIdText.getText());
            Appointments selectedAppointment = AppointmentsDaoImpl.getAppointment(id);
            String title = editAppointmentTitleText.getText();
            String description = editAppointmentDescriptionText.getText();
            String location = editAppointmentLocationText.getText();

//            int contactId =
//                    Integer.parseInt(editAppointmentContactComboBox.getId());
            Contacts selectedContact = editAppointmentContactComboBox.getSelectionModel().getSelectedItem();
            int contactId = selectedContact.getId();
            String type = editAppointmentTypeComboBox.getSelectionModel().getSelectedItem();

            System.out.println(editAppointmentTypeComboBox.getSelectionModel().getSelectedItem());

            LocalDate localStartDate = editAppointmentStartDate.getValue();
            LocalTime localStartTime = editAppointmentStartTime.getSelectionModel().getSelectedItem();
            LocalDateTime localStartDateTime = LocalDateTime.of(localStartDate, localStartTime);
            // The VM version is handling the time conversion on its own.
            // Therefore this is not needed.
//            ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
//            ZonedDateTime localStartZDT = ZonedDateTime.of(localStartDateTime, localZoneId);
//            ZoneId utcZoneId = ZoneId.of("UTC");
//            Instant localStartToUtcInstance = localStartZDT.toInstant();
//            ZonedDateTime localStartDateTimeToUtcZDT = localStartZDT.withZoneSameInstant(utcZoneId);
//            LocalDateTime utcStartDateTime = localStartDateTimeToUtcZDT.toLocalDateTime();
//            System.out.println("Local Start: " + localStartDateTime + " to UTC: " + utcStartDateTime);

            LocalDate localEndDate = editAppointmentEndDate.getValue();
            LocalTime localEndTime = editAppointmentEndTime.getSelectionModel().getSelectedItem();
            LocalDateTime localEndDateTime = LocalDateTime.of(localEndDate, localEndTime);
            // The VM version is handling the time conversion on its own.
            // Therefore this is not needed.
//            ZonedDateTime localEndZDT = ZonedDateTime.of(localEndDateTime, localZoneId);
//            ZonedDateTime localEndDateToUtcZDT = localEndZDT.withZoneSameInstant(utcZoneId);
//            LocalDateTime utcEndDateTime = localEndDateToUtcZDT.toLocalDateTime();
//            System.out.println("Local End: " + localEndDateTime + " to UTC: " + utcEndDateTime);

            String createdBy = selectedAppointment.getCreatedBy();

            LocalDateTime localUpdateDateTime = LocalDateTime.now();
            // The VM version is handling the time conversion on its own.
            // Therefore this is not needed.
//            ZonedDateTime localUpdateZDT = ZonedDateTime.of(localUpdateDateTime, localZoneId);
//            ZonedDateTime localUpdateDateToUtcZDT = localUpdateZDT.withZoneSameInstant(utcZoneId);
//            LocalDateTime utcUpdateDateTime = localUpdateDateToUtcZDT.toLocalDateTime();
//            System.out.println("Local Update: " + localUpdateDateTime + " to UTC: " + utcUpdateDateTime);

            LocalDateTime createDateTime = selectedAppointment.getCreateDateTime();

            String lastUpdatedBy = "me";
            Customers selectedCustomer = editAppointmentCustomerCombo.getSelectionModel().getSelectedItem();
            Users selectedUser = editAppointmentUserCombo.getSelectionModel().getSelectedItem();

            int customerId = selectedCustomer.getCustomerId();
            int userId = selectedUser.getId();

            LocalDateTime nowLDT = LocalDateTime.now();

            if (localStartDateTime.isBefore(LocalDateTime.now())) {
                errorMessageLbl.setText("Your start time is in the past.");
                break Save;
            }
            else if (localStartDateTime.isAfter(localEndDateTime)) {
                errorMessageLbl.setText("Your submitted End time is before your Start time.");
                break Save;
            }
            ObservableList<Appointments> appointmentList2 = AppointmentsDaoImpl.getAllAppointments();

            for (Appointments a : appointmentList2) {
                LocalDateTime aStart = a.getStartDateTime();
                LocalDateTime aEnd = a.getEndDateTime();

                if (a.getID() != id) {
                    if (customerId == a.getCustomerId()) {
                        if ( localStartDateTime.isEqual(aStart) && localStartDateTime.isEqual(aEnd)) {
                            errorMessageLbl.setText("Your appt matches start anf end time with " + a.getID() + " for that customer.");
                            break Save;
                        }
                        else if (localStartDateTime.isBefore(aStart) && localEndDateTime.isAfter(aStart)) {
                            errorMessageLbl.setText("Your end time lands in appt " + a.getID() + " for that customer.");
                            break Save;
                        }
                        else if(localStartDateTime.isAfter(aStart) && localStartDateTime.isBefore(aEnd)){
                            errorMessageLbl.setText("Your start time lands in appt " + a.getID() + " for that customer.");
                            break Save;
                        }
                        else if(localStartDateTime.isAfter(aStart) && localStartDateTime.isBefore(aEnd)){
                            errorMessageLbl.setText("Your times is enclosed by appt " + a.getID() + " for that customer.");
                            break Save;
                        }
                        else if (localStartDateTime.isBefore(aStart)  &&  localEndDateTime.isEqual(aEnd))
                        {
                            errorMessageLbl.setText("Your times encloses appt " + a.getID() + " for that customer.");
                            break Save;
                        }
                    }
                }
            }
//           If it passes all the collision checks, it will then complete the appointment addition.
//            System.out.println("No collisions found " +
//                    "in the scheduling. Creating " +
//                    "Appointment.");
//            System.out.println(id + " " + title + " " + description + " " +
//                    location + " " + type + " " + utcStartDateTime + " " + utcEndDateTime + " " +
//                    createDateTime + " " + createdBy + " " + utcUpdateDateTime + " " +
//                    lastUpdatedBy + " " +
//                    customerId + " " + userId + " " + contactId);
//
            // The VM version is handling the time conversion on its own.
            // Therefore I need to pass the local localdatetimes.
//            AppointmentsDaoImpl.update(id, title, description,
//                    location, type, utcStartDateTime, utcEndDateTime,
//                    createDateTime, createdBy, utcUpdateDateTime,
//                    lastUpdatedBy,
//                    customerId, userId, contactId);

            AppointmentsDaoImpl.update(id, title, description,
                    location, type, localStartDateTime, localEndDateTime,
                    createDateTime, createdBy, localUpdateDateTime,
                    lastUpdatedBy,
                    customerId, userId, contactId);


            appointmentTableView.setItems(AppointmentsDaoImpl.getAllAppointments());
            errorMessageLbl.setText("Appointment " + id + " has been updated.");

            editAppointmentIdText.clear();
            editAppointmentTitleText.clear();
            editAppointmentDescriptionText.clear();
            editAppointmentLocationText.clear();
            editAppointmentContactComboBox.valueProperty().set(null);
            editAppointmentTypeComboBox.valueProperty().set(null);
            editAppointmentStartTime.valueProperty().set(null);
            editAppointmentEndTime.valueProperty().set(null);
            editAppointmentCustomerCombo.valueProperty().set(null);
            editAppointmentUserCombo.valueProperty().set(null);
        }
        catch(NumberFormatException | SQLException e) {
            System.out.println("Wrong values detected!");
            System.out.print("Exception: " + e);
            System.out.print("Exception: " + e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This clears the edit appointment fields.
     * @param actionEvent from the Cancel Changes button click
     */
    public void onEditAppointmentCancelChangesButton(ActionEvent actionEvent) {
        System.out.println(getClass().getName() + " :Cancel Changes Button clicked.");
        editAppointmentIdText.clear();
        editAppointmentTitleText.clear();
        editAppointmentDescriptionText.clear();
        editAppointmentLocationText.clear();
        editAppointmentContactComboBox.valueProperty().set(null);
        editAppointmentTypeComboBox.valueProperty().set(null);
        editAppointmentStartTime.valueProperty().set(null);
        editAppointmentEndTime.valueProperty().set(null);
        editAppointmentCustomerCombo.valueProperty().set(null);
        editAppointmentUserCombo.valueProperty().set(null);

        errorMessageLbl.setText("");
    }

    /**
     * This handles appointment date and time collision then passes the data to create the Appointment.
     * @param actionEvent from the Save New Appointment button click
     */
    public void onNewAppointmentSaveButton(ActionEvent actionEvent) {
        System.out.println(getClass().getName() + " :Save New Appointment Button clicked.");

        Save : try {
            // int id = Integer.parseInt(newAppointmentIdText.getText());
            String title = newAppointmentTitleText.getText();
            System.out.println(title);
            String description = newAppointmentDescriptionText.getText();
            System.out.println(description);
            String location = newAppointmentLocationText.getText();
            System.out.println(location);
//            int contactId =
//                    Integer.parseInt(newAppointmentContactComboBox.getId());
            Contacts selectedContact =
                    newAppointmentContactComboBox.getSelectionModel().getSelectedItem();
            int contactId = selectedContact.getId();
            String type = newAppointmentTypeComboBox.getSelectionModel().getSelectedItem();
            System.out.println(type);

            LocalDate localStartDate = newAppointmentStartDate.getValue();
            LocalTime localStartTime = newAppointmentStartTime.getSelectionModel().getSelectedItem();
            LocalDateTime localStartDateTime = LocalDateTime.of(localStartDate, localStartTime);
//
//            The VM version automatically handles the time change from local to server time.
//            Therefore this is redundant and I need to pass the local localdatetimes.

//            ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
//            ZonedDateTime localStartZDT = ZonedDateTime.of(localStartDate, localStartTime, localZoneId);
//            ZoneId utcZoneId = ZoneId.of("UTC");
//            Instant localToUtcInstance = localStartZDT.toInstant();
//            ZonedDateTime localStartDateToUtcZDT = localStartZDT.withZoneSameInstant(utcZoneId);
////            LocalTime utcStartTimeFromLocal = localStartToUtcZDT.toInstant
////            (UNSURE);
//            LocalDateTime utcStartDateTime = localStartDateToUtcZDT.toLocalDateTime();
//
//            System.out.println("Local Start: " + localStartDateTime + " to " +
//                    "UTC: " + utcStartDateTime);


            LocalDate localEndDate = newAppointmentEndDate.getValue();
            LocalTime localEndTime = newAppointmentEndTime.getSelectionModel().getSelectedItem();
            LocalDateTime localEndDateTime = LocalDateTime.of(localEndDate, localEndTime);

//            The VM version automatically handles the time change from local to server time.
//            Therefore this is redundant and I need to pass the local localdatetimes.

//            ZonedDateTime localEndZDT = ZonedDateTime.of(localEndDate, localEndTime, localZoneId);
//            ZonedDateTime localEndDateToUtcZDT = localEndZDT.withZoneSameInstant(utcZoneId);
//            LocalDateTime utcEndDateTime = localEndDateToUtcZDT.toLocalDateTime();
//            System.out.println("Local End: " + localEndDateTime + " to UTC: " + utcEndDateTime);


            LocalDateTime localCreateDateTime = LocalDateTime.now();
//            The VM version automatically handles the time change from local to server time.
//            Therefore this is redundant and I need to pass the local localdatetimes.
//            ZonedDateTime localCreateZDT = ZonedDateTime.of(localCreateDateTime, localZoneId);
//            ZonedDateTime localCreateDateToUtcZDT = localCreateZDT.withZoneSameInstant(utcZoneId);
//            LocalDateTime utcCreateDateTime = localCreateDateToUtcZDT.toLocalDateTime();
//            System.out.println("Local Create: " + localCreateDateTime + " to UTC: " + utcCreateDateTime);

            String createdBy = "User";

            LocalDateTime localUpdateDateTime = LocalDateTime.now();
//            The VM version automatically handles the time change from local to server time.
//            Therefore this is redundant and I need to pass the local localdatetimes.
//            ZonedDateTime localUpdateZDT = ZonedDateTime.of(localUpdateDateTime, localZoneId);
//            ZonedDateTime localUpdateDateToUtcZDT = localUpdateZDT.withZoneSameInstant(utcZoneId);
//            LocalDateTime utcUpdateDateTime = localUpdateDateToUtcZDT.toLocalDateTime();
//            System.out.println("Local Update: " + localUpdateDateTime + " to UTC: " + utcUpdateDateTime);

            String lastUpdatedBy = "User";
            Customers selectedCustomer = newAppointmentCustomerCombo.getSelectionModel().getSelectedItem();
            Users selectedUser = newAppointmentUserCombo.getSelectionModel().getSelectedItem();

            int customerId = selectedCustomer.getCustomerId();
            int userId = selectedUser.getId();

            if (localStartDateTime.isBefore(LocalDateTime.now())) {
                errorMessageLbl.setText("Your start time is in the past.");
                break Save;
            }
            else if (localStartDateTime.isAfter(localEndDateTime)) {
                errorMessageLbl.setText("Your submitted End time is before your Start time.");
                break Save;
            }

            ObservableList<Appointments> appointmentList2 = AppointmentsDaoImpl.getAllAppointments();

            for (Appointments a : appointmentList2) {
                LocalDateTime aStart = a.getStartDateTime();
                LocalDateTime aEnd = a.getEndDateTime();

                if (customerId == a.getCustomerId()) {
                    if ( localStartDateTime.isEqual(aStart) && localStartDateTime.isEqual(aEnd)) {
                        errorMessageLbl.setText("Your appt matches start anf end time with " + a.getID() + " for that customer.");
                        break Save;
                    }
                    else if (localStartDateTime.isBefore(aStart) && localEndDateTime.isAfter(aStart)) {
                        errorMessageLbl.setText("Your end time lands in appt " + a.getID() + " for that customer.");
                        break Save;
                    }
                    else if(localStartDateTime.isAfter(aStart) && localStartDateTime.isBefore(aEnd)){
                        errorMessageLbl.setText("Your start time lands in appt " + a.getID() + " for that customer.");
                        break Save;
                    }
                    else if(localStartDateTime.isAfter(aStart) && localStartDateTime.isBefore(aEnd)){
                        errorMessageLbl.setText("Your times is enclosed by appt " + a.getID() + " for that customer.");
                        break Save;
                    }
                    else if (localStartDateTime.isBefore(aStart)  &&  localEndDateTime.isEqual(aEnd))
                    {
                        errorMessageLbl.setText("Your times encloses appt " + a.getID() + " for that customer.");
                        break Save;
                    }
                }
            }

//            System.out.println("No collision found, " +
//                    "adding appointment.");
//            System.out.println(" " + title + " " + description + " " +
//                    location + " " + type + " " + utcStartDateTime + " " + utcEndDateTime + " " +
//                    utcStartDateTime + " " + createdBy + " " + utcUpdateDateTime + " " +
//                    lastUpdatedBy + " " +
//                    customerId + " " + userId + " " + contactId);

            // The VM version automatically handles the time change from local to server time.
            // Therefore this is redundant and I need to pass the local localdatetimes.
//            AppointmentsDaoImpl.insert(title, description,
//                    location, type, utcStartDateTime, utcEndDateTime,
//                    utcStartDateTime, createdBy, utcUpdateDateTime,
//                    lastUpdatedBy,
//                    customerId, userId, contactId);


            AppointmentsDaoImpl.insert(title, description,
                    location, type, localStartDateTime, localEndDateTime,
                    localCreateDateTime, createdBy, localUpdateDateTime,
                    lastUpdatedBy,
                    customerId, userId, contactId);

            appointmentTableView.setItems(AppointmentsDaoImpl.getAllAppointments());
            errorMessageLbl.setText("New appointment saved.");

            newAppointmentIdText.clear();
            newAppointmentTitleText.clear();
            newAppointmentDescriptionText.clear();
            newAppointmentLocationText.clear();
            newAppointmentContactComboBox.valueProperty().set(null);
            newAppointmentTypeComboBox.valueProperty().set(null);
            newAppointmentStartTime.valueProperty().set(null);
            newAppointmentEndTime.valueProperty().set(null);
            newAppointmentStartDate.getEditor().clear();
            newAppointmentEndDate.getEditor().clear();
            newAppointmentCustomerCombo.valueProperty().set(null);
            newAppointmentUserCombo.valueProperty().set(null);

        }
        catch(NullPointerException | NumberFormatException | SQLException e) {
            System.out.println("Wrong values detected!");
            System.out.print("Exception: " + e);
            System.out.print("Exception: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This handles the New Appointment field clearing.
     * @param actionEvent from the clear button click
     */
    public void onNewAppointmentClearButton(ActionEvent actionEvent) {
        System.out.println(getClass().getName() + " :Clear Button clicked.");

        newAppointmentIdText.clear();
        newAppointmentTitleText.clear();
        newAppointmentDescriptionText.clear();
        newAppointmentLocationText.clear();
        newAppointmentContactComboBox.valueProperty().set(null);
        newAppointmentTypeComboBox.valueProperty().set(null);
        newAppointmentStartTime.valueProperty().set(null);
        newAppointmentEndTime.valueProperty().set(null);
        newAppointmentCustomerCombo.valueProperty().set(null);
        newAppointmentUserCombo.valueProperty().set(null);

        errorMessageLbl.setText("");
    }
}

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
 *
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
//    ObservableList<Contacts> contactList =
//            ContactsDaoImpl.getAllContacts();

    @Override
    /**
     *
     */
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

        ObservableList<String> typeOptions =
                FXCollections.observableArrayList();
        typeOptions.add("De-Briefing");
        typeOptions.add("Planning Session");
        typeOptions.add("One-on-One");
//        String[] typeOptions = {"Coffee House", "Office", "Phone", "Virtual"};
        editAppointmentTypeComboBox.setItems(typeOptions);
        newAppointmentTypeComboBox.setItems(typeOptions);

//        LocalTime start = LocalTime.of(6, 00);
//        LocalTime end = LocalTime.of(17, 00);
//FIXME: These time checks are not wokring correctly.
        LocalDate easternDate = LocalDate.now();
        LocalTime easternStartTime = LocalTime.of(8,0);
        ZoneId easternZoneId = ZoneId.of("America/New_York");
        ZonedDateTime easternStartZDT = ZonedDateTime.of(easternDate, easternStartTime, easternZoneId);
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        Instant easternToLocalInstance = easternStartZDT.toInstant();
        ZonedDateTime easternStartToLocalZDT = easternStartZDT.withZoneSameInstant(localZoneId);
        LocalTime localStartTimeFromEastern = easternStartToLocalZDT.toLocalTime();
        LocalTime start = easternStartToLocalZDT.toLocalTime();

        LocalTime easternEndTime = LocalTime.of(20,0);
        ZonedDateTime easternEndZDT = ZonedDateTime.of(easternDate, easternEndTime, easternZoneId);

        Instant easternToLocalInstanceEnd = easternEndZDT.toInstant();
        ZonedDateTime easternEndToLocalZDT = easternEndZDT.withZoneSameInstant(localZoneId);
        LocalTime localEndTimeFromEastern = easternEndToLocalZDT.toLocalTime();
        LocalTime end = easternEndToLocalZDT.toLocalTime();

        while(start.isBefore(end.minusMinutes(15))) {
            editAppointmentStartTime.getItems().add(start);
            editAppointmentEndTime.getItems().add(start.plusMinutes(30));
            newAppointmentStartTime.getItems().add(start);
            newAppointmentEndTime.getItems().add(start.plusMinutes(30));

            start = start.plusMinutes(30);
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
            Logger.getLogger(AppointmentView.class.getName()).log(Level.SEVERE,
                    null, e);
        }
    }

    /**
     *
     * @param actionEvent
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
     *
     * @param actionEvent
     */
    public void onThisMonthAppointmentsRadial(ActionEvent actionEvent) {
        try {
            int daysAhead = 30;
            appointmentList.clear();
            appointmentList.addAll(AppointmentsDaoImpl.getAppointments(daysAhead));
            appointmentTableView.setItems(appointmentList);
        } catch (Exception e) {
            Logger.getLogger(AppointmentView.class.getName()).log(Level.SEVERE,
                    null, e);
        }
    }

    /**
     *
     * @param actionEvent
     */
    public void onThisWeekAppointmentsRadial(ActionEvent actionEvent) {
        try {
            int daysAhead = 7;
            appointmentList.clear();
            appointmentList.addAll(AppointmentsDaoImpl.getAppointments(daysAhead));
            appointmentTableView.setItems(appointmentList);
        } catch (Exception e) {
            Logger.getLogger(AppointmentView.class.getName()).log(Level.SEVERE,
                    null, e);
        }
    }

    /**
     *
     * @param actionEvent
     */
    public void onEditAppointmentButton(ActionEvent actionEvent) {
        System.out.println(getClass().getName() + " :Edit Appointment Button clicked.");

        Appointments selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();

        editAppointmentIdText.setText(String.valueOf(selectedAppointment.getID()));
        editAppointmentTitleText.setText(selectedAppointment.getTitle());
        editAppointmentDescriptionText.setText(selectedAppointment.getDescription());
        editAppointmentLocationText.setText(selectedAppointment.getLocation());
//        Contacts selectedAppointmentContact =
//                editAppointmentContactComboBox.getSelectionModel().getSelectedItem();
//        System.out.println(selectedAppointmentContact);
//        editAppointmentContactComboBox.setValue(selectedAppointmentContact);
//        ContactsDaoImpl.getContact(selectedAppointment.getContactId());
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
     *
     * @param actionEvent
     */
    public void onDeleteAppointmentButton(ActionEvent actionEvent) throws SQLException {
        System.out.println(getClass().getName() + " :Delete Appointment Button clicked.");
        try {
            Appointments selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
            AppointmentsDaoImpl.delete(selectedAppointment.getID());
            appointmentTableView.setItems(AppointmentsDaoImpl.getAllAppointments());
            String errorMessage = String.format("Appointment %s has been " +
                    "deleted" +
                    ".", selectedAppointment.getID());
            errorMessageLbl.setText(errorMessage);
            errorMessageLbl.wait(15000);
            errorMessageLbl.setText("");
        } catch (Exception throwables) {
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
            ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
            ZonedDateTime localStartZDT = ZonedDateTime.of(localStartDateTime, localZoneId);
            ZoneId utcZoneId = ZoneId.of("UTC");
            Instant localStartToUtcInstance = localStartZDT.toInstant();
            ZonedDateTime localStartDateTimeToUtcZDT = localStartZDT.withZoneSameInstant(utcZoneId);
            LocalDateTime utcStartDateTime = localStartDateTimeToUtcZDT.toLocalDateTime();
            System.out.println("Local Start: " + localStartDateTime + " to UTC: " + utcStartDateTime);

            LocalDate localEndDate = editAppointmentEndDate.getValue();
            LocalTime localEndTime = editAppointmentEndTime.getSelectionModel().getSelectedItem();
            LocalDateTime localEndDateTime = LocalDateTime.of(localEndDate, localEndTime);
            ZonedDateTime localEndZDT = ZonedDateTime.of(localEndDateTime, localZoneId);
            ZonedDateTime localEndDateToUtcZDT = localEndZDT.withZoneSameInstant(utcZoneId);
            LocalDateTime utcEndDateTime = localEndDateToUtcZDT.toLocalDateTime();
            System.out.println("Local End: " + localEndDateTime + " to UTC: " + utcEndDateTime);

            String createdBy = selectedAppointment.getCreatedBy();

            LocalDateTime localUpdateDateTime = LocalDateTime.now();
            ZonedDateTime localUpdateZDT = ZonedDateTime.of(localUpdateDateTime, localZoneId);
            ZonedDateTime localUpdateDateToUtcZDT = localUpdateZDT.withZoneSameInstant(utcZoneId);
            LocalDateTime utcUpdateDateTime = localUpdateDateToUtcZDT.toLocalDateTime();
            System.out.println("Local Update: " + localUpdateDateTime + " to UTC: " + utcUpdateDateTime);

            LocalDateTime createDateTime = selectedAppointment.getCreateDateTime();

            String lastUpdatedBy = "me";
            Customers selectedCustomer = editAppointmentCustomerCombo.getSelectionModel().getSelectedItem();
            Users selectedUser = editAppointmentUserCombo.getSelectionModel().getSelectedItem();

            int customerId = selectedCustomer.getCustomerId();
            int userId = selectedUser.getId();

            for (Appointments a : appointmentList) {
                LocalDateTime aStart = a.getStartDateTime();
                LocalDateTime aEnd = a.getEndDateTime();
//                System.out.println("In the for loop");
//                System.out.println("Appt A start utc time: " + aStart);
//                System.out.println("Appt A end utc time: " + aEnd);
//                System.out.println("Appt entered start utc time: " + utcStartDateTime);
//                System.out.println("Appt entered start utc time: " + utcEndDateTime);

                if(a.getID() != id) {
                    if(customerId == a.getCustomerId()) {
                        if(localStartDateTime.isBefore(LocalDateTime.now())){
                            errorMessageLbl.setText("Your start time is in the past.");
                            break Save;
                        }
                        else if(localStartDateTime.isAfter(localEndDateTime)){
                            errorMessageLbl.setText("Your submitted End time is before your Start time.");
                            break Save;
                        }
                        else if(((localStartDateTime.isAfter(aStart)) || localStartDateTime.isEqual(aStart)) && localStartDateTime.isBefore(aEnd)) {
                            errorMessageLbl.setText("Your start time lands in appt " + a.getID() + " for that customer.");
                            break Save;
                        }
                        else if(localEndDateTime.isAfter(aStart) && (localEndDateTime.isBefore(aEnd) || localEndDateTime.isEqual(aEnd))) {
                            errorMessageLbl.setText("Your end time lands in appt " + a.getID() + " for that customer.");
                            break Save;
                        }
                        else if((localStartDateTime.isBefore(aStart) || localStartDateTime.isEqual(aStart)) && (localEndDateTime.isAfter(aEnd) || localEndDateTime.isEqual(aEnd))) {
                            errorMessageLbl.setText("Your times enclose appt " + a.getID() + " for that customer.");
                            break Save;
                        }
                    }
                }
            }
//                      If it passes all the collision checks, it will then
//                      complete the appointment addition.
            System.out.println("No collisions found " +
                    "in the scheduling. Creating " +
                    "Appointment.");
            System.out.println(id + " " + title + " " + description + " " +
                    location + " " + type + " " + utcStartDateTime + " " + utcEndDateTime + " " +
                    createDateTime + " " + createdBy + " " + utcUpdateDateTime + " " +
                    lastUpdatedBy + " " +
                    customerId + " " + userId + " " + contactId);
            AppointmentsDaoImpl.update(id, title, description,
                    location, type, utcStartDateTime, utcEndDateTime,
                    createDateTime, createdBy, utcUpdateDateTime,
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
            editAppointmentStartDate.getEditor().clear();
            editAppointmentEndDate.getEditor().clear();
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
     *
     * @param actionEvent
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
        editAppointmentStartDate.getEditor().clear();
        editAppointmentEndDate.getEditor().clear();
        editAppointmentCustomerCombo.valueProperty().set(null);
        editAppointmentUserCombo.valueProperty().set(null);

        errorMessageLbl.setText("");
    }

    /**
     *
     * @param actionEvent
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
            ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
            ZonedDateTime localStartZDT = ZonedDateTime.of(localStartDate, localStartTime, localZoneId);
            ZoneId utcZoneId = ZoneId.of("UTC");
            Instant localToUtcInstance = localStartZDT.toInstant();
            ZonedDateTime localStartDateToUtcZDT = localStartZDT.withZoneSameInstant(utcZoneId);
//            LocalTime utcStartTimeFromLocal = localStartToUtcZDT.toInstant
//            (UNSURE);
            LocalDateTime utcStartDateTime = localStartDateToUtcZDT.toLocalDateTime();

            System.out.println("Local Start: " + localStartDateTime + " to " +
                    "UTC: " + utcStartDateTime);


            LocalDate localEndDate = newAppointmentEndDate.getValue();
            LocalTime localEndTime = newAppointmentEndTime.getSelectionModel().getSelectedItem();
            LocalDateTime localEndDateTime = LocalDateTime.of(localEndDate, localEndTime);
            ZonedDateTime localEndZDT = ZonedDateTime.of(localEndDate, localEndTime, localZoneId);
            ZonedDateTime localEndDateToUtcZDT = localEndZDT.withZoneSameInstant(utcZoneId);
            LocalDateTime utcEndDateTime = localEndDateToUtcZDT.toLocalDateTime();
            System.out.println("Local End: " + localEndDateTime + " to UTC: " + utcEndDateTime);


            LocalDateTime localCreateDateTime = LocalDateTime.now();
            ZonedDateTime localCreateZDT = ZonedDateTime.of(localCreateDateTime, localZoneId);
            ZonedDateTime localCreateDateToUtcZDT = localCreateZDT.withZoneSameInstant(utcZoneId);
            LocalDateTime utcCreateDateTime = localCreateDateToUtcZDT.toLocalDateTime();
            System.out.println("Local Create: " + localCreateDateTime + " to UTC: " + utcCreateDateTime);

            String createdBy = "User";

            LocalDateTime localUpdateDateTime = LocalDateTime.now();
            ZonedDateTime localUpdateZDT = ZonedDateTime.of(localUpdateDateTime, localZoneId);
            ZonedDateTime localUpdateDateToUtcZDT = localUpdateZDT.withZoneSameInstant(utcZoneId);
            LocalDateTime utcUpdateDateTime = localUpdateDateToUtcZDT.toLocalDateTime();
            System.out.println("Local Update: " + localUpdateDateTime + " to UTC: " + utcUpdateDateTime);

            String lastUpdatedBy = "User";
            Customers selectedCustomer = newAppointmentCustomerCombo.getSelectionModel().getSelectedItem();
            Users selectedUser = newAppointmentUserCombo.getSelectionModel().getSelectedItem();

            int customerId = selectedCustomer.getCustomerId();
            int userId = selectedUser.getId();

            for (Appointments a : appointmentList) {
                LocalDateTime aStart = a.getStartDateTime();
                LocalDateTime aEnd = a.getEndDateTime();
//                System.out.println("In the for loop");
//                System.out.println("Appt A start utc time: " + aStart);
//                System.out.println("Appt A end utc time: " + aEnd);
//                System.out.println("Appt entered start utc time: " + utcStartDateTime);
//                System.out.println("Appt entered start utc time: " + utcEndDateTime);

                if(customerId == a.getCustomerId()) {
                    if(localStartDateTime.isBefore(LocalDateTime.now())){
                        errorMessageLbl.setText("Your start time is in the past.");
                        break Save;
                    }
                    else if(localStartDateTime.isAfter(localEndDateTime)){
                        errorMessageLbl.setText("Your submitted End time is before your Start time.");
                        break Save;
                    }
                    else if(((localStartDateTime.isAfter(aStart)) || localStartDateTime.isEqual(aStart)) && localStartDateTime.isBefore(aEnd)) {
                        errorMessageLbl.setText("Your start time lands in appt " + a.getID() + " for that customer.");
                        break Save;
                    }
                    else if(localEndDateTime.isAfter(aStart) && (localEndDateTime.isBefore(aEnd) || localEndDateTime.isEqual(aEnd))) {
                        errorMessageLbl.setText("Your end time lands in appt " + a.getID() + " for that customer.");
                        break Save;
                    }
                    else if((localStartDateTime.isBefore(aStart) || localStartDateTime.isEqual(aStart)) && (localEndDateTime.isAfter(aEnd) || localEndDateTime.isEqual(aEnd))) {
                        errorMessageLbl.setText("Your times enclose appt " + a.getID() + " for that customer.");
                        break Save;
                    }
                }
            }

            System.out.println("No collision found, " +
                    "adding appointment.");
            System.out.println(" " + title + " " + description + " " +
                    location + " " + type + " " + utcStartDateTime + " " + utcEndDateTime + " " +
                    utcStartDateTime + " " + createdBy + " " + utcUpdateDateTime + " " +
                    lastUpdatedBy + " " +
                    customerId + " " + userId + " " + contactId);

            AppointmentsDaoImpl.insert(title, description,
                    location, type, utcStartDateTime, utcEndDateTime,
                    utcStartDateTime, createdBy, utcUpdateDateTime,
                    lastUpdatedBy,
                    customerId, userId, contactId);

            appointmentTableView.setItems(AppointmentsDaoImpl.getAllAppointments());
            errorMessageLbl.setText("New appointment saved.");

            //         Muting this out for now.
//            newAppointmentIdText.clear();
//            newAppointmentTitleText.clear();
//            newAppointmentDescriptionText.clear();
//            newAppointmentLocationText.clear();
//            newAppointmentContactComboBox.valueProperty().set(null);
//            newAppointmentCustomerIdText.clear();
//            newAppointmentUserIdText.clear();
//            newAppointmentTypeComboBox.valueProperty().set(null);
//            newAppointmentStartTime.valueProperty().set(null);
//            newAppointmentEndTime.valueProperty().set(null);
//            newAppointmentStartDate.getEditor().clear();
//            newAppointmentEndDate.getEditor().clear();
//            newAppointmentCustomerCombo.valueProperty().set(null);
//            newAppointmentUserCombo.valueProperty().set(null);

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
     *
     * @param actionEvent
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
        newAppointmentStartDate.getEditor().clear();
        newAppointmentEndDate.getEditor().clear();
        newAppointmentCustomerCombo.valueProperty().set(null);
        newAppointmentUserCombo.valueProperty().set(null);

        errorMessageLbl.setText("");
    }
}

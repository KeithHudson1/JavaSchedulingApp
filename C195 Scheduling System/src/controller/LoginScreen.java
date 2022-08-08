package controller;

import DAO.AppointmentsDaoImpl;
import DAO.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.*;
import javafx.scene.*;
import model.Appointments;

import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * This is the controller for the initial login screen of the app.
 * This handles the user credential check, location and language determination.
 */
public class LoginScreen implements Initializable {
    public TextField userNameTextbox;
    public TextField passwordTextbox;
    public Button loginButton;
    public Button exitButton;
    public Label userNameLabel;
    public Label passwordLabel;
    public Label schedulingAppLbl;
    public Label errorMessagePane;
    public Label sysZoneidDetectedLbl;
    public TextField systemZoneIdTextField;

    ObservableList<Locale> languageList = FXCollections.observableArrayList(Locale.GERMAN, Locale.ENGLISH,
                    new Locale("es"), Locale.FRENCH);

    /**
     * This initializes the Login Screen view, tables and combo boxes.
     * @param url  location
     * @param resourceBundle resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(getClass().getName() + "in initialize.");

        String currentLocale = Locale.getDefault().getLanguage().toString();
        String currentZoneId = ZoneId.systemDefault().toString();

        if (currentLocale.equals("de") || currentLocale.equals("en") || currentLocale.equals("en_US") || currentLocale.equals("es") || currentLocale.equals("fr")) {
            ResourceBundle rb = ResourceBundle.getBundle("main/nat", Locale.getDefault());


            sysZoneidDetectedLbl.setText(rb.getString("System Zone Id"));
            systemZoneIdTextField.setText(currentZoneId);
            loginButton.setText(rb.getString("Login"));
            exitButton.setText(rb.getString("Exit"));
            userNameLabel.setText(rb.getString("Username"));
            passwordLabel.setText(rb.getString("Password"));
            schedulingAppLbl.setText(rb.getString("Welcome to the Scheduling App"));
        }
    }

    /**
     *  This handles the login credential check and error message handling.
     * @param actionEvent from the Login button click.
     * @throws IOException
     */
    public void onLoginButton(ActionEvent actionEvent) throws IOException, SQLException {
        System.out.println(getClass().getName() + " :Login button clicked");

        try {
            String userName = userNameTextbox.getText();
            String password = passwordTextbox.getText();

            String sql = "SELECT * FROM users WHERE User_Name=? AND Password=?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1,userName);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            ResourceBundle rb = ResourceBundle.getBundle("main/nat", Locale.getDefault());

            String filename = "login_activity.txt";

            File file = new File(filename);
            FileWriter fwriter = new FileWriter(filename, true);
            PrintWriter outputFile = new PrintWriter(fwriter);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String nowLDTString = LocalDateTime.now().format(formatter);

            String currentZoneId = ZoneId.systemDefault().toString();

            LocalDateTime nowLDT = LocalDateTime.now();
            ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
            ZonedDateTime localNowZDT = ZonedDateTime.of(nowLDT, localZoneId);
            ZoneId utcZoneId = ZoneId.of("UTC");
            Instant localNowToUtcInstance = localNowZDT.toInstant();
            ZonedDateTime localNowDateTimeToUtcZDT = localNowZDT.withZoneSameInstant(utcZoneId);
            LocalDateTime utcNowDateTime = localNowDateTimeToUtcZDT.toLocalDateTime();
            System.out.println("Local Now: " + nowLDT + " to UTC: " + utcNowDateTime);

            if(userName.isEmpty() || password.isEmpty()){
                errorMessagePane.setText(rb.getString("Please enter values above"));
            }
            else if(!rs.next()) {
                errorMessagePane.setText(rb.getString("Wrong Username and Password"));
                outputFile.println("User " + userName + " FAILED log-in at "+ nowLDTString + " " + currentZoneId );
                userNameTextbox.clear();
                passwordTextbox.clear();
            }
            else{
                errorMessagePane.setText(rb.getString("Login Successful"));
                outputFile.println("User " + userName + " successfully logged in at "+ utcNowDateTime + " UTC Time." );

                ObservableList<Appointments> approachingAppointments = null;
                try {
                    approachingAppointments = AppointmentsDaoImpl.getNearAppointments(15);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (approachingAppointments.isEmpty()) {
                    Alert upcomingAppointment = new Alert(Alert.AlertType.INFORMATION, "There are no appointments in the next 15 minutes.");
                    upcomingAppointment.showAndWait();
                }
                else{

                    StringBuilder warningString = new StringBuilder();
                    warningString.append("There are upcoming appointments. \n");
                    for (Appointments a : approachingAppointments) {
                        warningString.append("\nAppointment ID: " + a.getID() + "     Local date and time: " + a.getStartDateTime());
                    }
                    String appointmentWarning = warningString.toString();
                    Alert upcomingAppointment = new Alert(Alert.AlertType.WARNING, appointmentWarning);
                    upcomingAppointment.showAndWait();
                }


                Stage stage = (Stage) loginButton.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("/view/MenuView.fxml"));
                stage.setTitle("Customer View");
                stage.setScene(new Scene(root));
                stage.show();
            }
            outputFile.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This handles the app closing from the exit button click.
     * @param actionEvent exit button click
     */
    public void onExitButton(ActionEvent actionEvent) {
        System.out.println(getClass().getName() + " :Exit Button clicked.");
        Alert exit = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES);
        Optional<ButtonType> result = exit.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        }
    }
}
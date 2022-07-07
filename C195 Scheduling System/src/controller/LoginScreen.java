package controller;

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

import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is the controller for the initial login screen of the app.
 * This handles the user credential check, location and language determination.
 */
public class LoginScreen implements Initializable {
    public TextField userNameTextbox;
    public TextField passwordTextbox;
    public Button loginButton;
    public Button exitButton;
    public Label sysLangDetectedLbl;
    public TextField systemLanguageTextField;
    public Label appLangSelect;
    public ComboBox<Locale> languageSelectorBox;
    public Label userNameLabel;
    public Label passwordLabel;
    public Label schedulingAppLbl;
    public Label errorMessagePane;
    public GridPane menuButtonGrid;
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
        systemLanguageTextField.setText(Locale.getDefault().toString());
//        languageSelectorBox.setItems(languageList);  // FUTURE IMPROVEMENT

        String currentLocale = Locale.getDefault().toString();
        String currentZoneId = ZoneId.systemDefault().toString();

//        System.out.println(currentLocale);
//        System.out.println(currentZoneId);

        if (currentLocale.equals("de") || currentLocale.equals("en") || currentLocale.equals("en_US") || currentLocale.equals("es") || currentLocale.equals("fr")) {
            ResourceBundle rb = ResourceBundle.getBundle("main/nat", Locale.getDefault());

            sysLangDetectedLbl.setText(rb.getString("System Language Detected"));
            appLangSelect.setText(rb.getString("Application Language Selection"));
            systemLanguageTextField.setText(rb.getLocale().getLanguage());
            languageSelectorBox.setValue(Locale.getDefault());
            sysZoneidDetectedLbl.setText(rb.getString("System Zone Id"));
            systemZoneIdTextField.setText(currentZoneId);
//            systemZoneIdTextField.setText(ZoneId.systemDefault().toString());
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
            String nowLDT = LocalDateTime.now().format(formatter);

            String currentZoneId = ZoneId.systemDefault().toString();

            if(userName.isEmpty() || password.isEmpty()){
                errorMessagePane.setText(rb.getString("Please enter values above"));
            }
            else if(!rs.next()) {
                errorMessagePane.setText(rb.getString("Wrong Username and Password"));
                outputFile.println("User " + userName + " gave invalid log-in at "+ nowLDT + " " + currentZoneId );
                userNameTextbox.clear();
                passwordTextbox.clear();
            }
            else{
                outputFile.println("User " + userName + " successfully logged in at "+ nowLDT + " " + currentZoneId );
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


    /**
     * FUTURE IMPROVEMENT: Allow the user to change the UI language.
     *
     * @param actionEvent
     */
    /*public void onLanguageSelectorBox(ActionEvent actionEvent) throws IOException {
        System.out.println("Different language selected " + languageSelectorBox.getValue());
        Locale setLocale = languageSelectorBox.getValue();
        if (setLocale.equals("de") || setLocale.equals("es") || setLocale.equals("fr")) {
//        if(Locale.getDefault().getLanguage().equals("de")  || Locale.getDefault().getLanguage().equals("es") || Locale.getDefault().getLanguage().equals("fr")) {
            ResourceBundle newRb =
                    ResourceBundle.getBundle("main/nat",
                            setLocale);

            sysLangDetectedLbl.setText(newRb.getString("System Language " +
                    "Detected"));
            appLangSelect.setText(newRb.getString("Application Language " +
                    "Selection"));
            systemLanguageTextField.setText(newRb.getLocale().getLanguage());
            languageSelectorBox.setValue(Locale.getDefault());
            loginButton.setText(newRb.getString("Login"));
            exitButton.setText(newRb.getString("Exit"));
            userNameLabel.setText(newRb.getString("Username"));
            passwordLabel.setText(newRb.getString("Password"));
            schedulingAppLbl.setText(newRb.getString("Welcome to the Scheduling " +
                    "App"));
            // FIXME: The scene needs to refresh somehow.
//            Stage stage =
//            Parent root = FXMLLoader.load(getClass().getResource("/view" +
//                    "/LoginScreen" +
//                    ".fxml"));
//            stage.setTitle("Login Screen");
//            stage.setScene(new Scene(root));
//            stage.show();

        }
    }*/

}
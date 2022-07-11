package controller;

import DAO.AppointmentsDaoImpl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Appointments;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is the controller for the Menu View page of the app.
 */
public class MenuView implements Initializable {
    public Button appointmentViewButton;
    public Button reportPageButton;
    public Button customerViewButton;
    public Button logoutButton;
    public Button exitButton;
    public GridPane menuButtonGrid;

    /**
     * This initializes the Menu View page for the app and handles the upcoming appointment prompt.
     * @param url location
     * @param resourceBundle resources
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(getClass().getName() + "in initialize.");


    }

    /**
     * This handles the log out button process to loading the login screen.
     * @param actionEvent from the Louout button click.
     * @throws IOException
     */
    public void onLogOutButton(ActionEvent actionEvent) throws IOException {
        System.out.println(getClass().getName() + " :Logout Button clicked.");

        Stage stage = (Stage) logoutButton.getScene().getWindow();
        Parent root =  FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * This handles the closing of the app.
     * @param actionEvent from the exit button click.
     * @throws IOException
     */
    public void onExitButton(ActionEvent actionEvent) throws IOException {
        System.out.println(getClass().getName() + " :Exit Button clicked.");
        Alert exit = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES);
        Optional<ButtonType> result = exit.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        }
    }


    /**
     * This handles the window change to the appointment view for the app.
     * @param actionEvent from the appointment view button click
     * @throws IOException
     */
    public void onAppointmentViewButton(ActionEvent actionEvent) throws IOException {
        System.out.println(getClass().getName() + " :Appointment View button clicked");

        Stage stage = (Stage) appointmentViewButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentView.fxml"));
        stage.setTitle("Appointment View");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * This handles the window change to the Customer View page.
     * @param actionEvent from the Customer View button click
     * @throws IOException
     */
    public void onCustomerViewButton(ActionEvent actionEvent) throws IOException {
        System.out.println(getClass().getName() + " :Customer View button clicked");

        Stage stage = (Stage) customerViewButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerView.fxml"));
        stage.setTitle("Customer View");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * This handles the Report page window change.
     * @param actionEvent from the Report Page button click
     * @throws IOException
     */
    public void onReportPageButton(ActionEvent actionEvent) throws IOException {
        System.out.println(getClass().getName() + " :Customer View button clicked");

        Stage stage = (Stage) reportPageButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportPage.fxml"));
        stage.setTitle("Report Page");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
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

public class MenuView implements Initializable {
    public Button appointmentViewButton;
    public Button reportPageButton;
    public Button customerViewButton;
    public Button logoutButton;
    public Button exitButton;
    public GridPane menuButtonGrid;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(getClass().getName() + "in initialize.");

        ObservableList<Appointments> approachingAppointments = null;
        try {
            approachingAppointments = AppointmentsDaoImpl.getNearAppointments(15);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (approachingAppointments.isEmpty()) {
            Alert upcomingAppointment = new Alert(Alert.AlertType.WARNING, "There are no upcoming appointments.");
            upcomingAppointment.showAndWait();
            //            Optional<ButtonType> result = exit.showAndWait();

        }
        else{
//            Alert exit = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES);
//            Optional<ButtonType> result = exit.showAndWait();
//
//            if (result.isPresent() && result.get() == ButtonType.YES) {
//                Stage stage = (Stage) exitButton.getScene().getWindow();
//                stage.close();
//            }
            StringBuilder warningString = new StringBuilder();
            warningString.append("There are upcoming appointments. \n");
            for (Appointments a : approachingAppointments) {
                warningString.append("\nAppointment ID: " + a.getID() + "UTC start date and time: " + a.getStartDateTime());
            }
            String appointmentWarning = warningString.toString();
            Alert upcomingAppointment = new Alert(Alert.AlertType.WARNING, appointmentWarning);
            upcomingAppointment.showAndWait();
        }

    }

    public void onLogOutButton(ActionEvent actionEvent) throws IOException {
        System.out.println(getClass().getName() + " :Logout Button clicked.");

        Stage stage = (Stage) logoutButton.getScene().getWindow();
        Parent root =  FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

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
     * @param actionEvent
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
     * @param actionEvent
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
     * @param actionEvent
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


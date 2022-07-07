package main;

import DAO.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Appointments;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    public static void main(String[] args) {
        System.out.println("Chirp222");
//        Locale.setDefault(new Locale("en"));
        Locale.setDefault(new Locale("fr")); // This sets your local system language to French.
//        Locale.setDefault(new Locale("es"));
//        Locale.setDefault(new Locale("de"));
        DBConnection.openConnection();
//        DBConnection.checkDateConversion();

        launch(args);
        DBConnection.closeConnection();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/view/MenuView.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentView.fxml"));
        primaryStage.setTitle("Login Screen");
        primaryStage.setScene(new Scene(root, 1000 , 700));
        primaryStage.show();
    }
}

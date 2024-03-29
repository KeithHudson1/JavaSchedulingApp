package DAO;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.Appointments;
import model.Customers;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.TimeZone;

/**
 * In this class we will have the Create, Read, Update and Delete files for the appointments table from the MySQL server.
 */
public abstract class AppointmentsDaoImpl {

    /**
     * This method creates a new appointment and inserts it into the MySQL database.
     * @param title appointment title
     * @param description appointment description
     * @param location appointment location
     * @param type appointment type
     * @param startDateTime appointment start date and time
     * @param endDateTime appointment end date and time
     * @param createDate appointment create date and time
     * @param createdBy appointment created by value
     * @param lastUpdateDateTime appointment last update date and time
     * @param lastUpdatedBy appointment last update by value
     * @param customerId appointment customer id
     * @param userId appointment user id
     * @param contactId appointment contact id
     * @throws SQLException
     */
    public static int insert (String title, String description,
                              String location, String type,
                              LocalDateTime startDateTime,
                              LocalDateTime endDateTime,
                              LocalDateTime createDate,
                              String createdBy,
                              LocalDateTime lastUpdateDateTime,
                              String lastUpdatedBy,
                              int customerId, int userId, int contactId ) throws SQLException{

        try {
            String sql = "INSERT INTO appointments (Title, Description, " +
                    "Location," +
                    " Type, Start, End, Create_Date, Created_By, Last_Update, " +
                    "Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES(?," +
                    " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);

            ps.setTimestamp(5, Timestamp.valueOf(startDateTime)); // Start

            ps.setTimestamp(6, Timestamp.valueOf(endDateTime)); // End DATETIME;
            ps.setTimestamp(7, Timestamp.valueOf(createDate));//Create_Date DATETIME;
            ps.setString(8, createdBy);
            ps.setTimestamp(9, Timestamp.valueOf(lastUpdateDateTime));// Last_Update TIMESTAMP;
            ps.setString(10, lastUpdatedBy);
            ps.setInt(11, customerId); // This is a Foreign key!
            ps.setInt(12, userId); //This is a foreign key!
            ps.setInt(13, contactId); /// THis is a foreign key!

            int rowsAffected = ps.executeUpdate();
            if(rowsAffected >0) {
                System.out.println("Insert Successful!");
            }
            else{
                System.out.println("INSERT FAILED!");
            }
            return rowsAffected;
        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }
        catch(NumberFormatException e) {
            System.out.println("Wrong values detected!");
            System.out.print("Exception: " + e);
        }
        return 0;
    }

    /**
     * This method updates an appointment and pushes the changes into the MySQL database.
     * @param title appointment title
     * @param description appointment description
     * @param location appointment location
     * @param type appointment type
     * @param startDateTime appointment start date and time
     * @param endDateTime appointment end date and time
     * @param createDateTime appointment create date and time
     * @param createdBy appointment created by value
     * @param lastUpdate appointment last update date and time
     * @param lastUpdatedBy appointment last update by value
     * @param customerId appointment customer id
     * @param userId appointment user id
     * @param contactId appointment contact id
     * @throws SQLException
     */

    public static int update (int id, String title, String description,
                              String location, String type,
                              LocalDateTime startDateTime,
                              LocalDateTime endDateTime,
                              LocalDateTime createDateTime,
                              String createdBy,
                              LocalDateTime lastUpdate, String lastUpdatedBy,
                              int customerId, int userId, int contactId ) throws SQLException {

        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?," +
                    "Location = ?, Type = ?, Start = ?, End = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, " +
                    "Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, " +
                    "Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);


            ps.setTimestamp(5, Timestamp.valueOf(startDateTime)); // Start
            // DATETIME;


            ps.setTimestamp(6, Timestamp.valueOf(endDateTime)); // End// DATETIME;
            ps.setTimestamp(7, Timestamp.valueOf(createDateTime));//Create_Date DATETIME;
            ps.setString(8, createdBy);
            ps.setTimestamp(9, Timestamp.valueOf(lastUpdate));// Last_Update TIMESTAMP;
            ps.setString(10, lastUpdatedBy);
            ps.setInt(11, customerId); // This is a Foreign key!
            ps.setInt(12, userId); //This is a foreign key!
            ps.setInt(13, contactId); /// THis is a foreign key!
            ps.setInt(14, id);

            int rowsAffected = ps.executeUpdate();
            if(rowsAffected >0) {
                System.out.println("Insert Successful!");
            }
            else{
                System.out.println("INSERT FAILED!");
            }
            return rowsAffected;
        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }
        catch(NumberFormatException e) {
            System.out.println("Wrong values detected!");
            System.out.print("Exception: " + e);
            System.out.print("Exception: " + e.getMessage());
        }
        return 0;
    }

    /**
     * This method deletes the appointment with the provided appointment id from the MySQL database.
     * @param appointmentId appointment id for deletion
     * @throws SQLException
     */
    public static int delete(int appointmentId) throws SQLException{
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setInt(1, appointmentId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Insert Successful!");
            } else {
                System.out.println("INSERT FAILED!");
            }
            return rowsAffected;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    /**
     * This method retrieves an Appointment which matches the provided appointment id.
     * @param appointmentId appointment id for locating.
     * @throws Exception
     */
    public static Appointments getAppointment (int appointmentId) throws Exception {
        ObservableList<Appointments> allAppointments =
                AppointmentsDaoImpl.getAllAppointments();

        for (int i = 0; i < allAppointments.size(); i++) {
            Appointments searchedAppointment = allAppointments.get(i);
            if(searchedAppointment.getID() == appointmentId ){
                return searchedAppointment;
            }
        }
        return null;
    }

    /**
     * This method is used to filter out the appointments based on the contact is which is provided.
     * LAMBDA EXPRESSION: In this method, the lambda's expression makes the determination of the contact id matching.
     * I created this solution to simplify my code.
     * @param contactId the contact if which you are looking for appointments
     * @return Returns a list of appointments for the contact id provided.
     * @throws Exception
     */
    public static ObservableList<Appointments> getAppointmentForContact (int contactId) throws Exception {
        ObservableList<Appointments> allAppointments =
                AppointmentsDaoImpl.getAllAppointments();

        return allAppointments.filtered(appt -> {
            if(appt.getContactId() == contactId) {
                return true;
            }
            return false;
        });

//        This was the previous way I was handling appointment filtering for the contact id that was provided.
//        I then improved it to the lambda version above.
//        ObservableList<Appointments> contactsAppointments =
//                FXCollections.observableArrayList();
//        for (int i = 0; i < allAppointments.size(); i++) {
//            Appointments searchedAppointment = allAppointments.get(i);
//            if(searchedAppointment.getContactId() == contactId ){
//                contactsAppointments.add(searchedAppointment);
//            }
//        }
//        return contactsAppointments;
    }

    /**
     *
     * @param month takes an int value to represent month of year
     * @return
     */
    public static ObservableList<Appointments> getAppointmentsByMonthAndType (int month){
        ObservableList<Appointments> appointmentsByMonthAndType = FXCollections.observableArrayList();

        try{
            String sql = "SELECT COUNT(Appointment_ID), Type FROM appointments WHERE MONTH(Start) = ? GROUP BY Type";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, month);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("COUNT(Appointment_ID)");
                String typeZ = resultSet.getString("Type");
                Appointments a = new Appointments(id, typeZ);
                appointmentsByMonthAndType.add(a);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsByMonthAndType;
    }


    /**
     * This method retrieves appointments that are within a certain number of days based on the provided paramter.
     * @param daysAhead int value of days ahead you want appointments
     * @return returns a list of appointments
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Appointments> getAppointments(int daysAhead) throws SQLException, Exception{

        ObservableList<Appointments> thisWeeksAppointments = FXCollections.observableArrayList();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd " +
                "hh:mm:ss");
        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime daysAheadDateTime =
                LocalDateTime.now().plusDays(daysAhead);

        try{
            String sql = "SELECT * from client_schedule.appointments WHERE " +
                    "Start <= ? AND Start > ?" ;

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setTimestamp(1,
                    Timestamp.valueOf(String.valueOf(daysAheadDateTime.format(format))));
            ps.setTimestamp(2,
                    Timestamp.valueOf(String.valueOf(nowDateTime.format(format))));

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                int id = rs.getInt("Appointment_ID"); //    Appointment_ID INT(10) (PK)
                String title = rs.getString("Title"); //    Title VARCHAR(50)
                String description = rs.getString("Description");  //    Description VARCHAR(50)
                String location = rs.getString("Location"); //    Location VARCHAR(50)
                String type = rs.getString("Type"); //    Type VARCHAR(50)
                LocalDateTime startDateTime =
                        rs.getTimestamp("Start").toLocalDateTime(); //
                //   Start DATETIME
                LocalDate startDate = startDateTime.toLocalDate();
                LocalTime startTime = startDateTime.toLocalTime();

                LocalDateTime endDateTime =
                        rs.getTimestamp("End").toLocalDateTime(); //    End DATETIME
                LocalDate endDate = endDateTime.toLocalDate();
                LocalTime endTime = endDateTime.toLocalTime();
                LocalDateTime createDateTime =
                        rs.getTimestamp("Create_Date").toLocalDateTime(); //    Create_Date DATETIME
                String createdBy = rs.getString("Created_By"); //    Created_By VARCHAR(50)
                LocalDateTime lastUpdateDateTime =
                        rs.getTimestamp("Last_Update").toLocalDateTime(); //    Last_Update TIMESTAMP
                String lastUpdatedBy = rs.getString("Last_Updated_By"); //    Last_Updated_By VARCHAR(50)
                int customerId = rs.getInt("Customer_ID");//    Customer_ID INT(10) (FK)
                int userId = rs.getInt("User_ID"); //    User_ID INT(10) (FK)
                int contactId = rs.getInt("Contact_ID"); //    Contact_ID INT(10) (FK)

                Appointments A = new Appointments(id, title, description,
                        location, type, startDateTime, endDateTime,
                        createDateTime, createdBy, lastUpdateDateTime,
                        lastUpdatedBy,
                        customerId, userId, contactId);
                thisWeeksAppointments.add(A);
            }
        }
        catch(SQLException throwables) {
            throwables.printStackTrace();
        }
        return thisWeeksAppointments;
    }

    /**
     *  This method retrieves the upcoming appointments based on the provided minutesAhead parameter.
     * @param minutesAhead the minutes ahead int value
     * @return returns a list of appointments
     */
    public static ObservableList<Appointments> getNearAppointments(int minutesAhead) throws Exception {

        ObservableList<Appointments> approachingAppointments = FXCollections.observableArrayList();
        ObservableList<Appointments> allAppointments = getAllAppointments();

        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime minutesAheadDateTime = nowDateTime.plusMinutes(minutesAhead);

        for (Appointments a : allAppointments){
            if (a.getStartDateTime().isBefore(minutesAheadDateTime) && a.getStartDateTime().isAfter(nowDateTime)){
                approachingAppointments.add(a);
            }
        }
        return approachingAppointments;
    }

    /**
     *
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Appointments> getAllAppointments() throws SQLException, Exception{
        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from client_schedule.appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                int id = rs.getInt("Appointment_ID"); //    Appointment_ID INT(10) (PK)
                String title = rs.getString("Title"); //    Title VARCHAR(50)
                String description = rs.getString("Description");  //    Description VARCHAR(50)
                String location = rs.getString("Location"); //    Location VARCHAR(50)
                String type = rs.getString("Type"); //    Type VARCHAR(50)

                LocalDateTime localStartDateTime =
                        rs.getTimestamp("Start").toLocalDateTime(); //   Start DATETIME
//                LocalDate startDate = startDateTime.toLocalDate();
//                LocalTime startTime = startDateTime.toLocalTime();

                //The VM version is already doing the conversion coming into the software.
                // Therefore this is unneeded.
//                LocalDateTime utcStartDateTime =
//                        rs.getTimestamp("Start").toLocalDateTime(); //
//                //   Start DATETIME
//                LocalDate utcStartDate = utcStartDateTime.toLocalDate();
//                LocalTime utcStartTime = utcStartDateTime.toLocalTime();
//                ZoneId utcZoneId = ZoneId.of("UTC");
//                ZonedDateTime utcStartZDT = ZonedDateTime.of(utcStartDate,
//                        utcStartTime, utcZoneId);
//                ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
//                Instant utcStartToLocalInstance = utcStartZDT.toInstant();
//                ZonedDateTime utcStartToLocalZDT =
//                        utcStartZDT.withZoneSameInstant(localZoneId);
//                LocalDateTime localStartDateTime =
//                        utcStartToLocalZDT.toLocalDateTime();

                LocalDateTime localEndDateTime = rs.getTimestamp("End").toLocalDateTime(); //    End DATETIME
//                LocalDate endDate = endDateTime.toLocalDate();
//                LocalTime endTime = endDateTime.toLocalTime();

                // The VM version is already doing the conversion coming into the software.
                // Therefore this is unneeded.
//                LocalDateTime utcEndDateTime =
//                        rs.getTimestamp("End").toLocalDateTime();  //    End DATETIME
//                LocalDate utcEndDate = utcEndDateTime.toLocalDate();
//                LocalTime utcEndTime = utcEndDateTime.toLocalTime();
//                ZonedDateTime utcEndZDT = ZonedDateTime.of(utcEndDate,
//                        utcEndTime, utcZoneId);
////                ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
//                Instant utcEndToLocalInstance = utcEndZDT.toInstant();
//                ZonedDateTime utcEndToLocalZDT =
//                        utcEndZDT.withZoneSameInstant(localZoneId);
//                LocalDateTime localEndDateTime =
//                        utcEndToLocalZDT.toLocalDateTime();



                LocalDateTime createDateTime =
                        rs.getTimestamp("Create_Date").toLocalDateTime(); //    Create_Date DATETIME
                String createdBy = rs.getString("Created_By"); //    Created_By VARCHAR(50)
                LocalDateTime lastUpdateDateTime =
                        rs.getTimestamp("Last_Update").toLocalDateTime(); //    Last_Update TIMESTAMP
                String lastUpdatedBy = rs.getString("Last_Updated_By"); //    Last_Updated_By VARCHAR(50)
                int customerId = rs.getInt("Customer_ID");//    Customer_ID INT(10) (FK)
                int userId = rs.getInt("User_ID"); //    User_ID INT(10) (FK)
                int contactId = rs.getInt("Contact_ID"); //    Contact_ID INT(10) (FK)

//                Appointments A = new Appointments(id, title, description,
//                        location, type, startDateTime, endDateTime,
//                        createDateTime, createdBy, lastUpdateDateTime,
//                        lastUpdatedBy,
//                        customerId, userId, contactId);
//                allAppointments.add(A);
                Appointments A = new Appointments(id, title, description,
                        location, type, localStartDateTime, localEndDateTime,
                        createDateTime, createdBy, lastUpdateDateTime,
                        lastUpdatedBy,
                        customerId, userId, contactId);
                allAppointments.add(A);
            }
        }
        catch(SQLException throwables) {
            throwables.printStackTrace();
        }
        return allAppointments;
    }
}

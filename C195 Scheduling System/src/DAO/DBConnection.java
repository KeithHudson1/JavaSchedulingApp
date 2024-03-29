package DAO;

import java.sql.*;

/**
 * This class houses the credentials and process to access the MySQL server.
 */
public abstract class DBConnection {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost:3306/";
//    private static final String databaseName = "client_schedule";
    private static final String databaseName = "C195DBClient";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL  You can remove connection
//    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = LOCAL"; // NOT CORRECT  You can remove
//    connection
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection connection;  // Connection Interface

    /**
     * This is used to open the connection to the MySQL Database.
     */
    public static void openConnection()    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println(DBConnection.class.getName() + "Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * THis method is used to retrieve the connection.
     * @return
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * This is used to sever the connection to the MySQL database.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println(DBConnection.class.getName() + "Connection " +
                    "closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * Used to check the date conversion from the server's UTC to my system's time.
     */
    public static void checkDateConversion() {
        System.out.println("CREATE DATE TEST");
        String sql = "select Create_Date from Appointments";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Timestamp ts = rs.getTimestamp("Create_Date");
                System.out.println("CD: " + ts.toLocalDateTime().toString());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

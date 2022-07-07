package DAO;

import java.sql.*;

/**
 * Project: C195 Scheduling System
 * Package: sample.DAO
 * <p>
 * User: Keith Hudson
 * Date: 06/07/2022
 * <p>
 * Created with IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */

// Malcolm's JDBC presentation has his JDBC class as abstract because this
// isn't used to create Objects. It's just for DB connection.

public abstract class DBConnection {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL  You can remove connection
    // Time zon e lcuas off this in the VM lab.
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
     *
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
     * Created following Mark Kinkaid's- C195 Getting The DBConnection Class Project Ready (02-13-2021)
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

package DAO;

import com.mysql.cj.ServerPreparedQueryTestcaseGenerator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class UsersDaoImpl {

    public static boolean userCheck(String user, String passKey) throws SQLException {
        System.out.println( "userCheck has been accessed. ");
        boolean isCorrect = false;

//        System.out.println("I am in here.");
//        String sql = "SELECT * from  client_schedule.users WHERE User_Name = " +
//                "? AND Password = ?";
//        PreparedStatement ps =
//                DBConnection.getConnection().prepareStatement(sql);
//        ps.setString(1, user);
//        ps.setString(2, passKey);
//
//        ResultSet rs = ps.executeQuery();


        try {
            System.out.println("I am in here.");
            String sql = "SELECT * from  client_schedule.users WHERE User_Name = " +
                    "? AND Password = ?";
            PreparedStatement ps =
                    DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, passKey);

            ResultSet rs = ps.executeQuery();

            isCorrect = true;
            if(isCorrect) System.out.println("It's correct!");
//            return isCorrect;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
            isCorrect = false;
        }

//        isCorrect = false;
        return isCorrect;
    }
}

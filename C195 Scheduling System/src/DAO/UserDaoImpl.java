package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Project: C195 Scheduling System
 * Package: sample.DAO
 * <p>
 * User: Keith Hudson
 * Date: 06/07/2022
 * <p>
 * Created with IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 *
 * In this class we will have the Create, Read, Update and Delete files for the users table
 */
public class UserDaoImpl {
//    static boolean act;


    /**
     *
     * @param userName2
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public static Users getUser(String userName2) throws SQLException,
            Exception{
        // type is name or phone, value is the name or the phone #

        String sqlStatement=
                "select * FROM users WHERE User_Name  = '" + userName2 + "'";
        //  String sqlStatement="select FROM address";
        Query.makeQuery(sqlStatement);
        Users userResult;
        ResultSet rs = Query.getResult();
        while(rs.next()){

            int id = rs.getInt("User_ID"); // INT(10) (PK)
            String userName = rs.getString("User_Name"); // VARCHAR(50) (UNIQUE)
            String password = rs.getString("Password"); //Password TEXT
            String createDate = rs.getString("Create_Date"); // Create_Date DATETIME
            String createdBy = rs.getString("Created_By"); // Created_By VARCHAR(50)
            String lastUpdate = rs.getString("Last_Update"); // Last_Update TIMESTAMP
            String lastUpdatedBy = rs.getString("Last_Updated_By"); // Last_Updated_By VARCHAR(50)

            userResult = new Users(id, userName, password, createDate, createdBy,
                    lastUpdate, lastUpdatedBy);
            // allUsers.add(u);

            return userResult;
        }
//        DBConnection.closeConnection();
        return null;
    }

    public static Users getUser (int userId) {
        ObservableList<Users> allUsers = UserDaoImpl.getAllUsers();

        for(int i = 0; i < allUsers.size(); i++) {
            Users searchedUser = allUsers.get(i);
            if(searchedUser.getId() == userId) {
                return searchedUser;
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public static ObservableList<Users> getAllUsers() {
        ObservableList<Users> allUsers= FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM client_schedule.users";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("User_ID"); // INT(10) (PK)
                String userName = rs.getString("User_Name"); // VARCHAR(50) (UNIQUE)
                String password = rs.getString("Password"); //Password TEXT
                String createDate = rs.getString("Create_Date"); // Create_Date DATETIME
                String createdBy = rs.getString("Created_By"); // Created_By VARCHAR(50)
                String lastUpdate = rs.getString("Last_Update"); // Last_Update TIMESTAMP
                String lastUpdatedBy = rs.getString("Last_Updated_By"); // Last_Updated_By VARCHAR(50)

                Users u = new Users(id, userName, password, createDate, createdBy, lastUpdate, lastUpdatedBy);
                allUsers.add(u);
            }
        }
        catch(SQLException throwables) {
            throwables.printStackTrace();
        }
        return allUsers;
    }
    //FIXME:THis VV is the example function from the DAO Demo file package. I
    // will likely delete it eventually.
//    public static ObservableList<Users> getAllUsers() throws SQLException, Exception{
//        ObservableList<Users> allUsers= FXCollections.observableArrayList();
////        DBConnection.makeConnection();
//        DBConnection.openConnection();
//        String sqlStatement="select * from users";
//        Query.makeQuery(sqlStatement);
//        ResultSet result=Query.getResult();
//        while(result.next()){
//            int userid=result.getInt("User_ID");
//            String userNameG=result.getString("User_Name");
//            String password=result.getString("Password");
////            Users userResult= new Users(userid, userNameG, password);
////            allUsers.add(userResult);
//
//        }
////        DBConnection.closeConnection();
////        return allUsers;
//    }

}

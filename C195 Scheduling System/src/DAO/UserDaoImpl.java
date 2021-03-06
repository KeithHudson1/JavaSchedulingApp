package DAO;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

 /**
 * In this class we will have the Create, Read, Update and Delete files for the users table from the MySQL server.
 */
public class UserDaoImpl {
     /**
      * Retrieves the user based on the provided user id.
      * @param userId user id which you are searching for
      * @return
      */
    public static Users getUser (int userId) {
        ObservableList<Users> allUsers = UserDaoImpl.getAllUsers();

        for(int i = 0; i < allUsers.size(); i++) {
            Users searchedUser = allUsers.get(i);
            int searchedUserId = searchedUser.getId().getValue();
            if(searchedUserId == userId) {
                return searchedUser;
            }
        }
        return null;
    }

    /**
     * This method retrieves a list of all the users from the MySQL database.
     * @return a list of all users
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

                Users u = new Users(new ReadOnlyObjectWrapper(id), new ReadOnlyStringWrapper(userName), new ReadOnlyStringWrapper(password), new ReadOnlyStringWrapper(createDate), new ReadOnlyStringWrapper(createdBy), new ReadOnlyStringWrapper(lastUpdate), new ReadOnlyStringWrapper(lastUpdatedBy));
                allUsers.add(u);
            }
        }
        catch(SQLException throwables) {
            throwables.printStackTrace();
        }
        return allUsers;
    }
}

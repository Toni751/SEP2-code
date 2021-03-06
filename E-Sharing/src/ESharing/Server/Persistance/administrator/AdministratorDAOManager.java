package ESharing.Server.Persistance.administrator;

import ESharing.Server.Persistance.Database;
import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The DAO manager class for handling database requests made by the administrator
 * @version 1.0
 * @author Group1
 */
public class AdministratorDAOManager extends Database implements AdministratorDAO {

    public Connection getConnection() throws SQLException
    {
        return super.getConnection();
    }


    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_account NATURAL JOIN address;");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                int user_id = resultSet.getInt("id");
                String username= resultSet.getString("username");
                String password= resultSet.getString("password");
                String phoneNumber = resultSet.getString("phoneno");
                int reports = resultSet.getInt("reports");
                int address_id = resultSet.getInt("address_id");
                boolean administrator = resultSet.getBoolean("administrator");
                String street = resultSet.getString("street");
                String number = resultSet.getString("number");
                String create_date = resultSet.getString("creation_date");
                String avatarPath = resultSet.getString("avatarpath");
                Address address = new Address(street,number);
                address.setAddress_id(address_id);
                User user = new User(username,password,phoneNumber,address);
                user.setUser_id(user_id);
                user.setReportsNumber(reports);
                user.setCreation_date(create_date);
                user.setAvatar(Files.readAllBytes(Path.of(avatarPath)));
                if(administrator)
                    user.setAsAdministrator();
                users.add(user);
            }
            return users;

        } catch (SQLException | IOException e) {e.printStackTrace();}
        return null;
    }


}

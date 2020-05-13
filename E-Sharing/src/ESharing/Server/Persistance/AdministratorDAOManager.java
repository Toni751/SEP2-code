package ESharing.Server.Persistance;

import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdministratorDAOManager extends Database implements AdministratorDAO {

    private static AdministratorDAOManager instance;

    public Connection getConnection() throws SQLException
    {
        return super.getConnection();
    }

    public static synchronized AdministratorDAOManager getInstance() {
        if(instance == null)
            instance = new AdministratorDAOManager();
        return instance;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_account NATURAL JOIN address;");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                int user_id = resultSet.getInt("user_id");
                String username= resultSet.getString("username");
                String password= resultSet.getString("password");
                String phoneNumber = resultSet.getString("phoneNumber");
                int reports = resultSet.getInt("reports");
                int address_id = resultSet.getInt("address_id");
                String street = resultSet.getString("street");
                String postcode =resultSet.getString("postcode");
                String number = resultSet.getString("number");
                String city = resultSet.getString("city");
                String create_date = resultSet.getString("creation_date");
                Address address = new Address(street,number,city,postcode);
                address.setAddress_id(address_id);
                User user = new User(username,password,phoneNumber,address);
                user.setUser_id(user_id);
                user.setReportsNumber(reports);
                user.setCreation_date(create_date);
                users.add(user);
                System.out.println("User added to the list");
            }
            return users;

        } catch (SQLException e) {e.printStackTrace();}
        return null;
    }


}

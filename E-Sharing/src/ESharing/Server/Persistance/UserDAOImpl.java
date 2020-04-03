package ESharing.Server.Persistance;

import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO
{

  private UserDAOImpl() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static synchronized UserDAOImpl getInstance(UserDAOImpl instance) throws SQLException
  {


    if (instance == null)
    {
      instance = new UserDAOImpl();
    }
    return instance;
  }

  private Connection getConnection() throws SQLException
  {
    return DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres?currentSchema=jdbc",
        "postgres", "sep");
  }



  @Override public User create(String username, String password,
      int phoneNumber, Address address, int user_id) throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO User ( user_id,username,password,phoneNumber,address_id) VALUES (?,?,?,?,?):");
      statement.setInt(1, user_id);
      statement.setString(2, username);
      statement.setString(3, password);
      statement.setInt(4, phoneNumber);
      statement.setInt(5, address.getAddress_id());

      return new User( username, password,phoneNumber, address,user_id);
    }
  }

  @Override public User readById(int user_id) throws SQLException
  {
    try(Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM User JOIN Address ON address_id = id WHERE user_id= ?");
      statement.setInt(1, user_id);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        String username= resultSet.getString("username");
        String password= resultSet.getString("password");
        int phoneNumber = resultSet.getInt(" phoneNumber");
        int address_id = resultSet.getInt("address_id");
        String street = resultSet.getString("street");
        int postcode =resultSet.getInt("postcode");
        int number =resultSet.getInt("number");
        String city =resultSet.getString("city");
       Address address = new Address(address_id, street,number,city,postcode);
        return new User(username,password,phoneNumber,address,user_id);
      } else {
        return null;
      }
    }
  }

  @Override public List<User> readByUserName(String searchString)
      throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "SELECT * FROM User JOIN Address ON address_id = address_id WHERE username LIKE ?");
      statement.setString(1, "%" + searchString + "%");
      ResultSet resultSet = statement.executeQuery();
      ArrayList<User> result = new ArrayList<>();
      while (resultSet.next())
      {
        int user_id = resultSet.getInt("user_id");
        String username= resultSet.getString("username");
        String password= resultSet.getString("password");
        int phoneNumber = resultSet.getInt(" phoneNumber");
        int address_id = resultSet.getInt("address_id");
        String street = resultSet.getString("street");
        int postcode =resultSet.getInt("postcode");
        int number =resultSet.getInt("number");
        String city =resultSet.getString("city");
        Address address = new Address( address_id,street,number,city,postcode);
        User user = new User(username,password,phoneNumber,address,user_id);
        result.add(user);
      }
      return result;
    }
  }

  @Override public void update(User user) throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "UPDATE User SET  username = ?, password = ?, phoneNumber=? , address =? WHERE user_id=? ");
      statement.setString(1, user.getUsername());
      statement.setString(2, user.getPassword());
      statement.setInt(3, user.getPhoneNumber());
      statement.setInt(4, user.getAddress().getAddress_id());
      statement.setInt(5,user.getUser_id());
      statement.executeUpdate();
    }


  }

  @Override public void delete(User user) throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("DELETE FROM User WHERE user_id = ?");
      statement.setInt(1, user.getUser_id());
      statement.executeUpdate();
    }
  }
}


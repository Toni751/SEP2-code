package ESharing.Server.Persistance.user;

import ESharing.Server.Persistance.Database;
import ESharing.Server.Persistance.address.AddressDAO;
import ESharing.Server.Persistance.address.AddressDAOManager;
import ESharing.Server.Persistance.message.MessageDAO;
import ESharing.Server.Persistance.message.MessageDAOManager;
import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class UserDAOManager extends Database implements UserDAO
{

  private static UserDAOManager instance;
  private AddressDAO addressDAO;
  private MessageDAO messageDAO;

  //ALTER TABLE user ADD CONSTRAINT unique_username UNIQUE (username);
  public UserDAOManager(AddressDAO addressDAO, MessageDAO messageDAO)
  {
    try
    {
      DriverManager.registerDriver(new org.postgresql.Driver());
      this.addressDAO = addressDAO;
      this.messageDAO = messageDAO;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

//  public static synchronized UserDAOManager getInstance()
//  {
//    if (instance == null)
//    {
//      instance = new UserDAOManager();
//    }
//    return instance;
//  }

  public Connection getConnection() throws SQLException {
    return super.getConnection();
  }

  @Override public boolean create(User user)
  {
    System.out.println("Trying to establish connection");
    try (Connection connection = getConnection())
    {
      System.out.println("Connection established");
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO user_account (username,password,phoneno,address_id, creation_date) VALUES (?,?,?,?,?) ON CONFLICT ON CONSTRAINT unique_username DO NOTHING ; ");
      statement.setString(1, user.getUsername());
      statement.setString(2, user.getPassword());
      statement.setString(3, user.getPhoneNumber());
      statement.setInt(4, addressDAO.create(user.getAddress()));
      statement.setString(5, user.getCreation_date());
      int affectedRows = statement.executeUpdate();
      System.out.println("Affected rows by create user: " + affectedRows);

      if (affectedRows == 1)
      {
//        Statement statementForLastValue = connection.createStatement();
//        ResultSet resultSet = statementForLastValue.executeQuery("SELECT last_value FROM user_account_user_id_seq;");
//        if (resultSet.next())
//        {
//          user.setUser_id(resultSet.getInt("last_value"));
//        }
        return true;
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public User readById(int user_id)
  {
    try(Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_account NATURAL JOIN address WHERE id= ?;");
      statement.setInt(1, user_id);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next())
      {
        String username= resultSet.getString("username");
        String password= resultSet.getString("password");
        String phoneNumber = resultSet.getString("phoneno");
        int address_id = resultSet.getInt("address_id");
        String street = resultSet.getString("street");
        String number = resultSet.getString("number");
        String avatarPath = resultSet.getString("avatarpath");
        Address address = new Address(street, number);
        address.setAddress_id(address_id);
        User user = new User(username,password,phoneNumber,address);
        user.setUser_id(user_id);
        user.setAvatar(Files.readAllBytes(Paths.get(avatarPath)));
        return user;
      }
    }
    catch (SQLException | IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public User readByUserNameAndPassword(String usernameRequest, String passwordRequest)
  {
    PreparedStatement statement;
    ResultSet resultSet;
    try (Connection connection = getConnection())
    {
      statement = connection.prepareStatement(
          "SELECT * FROM user_account NATURAL JOIN address WHERE username = ? AND password = ?;");
      statement.setString(1, usernameRequest);
      statement.setString(2, passwordRequest);
      resultSet = statement.executeQuery();
      if (resultSet.next())
      {
        int user_id = resultSet.getInt("id");
        String username= resultSet.getString("username");
        String password= resultSet.getString("password");
        String phoneNumber = resultSet.getString("phoneno");
        int address_id = resultSet.getInt("address_id");
        String street = resultSet.getString("street");
        boolean administrator = resultSet.getBoolean("administrator");
        String number = resultSet.getString("number");
        String creationDate = resultSet.getString("creation_date");
        String avatarPath = resultSet.getString("avatarpath");
        Address address = new Address(street,number);
        address.setAddress_id(address_id);
        User user = new User(username,password,phoneNumber,address);
        user.setUser_id(user_id);
        user.setCreation_date(creationDate);

        user.setAvatar(Files.readAllBytes(Path.of(avatarPath)));

        if(administrator)
          user.setAsAdministrator();
        return user;
      }
      // Search in admin table
//      else if(!resultSet.next())
//      {
//        statement = connection.prepareStatement("SELECT * FROM admin_account WHERE username = ? AND password = ?");
//        statement.setString(1, usernameRequest);
//        statement.setString(2, passwordRequest);
//        resultSet = statement.executeQuery();
//        if(resultSet.next())
//        {
//          int admin_id = resultSet.getInt("id");
//          String admin_name = resultSet.getString("username");
//          String password = resultSet.getString("password");
//          String phoneNo = resultSet.getString("phoneno");
//
//          User admin = new User(admin_name, password, phoneNo, null);
//          admin.setAsAdministrator();
//          admin.setUser_id(admin_id);
//          return admin;
//        }
//      }
    }
    catch (SQLException | IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public boolean update(User user)
  {
    try (Connection connection = getConnection())
    {
      System.out.println(user.isAdministrator());
      if(user.isAdministrator())
      {
        PreparedStatement statement = connection.prepareStatement("UPDATE user_account SET username = ?, password = ?, phoneno = ? WHERE id = ?;");
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getPhoneNumber());
        statement.setInt(4, user.getUser_id());
        int affectedRows = statement.executeUpdate();
        if(affectedRows == 1) {
          return true;
        }

      }
      else {
        System.out.println(user.getUsername());
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE user_account SET  username = ?, password = ?, phoneno=? , address_id =? WHERE id=?;");
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getPhoneNumber());
        statement.setInt(4, addressDAO.create(user.getAddress()));
        statement.setInt(5, user.getUser_id());

        Statement addressStatement = connection.createStatement();
        int noUsersLivingAtAddress = 0;
        int oldAddressId = 0;

        ResultSet resultSet = addressStatement.executeQuery("SELECT address_id AS plswork FROM user_account WHERE id = " + user.getUser_id() + ";");

        if (resultSet.next()) {
          oldAddressId = resultSet.getInt("plswork");
          System.out.println("Address id: " + oldAddressId);
          noUsersLivingAtAddress = getNoUsersLivingAtAddress(resultSet.getInt("plswork"));
        }

        System.out.println("People living at address: " + noUsersLivingAtAddress);
        int affectedRows = statement.executeUpdate();

        if (noUsersLivingAtAddress == 1)
          addressDAO.delete(oldAddressId);

        if (affectedRows == 1)
        {
          System.out.println("Updated");
          return true;
        }
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }

  @Override public boolean delete(User user)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("DELETE FROM user_account WHERE id = ?;");
      statement.setInt(1, user.getUser_id());
      messageDAO.deleteMessagesForUser(user);
      int noUsersLivingAtAddress = getNoUsersLivingAtAddress(user.getAddress().getAddress_id());
      int affectedRows = statement.executeUpdate();
      if (noUsersLivingAtAddress == 1)
        addressDAO.delete(user.getAddress().getAddress_id());

      if (affectedRows == 1)
        return true;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean changeAvatar(String path, int userId) {
    try(Connection connection = getConnection())
    {
      PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user_account SET avatarpath = ? WHERE id =?;");
      preparedStatement.setString(1, path);
      preparedStatement.setInt(2, userId);

      int affectedRows = preparedStatement.executeUpdate();
      if(affectedRows == 1)
        return true;
    }
    catch (SQLException  e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean addNewUserReport(int user_id) {
    try (Connection connection = getConnection())
    {

      PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_account WHERE id=?;");
      statement.setInt(1, user_id);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next())
      {
        System.out.println("report user");
        int reports = resultSet.getInt("reports");
        reports++;
        PreparedStatement updateStatement = connection.prepareStatement("UPDATE user_account SET reports =? WHERE id =?;");
        updateStatement.setInt(1, reports);
        updateStatement.setInt(2, user_id);

        if(updateStatement.executeUpdate() == 1)
          return true;
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  private int getNoUsersLivingAtAddress (int address_id)
  {
    try (Connection connection = getConnection())
    {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS count FROM user_account WHERE address_id = " + address_id + ";");

      if (resultSet.next())
        return resultSet.getInt("count");
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return 0;
  }
}


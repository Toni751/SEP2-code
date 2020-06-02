package ESharing.Server.Persistance.address;

import ESharing.Server.Persistance.Database;
import ESharing.Shared.TransferedObject.Address;

import java.sql.*;

/**
 * The manager DAO class for the address database table
 * @version 1.0
 * @author Group1
 */
public class AddressDAOManager extends Database implements AddressDAO
{

  public Connection getConnection() throws SQLException {
    return super.getConnection();
  }

  @Override public int create(Address address)
  {
    try (Connection connection = getConnection())
    {
      System.out.println(address);
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO address (street,number) VALUES (?,?) "
              + "ON CONFLICT ON CONSTRAINT unique_address DO NOTHING;");
      statement.setString(1, address.getStreet());
      statement.setString(2, address.getNumber());
      int rowCount = statement.executeUpdate();
      System.out.println("Rows affected by create address: " + rowCount);

      if (rowCount == 1)
      {
        Statement statementForLastValue = connection.createStatement();
        ResultSet resultSet = statementForLastValue.executeQuery("SELECT last_value FROM address_address_id_seq;");
        if (resultSet.next())
          address.setAddress_id(resultSet.getInt("last_value"));
        return address.getAddress_id();
      }
      else
      {
        return readByAddress(address.getStreet(), address.getNumber()).getAddress_id();
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return 0;
  }

//  @Override public Address readById(int address_id)
//  {
//    try (Connection connection = getConnection())
//    {
//      PreparedStatement statement = connection.prepareStatement("SELECT * FROM address WHERE address_id = ?;");
//      statement.setInt(1, address_id);
//      ResultSet resultSet = statement.executeQuery();
//      if (resultSet.next())
//      {
//        String street = resultSet.getString("street");
//        String number = resultSet.getString("number");
//        Address address = new Address(street, number);
//        address.setAddress_id(address_id);
//        return address;
//      }
//    }
//    catch (SQLException e)
//    {
//      e.printStackTrace();
//    }
//    return null;
//  }

  @Override
  public Address readByAddress(String street, String number)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM address WHERE street = ? AND number = ?;");
      statement.setString(1, street);
      statement.setString(2, number);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next())
      {
        Address address = new Address(street, number);
        address.setAddress_id(resultSet.getInt("address_id"));
        return address;
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  @Override public void delete(int address_id)
  {
    try(Connection connection = getConnection())
    {
      System.out.println("Deleting address at id: " + address_id);
      PreparedStatement statement = connection.prepareStatement("DELETE FROM address WHERE address_id = ?;");
      statement.setInt(1, address_id);
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

}

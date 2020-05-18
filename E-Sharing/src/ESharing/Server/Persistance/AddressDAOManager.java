package ESharing.Server.Persistance;

import ESharing.Shared.TransferedObject.Address;

import java.sql.*;

public class AddressDAOManager extends Database implements AddressDAO
{

  private static AddressDAOManager instance;
  // We need to create a unique constraint for the address
  // ALTER TABLE address ADD CONSTRAINT unique_address UNIQUE (street, number, city, postcode);

  public static synchronized AddressDAOManager getInstance()
  {

    if (instance == null)
    {
      instance = new AddressDAOManager();
    }
    return instance;
  }

  public Connection getConnection() throws SQLException {
    return super.getConnection();
  }

  @Override public int create(Address address)
  {
    try (Connection connection = getConnection())
    {
      System.out.println(address);
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO address (street,number,city,postcode) VALUES (?,?,?,?)"
              + "ON CONFLICT ON CONSTRAINT unique_address DO NOTHING;");
      statement.setString(1, address.getStreet());
      statement.setString(2, address.getNumber());
      statement.setString(3, address.getCity());
      statement.setString(4, address.getPostcode());
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
        return readByAddress(address.getStreet(), address.getNumber(), address.getCity(), address.getPostcode()).getAddress_id();
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return 0;
  }

  @Override public Address readById(int address_id)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM address WHERE address_id = ?;");
      statement.setInt(1, address_id);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next())
      {
        String street = resultSet.getString("street");
        String number = resultSet.getString("number");
        String city = resultSet.getString("city");
        String postcode = resultSet.getString("postcode");
        Address address = new Address(street, number, city, postcode);
        address.setAddress_id(address_id);
        return address;
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public Address readByAddress(String street, String number, String city, String postcode)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM address WHERE street = ? AND number = ? AND city = ? AND postcode = ?;");
      statement.setString(1, street);
      statement.setString(2, number);
      statement.setString(3, city);
      statement.setString(4, postcode);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next())
      {
        Address address = new Address(street, number, city, postcode);
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

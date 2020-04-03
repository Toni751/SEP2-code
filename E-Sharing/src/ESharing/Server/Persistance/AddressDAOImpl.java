package ESharing.Server.Persistance;

import ESharing.Shared.TransferedObject.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDAOImpl implements AddressDAO
{

  private AddressDAOImpl() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static synchronized AddressDAOImpl getInstance(AddressDAOImpl instance) throws SQLException
  {

    if (instance == null)
    {
      instance = new AddressDAOImpl();
    }
    return instance;
  }

  private Connection getConnection() throws SQLException
  {
    return DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres?currentSchema=jdbc",
        "postgres", "sep");
  }








  @Override public Address create(int address_id, String street, int number,
      String city, int postcode) throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO Address ( address_id,street,number,city,postcode) VALUES (?,?,?,?,?):");
      statement.setInt(1, address_id);
      statement.setString(2, street);
      statement.setInt(3, number);
      statement.setString(4,city);
      statement.setInt(5, postcode);


      return new Address(address_id,street,number,city,postcode);
    }
  }

  @Override public Address readById(int address_id) throws SQLException
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM Address WHERE address_id = ?");
      statement.setInt(1, address_id);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next())
      {
        String street = resultSet.getString("street");
        int number = resultSet.getInt("number");
        String city = resultSet.getString("city");
        int postcode = resultSet.getInt("postcode");
        return new Address(address_id, street, number, city, postcode);
      }
      else
      {
        return null;
      }
    }

  }

  @Override public List<Address> readByStreet(String searchString)
      throws SQLException
  {
    try(Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM Address WHERE street LIKE ?");
      statement.setString(1, "%" + searchString + "%");
      ResultSet resultSet = statement.executeQuery();
      ArrayList<Address> result = new ArrayList<>();
      while(resultSet.next()) {
        int address_id =resultSet.getInt("address_id");
        String street = resultSet.getString("street");
        int number = resultSet.getInt("number");
        String city = resultSet.getString("city");
        int postcode = resultSet.getInt("postcode");
        Address address = new Address(address_id, street, number, city, postcode);
        result.add(address);
      }
      return result;
    }
  }

  @Override public void update(Address address) throws SQLException
  {
    try(Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("UPDATE Address SET street=?,number=?,city=?,postcode=? WHERE address_id = ?");
      statement.setString(1, address.getStreet());
      statement.setInt(2, address.getNumber());
      statement.setString(3,address.getCity());
      statement.setInt(4, address.getPostcode());
      statement.setInt(5, address.getAddress_id());
      statement.executeUpdate();
    }
  }

  @Override public void delete(Address address) throws SQLException
  {
    try(Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("DELETE FROM Address WHERE address_id = ?");
      statement.setInt(1, address.getAddress_id());
      statement.executeUpdate();
    }
  }

}

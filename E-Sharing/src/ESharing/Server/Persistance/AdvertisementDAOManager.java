package ESharing.Server.Persistance;

import ESharing.Shared.TransferedObject.Advertisement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class AdvertisementDAOManager extends Database implements AdvertisementDAO
{

  private static AdvertisementDAOManager instance;

  public static synchronized AdvertisementDAOManager getInstance()
  {
    if (instance == null)
    {
      instance = new AdvertisementDAOManager();
    }
    return instance;

  }

  public Connection getConnection() throws SQLException
  {
    return super.getConnection();
  }

  @Override public boolean create(Advertisement advertisement)
  {
    System.out.println("Trying to establish connection");
    try (Connection connection = getConnection())
    {
      System.out.println("Connection established");
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO advertisement (id,title,owner_id,description,price,approved,date_creation,vehicle_type) VALUES (?,?,?,?,?,?,NOW()::DATE,?) ");
      statement.setInt(1, advertisement.getAdvertisementID());
      statement.setString(2, advertisement.getTitle());
      statement.setInt(3, advertisement.getOwner().getUser_id());
      statement.setString(4, advertisement.getDescription());
      statement.setInt(5, (int) advertisement.getPrice());
      statement.setBoolean(6, advertisement.ifAdApproved());
      statement.setString(7, advertisement.getCreationDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
      statement.setString(8, advertisement.getType());
      int affectedRows = statement.executeUpdate();
      System.out.println("Affected rows by create advertisement: " + affectedRows);

      if (affectedRows == 1)
      {
        return true;
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }

    @Override public boolean removeAdvertisement (Advertisement advertisement)
    {
      try (Connection connection = getConnection())
      {
        System.out.println("Deleting advertisement at id: " + advertisement.getAdvertisementID());
        PreparedStatement statement = connection.prepareStatement("DELETE FROM advertisement WHERE id = ?;");
        statement.setInt(1, advertisement.getAdvertisementID());
        statement.executeUpdate();
        int affectedRows = statement.executeUpdate();
         if (affectedRows == 1)
        return true;
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
      return false;
    }
  }

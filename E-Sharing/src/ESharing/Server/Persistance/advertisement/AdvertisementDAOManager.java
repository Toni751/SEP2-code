package ESharing.Server.Persistance.advertisement;

import ESharing.Server.Persistance.Database;
import ESharing.Server.Persistance.address.AddressDAOManager;
import ESharing.Server.Persistance.user.UserDAO;
import ESharing.Server.Persistance.user.UserDAOManager;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.AdImages;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvertisementDAOManager extends Database implements AdvertisementDAO
{

  private static AdvertisementDAOManager instance;
  private UserDAO userDAO;

  public AdvertisementDAOManager(UserDAO userDAO)
  {
    try
    {
      DriverManager.registerDriver(new org.postgresql.Driver());
      this.userDAO = userDAO;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

//  public static synchronized AdvertisementDAOManager getInstance()
//  {
//    if (instance == null)
//    {
//      instance = new AdvertisementDAOManager();
//    }
//    return instance;
//
//  }

  public Connection getConnection() throws SQLException
  {
    return super.getConnection();
  }

  @Override public int create(Advertisement advertisement)
  {
    System.out.println("Trying to establish connection");
    try (Connection connection = getConnection())
    {
      System.out.println("Connection established");
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO advertisement (title,owner_id,description,price,approved,date_creation,vehicle_type) VALUES (?,?,?,?,?,?,?);");
      statement.setString(1, advertisement.getTitle());
      statement.setInt(2, advertisement.getOwner().getUser_id());
      statement.setString(3, advertisement.getDescription());
      statement.setDouble(4, advertisement.getPrice());
      statement.setBoolean(5, advertisement.ifAdApproved());
      statement.setDate(6, Date.valueOf(LocalDate.now()));
      statement.setString(7, advertisement.getType());
      int affectedRows = statement.executeUpdate();
      System.out.println("Affected rows by create advertisement: " + affectedRows);
      if (affectedRows == 1)
      {
        Statement statementForLastValue = connection.createStatement();
        ResultSet resultSet = statementForLastValue.executeQuery("SELECT last_value FROM advertisement_id_seq;");
        if (resultSet.next())
          return resultSet.getInt("last_value");
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return -1;
  }

  @Override public boolean removeAdvertisement(Advertisement advertisement)
  {

    try (Connection connection = getConnection())
    {
      PreparedStatement statement1 = connection.prepareStatement("DELETE FROM ad_unavailability WHERE advertisement_id = ?;");
      statement1.setInt(1,advertisement.getAdvertisementID());
      statement1.executeUpdate();

      PreparedStatement statement2 = connection.prepareStatement("DELETE FROM ad_picture WHERE advertisement_id = ?;");
      statement2.setInt(1,advertisement.getAdvertisementID());
      statement2.executeUpdate();

      System.out.println("Deleting advertisement at id: " + advertisement.getAdvertisementID());
      PreparedStatement statement = connection.prepareStatement("DELETE FROM advertisement WHERE id = ?;");
      statement.setInt(1, advertisement.getAdvertisementID());
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

  @Override public void addImagesAndDates(Advertisement advertisement)
  {
    addAdvertisementPictures(advertisement.getServerPath(AdImages.MAIN_IMAGE.toString()),
        AdImages.MAIN_IMAGE.toString(), advertisement.getAdvertisementID());
    if (advertisement.getServerPaths().containsKey(AdImages.SUB_IMAGE1.toString()))
      addAdvertisementPictures(advertisement.getServerPath(AdImages.SUB_IMAGE1.toString()),
          AdImages.SUB_IMAGE1.toString(), advertisement.getAdvertisementID());
    if (advertisement.getServerPaths().containsKey(AdImages.SUB_IMAGE2.toString()))
      addAdvertisementPictures(advertisement.getServerPath(AdImages.SUB_IMAGE2.toString()),
          AdImages.SUB_IMAGE2.toString(), advertisement.getAdvertisementID());
    if (advertisement.getServerPaths().containsKey(AdImages.SUB_IMAGE3.toString()))
      addAdvertisementPictures(advertisement.getServerPath(AdImages.SUB_IMAGE3.toString()),
          AdImages.SUB_IMAGE3.toString(), advertisement.getAdvertisementID());
    if (advertisement.getServerPaths().containsKey(AdImages.SUB_IMAGE4.toString()))
      addAdvertisementPictures(advertisement.getServerPath(AdImages.SUB_IMAGE4.toString()),
          AdImages.SUB_IMAGE4.toString(), advertisement.getAdvertisementID());

    for (int i = 0; i < advertisement.getUnavailability().size(); i++)
    {
      System.out.println("for date");
      addAdvertisementUnavailableDates(advertisement.getAdvertisementID(),
          advertisement.getUnavailability().get(i));
    }
  }

  @Override public List<Advertisement> getAllAdvertisements()
  {
    List<Advertisement> advertisements = new ArrayList<>();
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM advertisement;");
      // + "AS ad INNER JOIN ad_picture as pic ON ad.advertisementID = pic.advertisementID INNER JOIN ad_unavailability AS unav ON pic.advertisementID = unav.advertisementID");
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next())
      {
        int advertisementID = resultSet.getInt("advertisement_id");
        List<LocalDate> unavailability = getUnavailabilityForAdvertisement(
            advertisementID);
        String title = resultSet.getString("title");
        int owner_id = resultSet.getInt("owner_id");
        User user = userDAO.readById(owner_id);
        String description = resultSet.getString("description");
        int price = resultSet.getInt("price");
        boolean approved = resultSet.getBoolean("approved");
        Date date_creation = resultSet.getDate(String.valueOf(Date.valueOf(LocalDate.now())));
        String vehicle_type = resultSet.getString("type");
        // int advertisement_id = resultSet.getInt("advertisement_id");
        // String path = resultSet.getString("path");
        // String picture_name =resultSet.getString("picture_name");
        // int advertisementId = resultSet.getInt("advertisement_id");
        // Date unavailable_date = resultSet.getDate(String.valueOf(Date.valueOf(LocalDate.now())));
         Advertisement advertisement = new Advertisement(user,null,vehicle_type,unavailability,price,title,description);
         advertisement.setAdvertisementID(advertisementID);
         advertisement.setServerPath(getPicturesForAdvertisement(advertisementID));
         advertisements.add(advertisement);

      }
      return advertisements;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  @Override public void approveAdvertisement(Advertisement advertisement)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("UPDATE advertisement  SET approved=? WHERE advertisement_id=?");
      statement.setBoolean(1,true);
      statement.setInt(2,advertisement.getAdvertisementID());
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  private void addAdvertisementPictures(String serverPath, String photoName, int advertisementID)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO ad_picture (advertisement_id, path, picture_name) VALUES (?,?,?);");
      statement.setInt(1, advertisementID);
      statement.setString(2, serverPath);
      statement.setString(3, photoName);

      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  private void addAdvertisementUnavailableDates(int advertisementID, LocalDate date)
  {
    try (Connection connection = getConnection())
    {
      System.out.println("date");
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO ad_unavailability (advertisement_id, unavailable_date) VALUES (?,?);");
      statement.setInt(1, advertisementID);
      statement.setDate(2, Date.valueOf(date));

      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  private ArrayList<LocalDate> getUnavailabilityForAdvertisement(int advertisement_id)
  {
    ArrayList<LocalDate> unavailableDates = new ArrayList<>();
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "SELECT * FROM ad_unavailability  WHERE advertisement_id=?;");
      statement.setInt(1, advertisement_id);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next())
      {
        unavailableDates.add(resultSet.getDate("unavailable_date").toLocalDate());
      }
      return unavailableDates;

    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  private Map<String, String> getPicturesForAdvertisement(int advertisement_id)
  {
    Map<String, String> map = new HashMap<>();
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT* FROM ad_picture WHERE advertisement_id=?");
      statement.setInt(1, advertisement_id);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next())
      {
        String name = resultSet.getString("picture_name");
        String serverPath = resultSet.getString("path");
        map.put(name, serverPath);
      }
      return map;

    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;

  }
}

package ESharing.Server.Persistance;

import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.Util.AdImages;

import java.sql.*;
import java.time.LocalDate;

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
      statement.setInt(4, (int) advertisement.getPrice());
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


         //REMOVE PICTURES AND DATES!
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
      return false;
    }

  @Override
  public void addImagesAndDates(Advertisement advertisement) {
    addAdvertisementPictures(advertisement.getServerPath(AdImages.MAIN_IMAGE.toString()), AdImages.MAIN_IMAGE.toString(), advertisement.getAdvertisementID());
    if(advertisement.getServerPaths().containsKey(AdImages.SUB_IMAGE1.toString()))
      addAdvertisementPictures(advertisement.getServerPath(AdImages.SUB_IMAGE1.toString()), AdImages.SUB_IMAGE1.toString(), advertisement.getAdvertisementID());
    if(advertisement.getServerPaths().containsKey(AdImages.SUB_IMAGE2.toString()))
      addAdvertisementPictures(advertisement.getServerPath(AdImages.SUB_IMAGE2.toString()), AdImages.SUB_IMAGE2.toString(), advertisement.getAdvertisementID());
    if(advertisement.getServerPaths().containsKey(AdImages.SUB_IMAGE3.toString()))
      addAdvertisementPictures(advertisement.getServerPath(AdImages.SUB_IMAGE3.toString()), AdImages.SUB_IMAGE3.toString(), advertisement.getAdvertisementID());
    if(advertisement.getServerPaths().containsKey(AdImages.SUB_IMAGE4.toString()))
      addAdvertisementPictures(advertisement.getServerPath(AdImages.SUB_IMAGE4.toString()), AdImages.SUB_IMAGE4.toString(), advertisement.getAdvertisementID());

    for(int i = 0 ; i < advertisement.getUnavailability().size(); i++) {
      System.out.println("for date");
      addAdvertisementUnavailableDates(advertisement.getAdvertisementID(), advertisement.getUnavailability().get(i));
    }
  }


  private void addAdvertisementPictures(String serverPath, String photoName, int advertisementID)
    {
      try (Connection connection = getConnection()){
          PreparedStatement statement = connection.prepareStatement("INSERT INTO ad_picture (advertisement_id, path, picture_name) VALUES (?,?,?);");
          statement.setInt(1, advertisementID);
          statement.setString(2, serverPath);
          statement.setString(3, photoName);

          statement.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    private void addAdvertisementUnavailableDates(int advertisementID, LocalDate date) {
      try(Connection connection = getConnection()){
        System.out.println("date");
        PreparedStatement statement = connection.prepareStatement("INSERT INTO ad_unavailability (advertisement_id, unavailable_date) VALUES (?,?);");
        statement.setInt(1, advertisementID);
        statement.setDate(2, Date.valueOf(date));

        statement.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

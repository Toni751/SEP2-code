package ESharing.Server.Persistance.advertisement;

import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Server.Persistance.Database;
import ESharing.Server.Persistance.address.AddressDAO;
import ESharing.Server.Persistance.address.AddressDAOManager;
import ESharing.Server.Persistance.message.MessageDAO;
import ESharing.Server.Persistance.reservation.ReservationDAO;
import ESharing.Server.Persistance.user.UserDAO;
import ESharing.Server.Persistance.user.UserDAOManager;
import ESharing.Shared.TransferedObject.*;
import ESharing.Shared.Util.AdImages;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvertisementDAOManager extends Database implements AdvertisementDAO
{

  private UserDAO userDAO;
  private ReservationDAO reservationDAO;
  private MessageDAO messageDAO;

  public AdvertisementDAOManager(UserDAO userDAO, ReservationDAO reservationDAO,MessageDAO messageDAO)
  {
    this.userDAO = userDAO;
    this.reservationDAO = reservationDAO;
    this.messageDAO = messageDAO;
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

  @Override public List<Message> removeAdvertisement(int id)
  {
    int ownerId = -1;
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM advertisement WHERE id = ?;");
      statement.setInt(1, id);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next())
      {
        ownerId = resultSet.getInt("owner_id");
      }
      List<Message> messages = notifyUsersWithReservation(id);

      PreparedStatement statement1 = connection.prepareStatement(
          "DELETE FROM ad_unavailability WHERE advertisement_id = ?;");
      statement1.setInt(1, id);
      statement1.executeUpdate();

      PreparedStatement statement2 = connection.prepareStatement(
          "DELETE FROM ad_picture WHERE advertisement_id = ?;");
      statement2.setInt(1, id);
      statement2.executeUpdate();

      PreparedStatement ratingStatement = connection.prepareStatement(
              "DELETE FROM ratings WHERE advertisement_id = ?;");
      ratingStatement.setInt(1, id);
      ratingStatement.executeUpdate();



      System.out.println("Deleting advertisement at id: " + id);
      PreparedStatement statement3 = connection.prepareStatement("DELETE FROM advertisement WHERE id = ?;");
      statement3.setInt(1, id);
      int affectedRows = statement3.executeUpdate();
      if (affectedRows == 1)
        return messages;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  private List<Message> notifyUsersWithReservation(int id)
  {
    ArrayList<Message> messages = new ArrayList<>();
    try (Connection connection = getConnection())
    {
      ArrayList<Integer> ids = new ArrayList<>();
      PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT user_id FROM ad_unavailability WHERE advertisement_id=? AND unavailable_date >= NOW()::DATE AND user_id IS NOT NULL ORDER BY user_id;");
      statement.setInt(1,id);
      ResultSet resultSet = statement.executeQuery();
      System.out.println("IDIDIDIDIDI : " + id);
      while (resultSet.next())
      {
        System.out.println("XXXXXXXXXXXXXXXXXXX"+ resultSet.getInt("user_id"));
        ids.add(resultSet.getInt("user_id"));
      }
      System.out.println("IDS " + ids);
      PreparedStatement statement1 = connection.prepareStatement("SELECT owner_id FROM advertisement WHERE id=?;");
      statement1.setInt(1,id);

      ResultSet resultSet1 = statement1.executeQuery();
      int owner_id=0;
      if (resultSet1.next())
      {
        owner_id = resultSet1.getInt("owner_id");
        System.out.println("OWNER" + owner_id);
      }
      User sender = userDAO.readById(owner_id);
      System.out.println("SENDER" + sender);
      User receiver = null;
      System.out.println("IDSSSSSSSS" + ids.size());
        for (int i = 0; i < ids.size(); i++) {
          System.out.println("Reservation removed ");
          receiver = userDAO.readById(ids.get(i));
          Message message = new Message(sender, receiver, "your reservation was canceled", false);
          messageDAO.addMessage(message);
          messages.add(message);
      }
      if(messages.isEmpty()){
        messages.add(new Message(sender, sender, "", false));
      }
      return messages;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
   return null;
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
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next())
      {
        int advertisementID = resultSet.getInt("id");
        List<LocalDate> unavailability = getUnavailabilityForAdvertisement(
            advertisementID);
        String title = resultSet.getString("title");
        int owner_id = resultSet.getInt("owner_id");
        User user = userDAO.readById(owner_id);
        String description = resultSet.getString("description");
        int price = resultSet.getInt("price");
        int reports = resultSet.getInt("reports");
        boolean approved = resultSet.getBoolean("approved");
        Date date_creation = resultSet.getDate("date_creation");
        String vehicle_type = resultSet.getString("vehicle_type");
        Advertisement advertisement = new Advertisement(user, null, vehicle_type,
            unavailability, price, title, description);
        advertisement.setAdvertisementID(advertisementID);
        advertisement.setReports(reports);
        advertisement.setCreationDate(date_creation.toLocalDate());
        if (approved)
          advertisement.setAdApproved();
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

  @Override public CatalogueAd approveAdvertisement(int id)
  {
    try (Connection connection = getConnection())
    {

      PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM advertisement WHERE id = ?;");
      selectStatement.setInt(1, id);
      ResultSet resultSet = selectStatement.executeQuery();
      while (resultSet.next())
      {
        int advertisementID = resultSet.getInt("id");
        String title = resultSet.getString("title");
        int price = resultSet.getInt("price");
        String vehicle_type = resultSet.getString("vehicle_type");
        String mainImageServerPath = null;

        PreparedStatement mainImageStatement = connection.prepareStatement(
            "SELECT * FROM ad_picture WHERE advertisement_id = ? AND picture_name = ?;");
        mainImageStatement.setInt(1, advertisementID);
        mainImageStatement.setString(2, AdImages.MAIN_IMAGE.toString());
        ResultSet mainImageResult = mainImageStatement.executeQuery();
        while (mainImageResult.next())
        {
          mainImageServerPath = mainImageResult.getString("path");
        }

        PreparedStatement statement = connection.prepareStatement(
            "UPDATE advertisement  SET approved=? WHERE id=?");
        statement.setBoolean(1, true);
        statement.setInt(2, id);
        statement.executeUpdate();

        return new CatalogueAd(id, title, mainImageServerPath, price,
            vehicle_type);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  @Override public List<CatalogueAd> getAdvertisementsCatalogue()
  {
    List<CatalogueAd> catalogueAds = new ArrayList<>();
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM advertisement WHERE approved = ?;");
      statement.setBoolean(1, true);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next())
      {
        int advertisementID = resultSet.getInt("id");
        String title = resultSet.getString("title");
        int price = resultSet.getInt("price");
        String vehicle_type = resultSet.getString("vehicle_type");
        String mainImageServerPath = null;

        PreparedStatement mainImageStatement = connection.prepareStatement(
            "SELECT * FROM ad_picture WHERE advertisement_id = ? AND picture_name = ?;");
        mainImageStatement.setInt(1, advertisementID);
        mainImageStatement.setString(2, AdImages.MAIN_IMAGE.toString());
        ResultSet mainImageResult = mainImageStatement.executeQuery();
        while (mainImageResult.next())
        {
          mainImageServerPath = mainImageResult.getString("path");
        }
        catalogueAds.add(
            new CatalogueAd(advertisementID, title, mainImageServerPath, price,
                vehicle_type));
      }
      System.out.println(catalogueAds);
      return catalogueAds;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  @Override public Advertisement getAdvertisementById(int id)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM advertisement WHERE id=?;");
      statement.setInt(1, id);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next())
      {
        String title = resultSet.getString("title");
        String type = resultSet.getString("vehicle_type");
        int price = resultSet.getInt("price");
        String description = resultSet.getString("description");
        LocalDate dateCreation = resultSet.getDate("date_creation").toLocalDate();
        int reports = resultSet.getInt("reports");
        int ownerID = resultSet.getInt("owner_id");

        String username = null;
        String phoneNumber = null;
        String avatarpath = null;
        int address_id = 0;
        int owner_id = 0;

        Map<String, String> pictures = getPicturesForAdvertisement(id);
        ArrayList<LocalDate> unavailableDates = getUnavailabilityForAdvertisement(
            id);

        PreparedStatement ownerStatement = connection.prepareStatement("SELECT * FROM user_account WHERE id=?;");
        ownerStatement.setInt(1, ownerID);
        ResultSet ownerResult = ownerStatement.executeQuery();
        while (ownerResult.next())
        {
          username = ownerResult.getString("username");
          phoneNumber = ownerResult.getString("phoneno");
          avatarpath = ownerResult.getString("avatarpath");
          address_id = ownerResult.getInt("address_id");
        }

        String street = null;
        String number = null;

        PreparedStatement addressStatement = connection.prepareStatement("SELECT * FROM address WHERE address_id=?;");
        addressStatement.setInt(1, address_id);
        ResultSet addressResult = addressStatement.executeQuery();
        while (ownerResult.next())
        {
          street = addressResult.getString("street");
          number = addressResult.getString("number");
        }

        Address ownerAddress = new Address(street, number);
        User owner = new User(username, null, phoneNumber, ownerAddress);
        owner.setAvatarServerPath(avatarpath);
        owner.setUser_id(ownerID);
        Advertisement advertisement = new Advertisement(owner, null, type,
            unavailableDates, price, title, description);
        advertisement.setServerPath(pictures);
        advertisement.setAdvertisementID(id);
        advertisement.setReports(reports);
        advertisement.setCreationDate(dateCreation);

        System.out.println("ADvertismen" + id);

        return advertisement;
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  @Override public int addNewAdvertisementReport(int advertisementID)
  {
    try (Connection connection = getConnection())
    {

      PreparedStatement statement = connection.prepareStatement("SELECT * FROM advertisement WHERE id=?;");
      statement.setInt(1, advertisementID);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next())
      {
        int reports = resultSet.getInt("reports");
        reports++;
        PreparedStatement updateStatement = connection.prepareStatement(
            "UPDATE advertisement SET reports =? WHERE id =?;");
        updateStatement.setInt(1, reports);
        updateStatement.setInt(2, advertisementID);

        if (updateStatement.executeUpdate() == 1)
          return reports;
      }

    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return -1;
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

  @Override public List<CatalogueAd> getAdvertisementsByUser(int user_id)
  {
    List<CatalogueAd> catalogueAdUser = new ArrayList<>();
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "SELECT id,title,vehicle_type,price FROM advertisement WHERE owner_id = ?;");
      statement.setInt(1, user_id);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next())
      {
        int advertisementID = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String type = resultSet.getString("vehicle_type");
        int price = resultSet.getInt("price");

        Map<String, String> map = getPicturesForAdvertisement(advertisementID);
        String mainImageServerPath = map.get(AdImages.MAIN_IMAGE.toString());

        CatalogueAd catalogueAd = new CatalogueAd(advertisementID, title,
            mainImageServerPath, price, type);
        catalogueAd.setReservations(reservationDAO.getReservationForAdvertisement(advertisementID));

        catalogueAdUser.add(catalogueAd);
      }
      return catalogueAdUser;
    }

    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  @Override public List<AdCatalogueAdmin> getAdminAdCatalogue()
  {
    List<AdCatalogueAdmin> catalogueAdAdmin = new ArrayList<>();
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "SELECT id,title,vehicle_type,reports,approved FROM advertisement;");
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next())
      {
        int advertisementID = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String type = resultSet.getString("vehicle_type");
        int reports = resultSet.getInt("reports");
        boolean approved = resultSet.getBoolean("approved");

        catalogueAdAdmin.add(
            new AdCatalogueAdmin(advertisementID, title, type, reports, approved));
      }
      System.out.println(catalogueAdAdmin);
      return catalogueAdAdmin;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  @Override public AdCatalogueAdmin getAdminAdCatalogue(int id)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "SELECT id,title,vehicle_type,reports,approved FROM advertisement;");
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next())
      {
        int advertisementID = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String type = resultSet.getString("vehicle_type");
        int reports = resultSet.getInt("reports");
        boolean approved = resultSet.getBoolean("approved");

        return new AdCatalogueAdmin(id, title, type, reports, approved);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  @Override public boolean addRating(int ad_id, int user_id, double rating)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO ratings (advertisement_id,user_id,score) VALUES (?, ?, ?) "
              + "ON CONFLICT ON CONSTRAINT unique_rating DO NOTHING;");
      statement.setInt(1, ad_id);
      statement.setInt(2, user_id);
      statement.setDouble(3, rating);

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

  @Override public double retrieveAdRating(int ad_id)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "SELECT AVG(score) AS average FROM ratings WHERE advertisement_id = ?;");
      statement.setInt(1, ad_id);
      ResultSet resultSet = statement.executeQuery();
      double score =0;
      if(resultSet.next())
        score = resultSet.getDouble("average");
      return score;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return 0;
  }
}

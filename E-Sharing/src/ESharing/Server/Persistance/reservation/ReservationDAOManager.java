package ESharing.Server.Persistance.reservation;


import ESharing.Server.Persistance.Database;
import ESharing.Server.Persistance.user.UserDAO;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.TransferedObject.Reservation;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.AdImages;

import javax.lang.model.type.NullType;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The DAO manager class for handling database requests regarding reservations
 * @version 1.0
 * @author Group1
 */
public class ReservationDAOManager extends Database implements ReservationDAO{

    private UserDAO userDAO;

    /**
     * One argument constructor which initializes the user DAO
     * @param userDAO the value to be set for the user DAO
     */
    public ReservationDAOManager(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public Connection getConnection() throws SQLException
    {
        return super.getConnection();
    }

    @Override
    public boolean makeNewReservation(Reservation reservation)
    {
        System.out.println("Trying to establish connection");
        try (Connection connection = getConnection()) {
            System.out.println("Connection established");
            for (int i = 0; i < reservation.getReservationDays().size(); i++) {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO ad_unavailability(advertisement_id, unavailable_date, user_id) VALUES (?,?,?);");
                statement.setInt(1, reservation.getAdvertisementID());
                statement.setDate(2, Date.valueOf(reservation.getReservationDays().get(i)));
                statement.setInt(3, reservation.getRequestedUser().getUser_id());
                statement.executeUpdate();
                System.out.println("Unavaialble date added");
            }
            System.out.println("NEW reservation created");
            return true;
        }
        catch (SQLException e) {e.printStackTrace();}
        return false;
    }

    @Override
    public boolean removeReservation(int advertisementID, int userID) {
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM ad_unavailability WHERE advertisement_id = ? AND user_id=?;");
            statement.setInt(1, advertisementID);
            statement.setInt(2,userID);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0)
                return true;
        }
        catch (SQLException e){e.printStackTrace();}
        return false;
    }

    @Override
    public List<Reservation> getUserReservations(int userID) {
        List<Reservation> userReservations = new ArrayList<>();
        List<Integer> advertisementIDs = new ArrayList<>();
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT advertisement_id FROM ad_unavailability WHERE user_id = ?;");
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int advertisementID = resultSet.getInt("advertisement_id");
                if(!advertisementIDs.contains(advertisementID))
                    advertisementIDs.add(advertisementID);
            }

            System.out.println("RESERVATIONS: " + advertisementIDs);

            for(int i = 0 ; i < advertisementIDs.size() ; i++)
            {
                List<LocalDate> reservationDays = new ArrayList<>();
                String ownerUsername = null;
                double price = 0;
                String advertisementTitle = null;
                int ownerID = 0;


                PreparedStatement reservationDaysStatement = connection.prepareStatement(
                "SELECT unavailable_date FROM ad_unavailability WHERE user_id = ? AND advertisement_id = ?;");
                reservationDaysStatement.setInt(1, userID);
                reservationDaysStatement.setInt(2, advertisementIDs.get(i));
                ResultSet daysResult = reservationDaysStatement.executeQuery();
                while (daysResult.next()){
                    Date reservationDay = daysResult.getDate("unavailable_date");
                    reservationDays.add(reservationDay.toLocalDate());
                }

                PreparedStatement advertisementStatement = connection.prepareStatement("" +
                        "SELECT title, price, owner_id FROM advertisement WHERE id =?;");
                advertisementStatement.setInt(1, advertisementIDs.get(i));
                ResultSet advertisementResult = advertisementStatement.executeQuery();
                while (advertisementResult.next()){
                    advertisementTitle = advertisementResult.getString("title");
                    price = advertisementResult.getDouble("price");
                    ownerID = advertisementResult.getInt("owner_id");
                }

                PreparedStatement ownerStatement = connection.prepareStatement("" +
                        "SELECT username From user_account WHERE id=?;");
                ownerStatement.setInt(1, ownerID);
                ResultSet ownerResult = ownerStatement.executeQuery();
                while (ownerResult.next()){
                    ownerUsername = ownerResult.getString("username");
                }

                User user = userDAO.readById(userID);

                userReservations.add(new Reservation(advertisementIDs.get(i), advertisementTitle, ownerUsername, user, price, reservationDays));
            }

            System.out.println(userReservations);
            return userReservations;
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Reservation> getReservationForAdvertisement(int advertisementID) {
        List<Reservation> reservationAd = new ArrayList<>();
        List<Integer> reservationUsersId = new ArrayList<>();
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT user_id FROM ad_unavailability WHERE advertisement_id = ? AND user_id IS NOT NULL;");
            statement.setInt(1, advertisementID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                int userId = resultSet.getInt("user_id");
                if(!reservationUsersId.contains(userId))
                    reservationUsersId.add(userId);
            }


            for (Integer integer : reservationUsersId) {
                PreparedStatement reservationStatement = connection.prepareStatement(
                        "SELECT unavailable_date FROM ad_unavailability WHERE advertisement_id = ? AND user_id = ?");
                reservationStatement.setInt(1, advertisementID);
                reservationStatement.setInt(2, integer);
                ResultSet reservationResult = reservationStatement.executeQuery();
                List<LocalDate> reservation = new ArrayList<>();
                while (reservationResult.next()) {
                    reservation.add(reservationResult.getDate("unavailable_date").toLocalDate());
                }
                User user = userDAO.readById(integer);

                reservationAd.add(new Reservation(advertisementID, null,null, user,0, reservation));
            }

            System.out.println(reservationAd);
            return reservationAd;
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<LocalDate> getUserReservation(int advertisementID, int userID) {
        List<LocalDate> reservationDays = new ArrayList<>();
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT unavailable_date FROM ad_unavailability WHERE advertisement_id = ? AND user_id=?;");
            statement.setInt(1, advertisementID);
            statement.setInt(2,userID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                reservationDays.add(resultSet.getDate("unavailable_date").toLocalDate());
            }
            return reservationDays;
        }
        catch (SQLException e){e.printStackTrace();}
        return null;
    }

}

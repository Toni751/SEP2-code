package ESharing.Server.Persistance.reservation;


import ESharing.Server.Persistance.Database;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.TransferedObject.Reservation;
import ESharing.Shared.Util.AdImages;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReservationDAOManager extends Database implements ReservationDAO{



    public Connection getConnection() throws SQLException
    {
        return super.getConnection();
    }


    @Override
    public int makeNewReservation(Reservation reservation)
    {

        System.out.println("Trying to establish connection");
        try (Connection connection = getConnection())
        {
            System.out.println("Connection established");
            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO reservation (advertisementID, userID, totalPrice,reservationDays) VALUES (?,?,?,?);");
            statement.setInt(1, reservation.getAdvertisementID());
            statement.setInt(2, reservation.getUserID());
            statement.setDouble(3, reservation.getTotalPrice());
            statement.setString(4, reservation.getReservationDays().toString());
            int affectedRows = statement.executeUpdate();
            System.out.println("Affected rows by create reservation: " + affectedRows);
            if (affectedRows == 1)
            {
                Statement statementForLastValue = connection.createStatement();
                ResultSet resultSet = statementForLastValue.executeQuery("SELECT last_value FROM reservation;");
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

    @Override
    public int removeReservation(int advertisementID, int userID) {

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM advertisement WHERE advertisementID = ?,userId=? ;");
            statement.setInt(1, advertisementID);
            statement.setInt(2,userID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                advertisementID = resultSet.getInt("advertisementID");
                userID = resultSet.getInt("userID");
            }

            PreparedStatement statement1 = connection.prepareStatement("DELETE FROM reservation WHERE  WHERE advertisementID = ?,userId=?;");
            statement.setInt(1, advertisementID);
            statement.setInt(2,userID);
            statement1.executeUpdate();
            int affectedRows = statement1.executeUpdate();
            if (affectedRows == 1)
                return advertisementID;
            if (affectedRows == 2)
                return userID;


        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<Reservation> getUserReservations(int userID) {
        List<Reservation> userReservations = new ArrayList<>();
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT advertisementID,totalPrice,rentalDays FROM reservation WHERE userID = ?;");
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                int advertisementID = resultSet.getInt("id");
                int totalPrice = resultSet.getInt("price");
                List<LocalDate> rentalDays = (List<LocalDate>) resultSet.getDate("rentalDays");


               userReservations.add(
                    new Reservation(advertisementID,totalPrice,rentalDays));
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
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT userID,totalPrice,rentalDays FROM reservation WHERE advertisementID = ?;");
            statement.setInt(1, advertisementID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                int userID = resultSet.getInt("userId");
                int totalPrice = resultSet.getInt("price");
                List<LocalDate> rentalDays = (List<LocalDate>) resultSet.getDate("rentalDays");


                reservationAd.add(
                    new Reservation(userID,totalPrice,rentalDays));
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
}

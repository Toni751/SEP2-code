package ESharing.Server.Persistance.reservation;

import ESharing.Shared.TransferedObject.Reservation;

import java.time.LocalDate;
import java.util.List;

/**
 * The DAO interface for managing database requests regarding reservations
 * @version 1.0
 * @author Group1
 */
public interface ReservationDAO {

    /**
     * Adds a new reservation to the database
     * @param reservation the reservation to be added
     * @return true if the reservation was added successfully, false otherwise
     */
    boolean makeNewReservation(Reservation reservation);

    /**
     * Removes a reservation from the database
     * @param advertisementID the id of the advertisement
     * @param userID the id of the user who had the reservation
     * @return true if the reservation was removed successfully, false otherwise
     */
    boolean removeReservation(int advertisementID, int userID);

    /**
     * Retrieves all the reservations a user has made
     * @param userID the id of the user
     * @return the list with all the user's reservations
     */
    List<Reservation> getUserReservations(int userID);

    /**
     * Retrieves all the reservations made of an advertisement
     * @param advertisementID the id of the advertisement
     * @return the list with all the advertisement's reservations
     */
    List<Reservation> getReservationForAdvertisement(int advertisementID);

    /**
     * Retrieves the dates when a user reserved an advertisement
     * @param advertisementID the given advertisement id
     * @param userID the given user id
     * @return the list with all the dates when the user reserved the vehicle
     */
    List<LocalDate> getUserReservation(int advertisementID, int userID);
}

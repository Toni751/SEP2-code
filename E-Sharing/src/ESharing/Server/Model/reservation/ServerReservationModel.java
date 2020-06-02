package ESharing.Server.Model.reservation;

import ESharing.Shared.TransferedObject.Reservation;
import ESharing.Shared.Util.PropertyChangeSubject;

import java.time.LocalDate;
import java.util.List;

/**
 * The interface for the server reservation model
 * @version 1.0
 * @author Group1
 */
public interface ServerReservationModel extends PropertyChangeSubject {
    /**
     * Sends a request to the DAO for adding a new reservation to the database
     * @param reservation the reservation to be added
     * @return true if the reservation was added to the database, false otherwise
     */
    boolean makeNewReservation(Reservation reservation);

    /**
     * Sends a request to the DAO for removing a reservation from the database
     * @param advertisementID the id of the reserved advertisement
     * @param userID the id of the user who made the reservation
     * @return true if the reservation was successfully deleted, false otherwise
     */
    boolean removeReservation(int advertisementID, int userID);

    /**
     * Retrieves from the DAO a list with all the reservations a user has
     * @param userID the id of the user
     * @return the list with all the user's reservations
     */
    List<Reservation> getUserReservations(int userID);

    /**
     * Retrieves from the DAO a list with the reservations an advertisement has
     * @param advertisementID the id of the advertisement
     * @return the list with all the advertisement's reservations
     */
    List<Reservation> getReservationForAdvertisement(int advertisementID);
}

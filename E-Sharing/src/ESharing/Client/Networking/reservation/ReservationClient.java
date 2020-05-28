package ESharing.Client.Networking.reservation;

import ESharing.Shared.TransferedObject.Reservation;
import ESharing.Shared.Util.PropertyChangeSubject;
import java.util.List;

/**
 * The interface from networking layer which is responsible for sending and receiving requests related to reservation to and form the server
 * and for sending/ receiving data to the server
 * @version 1.0
 * @author Group1
 */
public interface ReservationClient extends PropertyChangeSubject {

    /**
     * Initializes connection with a server
     */
    void initializeConnection();

    /**
     * Sends a request for making new reservation
     * @param reservation the given reservation
     * @return the result of the making process
     */
    boolean makeNewReservation(Reservation reservation);

    /**
     * Sends a request for removing a reservation
     * @param advertisementID the given reservation
     * @param userID the id of the advertisement owner
     * @return the result of the removing part
     */
    boolean removeReservation(int advertisementID, int userID);

    /**
     * Returns the list of all user reservation
     * @param userID the id of the user
     * @return the list of all user reservation
     */
    List<Reservation> getUserReservations(int userID);

    /**
     * Returns the list of all advertisement reservation
     * @param advertisementID the id of the advertisement
     * @return the list of all advertisement reservation
     */
    List<Reservation> getReservationForAdvertisement(int advertisementID);

    /**
     * Registers all call backs from the server
     */
    void registerForCallback();
}

package ESharing.Client.Model.ReservationModel;

import ESharing.Shared.TransferedObject.Reservation;
import ESharing.Shared.Util.PropertyChangeSubject;
import java.util.List;

/**
 * The interface used to connect a view model layer with a model layer for all reservation features.
 * @version 1.0
 * @author Group1
 */
public interface ReservationModel extends PropertyChangeSubject {

    /**
     * Sends a request to the networking part for creating new reservation
     * @param reservation the new created reservation object
     * @return the result of the creation process
     */
    boolean makeNewReservation(Reservation reservation);

    /**
     * Sends a request to the networking part for removing the given advertisement
     * @param advertisementID the id of the given reservation
     * @param userID the id of the advertisement owner
     * @return the result of the removing process
     */
    boolean removeReservation(int advertisementID, int userID);

    /**
     * Returns a list with all reservations for the given user
     * @param userID the id of the given user
     * @return a list with all reservations for the given user
     */
    List<Reservation> getUserReservations(int userID);

    /**
     * Returns a list with all reservations which are a part of the given advertisement
     * @param advertisementID the id of the given advertisement
     * @return the list with all reservations which are a part of the given advertisement
     */
    List<Reservation> getReservationForAdvertisement(int advertisementID);
}

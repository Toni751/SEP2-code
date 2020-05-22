package ESharing.Server.Model.reservation;

import ESharing.Shared.TransferedObject.Reservation;
import ESharing.Shared.Util.PropertyChangeSubject;

import java.util.List;

public interface ServerReservationModel extends PropertyChangeSubject {
    boolean makeNewReservation(Reservation reservation);
    boolean removeReservation(int advertisementID, int userID);
    List<Reservation> getUserReservations(int userID);
    List<Reservation> getReservationForAdvertisement(int advertisementID);
}

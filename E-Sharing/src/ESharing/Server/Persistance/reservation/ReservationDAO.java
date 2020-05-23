package ESharing.Server.Persistance.reservation;

import ESharing.Shared.TransferedObject.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO {

    boolean makeNewReservation(Reservation reservation);
    boolean removeReservation(int advertisementID, int userID);
    List<Reservation> getUserReservations(int userID);
    List<Reservation> getReservationForAdvertisement(int advertisementID);
    List<LocalDate> getUserReservation(int advertisementID, int userID);
}

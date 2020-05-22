package ESharing.Server.Persistance.reservation;


import ESharing.Shared.TransferedObject.Reservation;

import java.time.LocalDate;
import java.util.List;

public class ReservationDAOManager implements ReservationDAO{


    @Override
    public boolean makeNewReservation(Reservation reservation) {
        return false;
    }

    @Override
    public boolean removeReservation(int advertisementID, int userID) {
        return false;
    }

    @Override
    public List<Reservation> getUserReservations(int userID) {
        return null;
    }

    @Override
    public List<Reservation> getReservationForAdvertisement(int advertisementID) {
        return null;
    }
}

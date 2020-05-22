package ESharing.Server.Networking;

import ESharing.Shared.Networking.reservation.RMIReservationServer;
import ESharing.Shared.TransferedObject.Reservation;

import java.rmi.RemoteException;
import java.util.List;

public class ServerReservationHandler implements RMIReservationServer {

    @Override
    public boolean makeNewReservation(Reservation reservation) throws RemoteException {
        return false;
    }

    @Override
    public boolean removeReservation(int advertisementID, int userID) throws RemoteException {
        return false;
    }

    @Override
    public List<Reservation> getUserReservations(int userID) throws RemoteException {
        return null;
    }

    @Override
    public List<Reservation> getReservationForAdvertisement(int advertisementID) throws RemoteException {
        return null;
    }
}

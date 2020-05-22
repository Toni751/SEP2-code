package ESharing.Shared.Networking.reservation;

import ESharing.Shared.TransferedObject.Reservation;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMIReservationServer extends Remote {
    boolean makeNewReservation(Reservation reservation) throws RemoteException;
    boolean removeReservation(int advertisementID, int userID) throws RemoteException;
    List<Reservation> getUserReservations(int userID) throws RemoteException;
    List<Reservation> getReservationForAdvertisement(int advertisementID) throws RemoteException;
}

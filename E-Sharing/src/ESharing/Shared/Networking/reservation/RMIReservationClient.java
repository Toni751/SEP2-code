package ESharing.Shared.Networking.reservation;

import ESharing.Shared.TransferedObject.Reservation;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIReservationClient extends Remote {

    void newReservationCreated(Reservation reservation) throws RemoteException;
    void reservationRemoved(int advertisementID, int userID) throws RemoteException;
}

package ESharing.Shared.Networking.reservation;

import ESharing.Shared.TransferedObject.Reservation;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;

public interface RMIReservationClient extends Remote {

    void newReservationCreated(Reservation reservation) throws RemoteException;
    void reservationRemoved(int[] idArray, List<LocalDate> removedDays) throws RemoteException;
}

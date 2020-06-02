package ESharing.Shared.Networking.reservation;

import ESharing.Shared.TransferedObject.Reservation;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;

/**
 * RMI networking interface for handling callbacks on the client regarding reservations
 * @version 1.0
 * @author Group1
 */
public interface RMIReservationClient extends Remote {

    /**
     * Notifies the client's networking about a new reservation
     * @param reservation the new reservation
     * @throws RemoteException if the remote method invocation fails
     */
    void newReservationCreated(Reservation reservation) throws RemoteException;

    /**
     * Notifies the client's networking about a newly removed reservation
     * @param idArray the array with the ids
     * @param removedDays the removed reservation days
     * @throws RemoteException if the remote method invocation fails
     */
    void reservationRemoved(int[] idArray, List<LocalDate> removedDays) throws RemoteException;
}

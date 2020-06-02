package ESharing.Shared.Networking.reservation;

import ESharing.Shared.Networking.advertisement.RMIAdvertisementClient;
import ESharing.Shared.TransferedObject.Reservation;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * RMI networking interface for remote method invocation on the server regarding reservations
 * @version 1.0
 * @author Group1
 */
public interface RMIReservationServer extends Remote
{
    /**
     * Sends a request to the server to add a new reservation
     * @param reservation the new reservation to be added
     * @return true if the reservation was added successfully, false otherwise
     * @throws RemoteException if the remote method invocation fails
     */
    boolean makeNewReservation(Reservation reservation) throws RemoteException;

    /**
     * Sends a request to the server to remove a reservation
     * @param advertisementID the id of the advertisement
     * @param userID the id of the user
     * @return true if the reservation was removed successfully, false otherwise
     * @throws RemoteException if the remote method invocation fails
     */
    boolean removeReservation(int advertisementID, int userID) throws RemoteException;

    /**
     * Sends a request to the server to retrieve a user's reservations
     * @param userID the id of the user
     * @return the list with all the user's reservations
     * @throws RemoteException if the remote method invocation fails
     */
    List<Reservation> getUserReservations(int userID) throws RemoteException;

    /**
     * Sends a request to the server to retrieve an advertisement's reservations
     * @param advertisementID the id of the advertisement
     * @return the list with all the user's advertisements
     * @throws RemoteException if the remote method invocation fails
     */
    List<Reservation> getReservationForAdvertisement(int advertisementID) throws RemoteException;

    /**
     * Sends a request to the server to register the client for callback regarding advertisements
     * @param client the client to be registered for callback methods
     * @throws RemoteException if the remote method invocation fails
     */
    void registerClientCallback (RMIAdvertisementClient client) throws RemoteException;

    /**
     * Sends a request to the server to register the client for callback regarding reservations
     * @param reservationClient the client to be registered for callback methods
     * @throws RemoteException if the remote method invocation fails
     */
    void registerCallback(RMIReservationClient reservationClient) throws RemoteException;

    /**
     * Sends a request to the server to register the client for callback methods
     * @throws RemoteException if the remote method invocation fails
     */
    void unRegisterCallback() throws RemoteException;
}

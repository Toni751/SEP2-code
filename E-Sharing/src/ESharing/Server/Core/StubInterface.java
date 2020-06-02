package ESharing.Server.Core;

import ESharing.Shared.Networking.advertisement.RMIAdvertisementServer;
import ESharing.Shared.Networking.chat.RMIChatServer;
import ESharing.Shared.Networking.reservation.RMIReservationServer;
import ESharing.Shared.Networking.user.RMIUserServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The interface for the stub factory, which the client will have as a reference variable after downloading
 * @version 1.0
 * @author Group1
 */
public interface StubInterface extends Remote {
    /**
     * Thread-safely initializes the server chat handler if it is null, and then returns it
     * @return the server chat handler
     * @throws RemoteException if connection fails
     */
    RMIChatServer getServerChatHandler() throws RemoteException;

    /**
     * Thread-safely initializes the server user handler if it is null, and then returns it
     * @return the server user handler
     * @throws RemoteException if connection fails
     */
    RMIUserServer getUserServerRMI() throws RemoteException;

    /**
     * Thread-safely initializes the server advertisement handler if it is null, and then returns it
     * @return the server advertisement handler
     * @throws RemoteException if connection fails
     */
    RMIAdvertisementServer getServerAdHandler() throws RemoteException;

    /**
     * Thread-safely initializes the server reservation handler if it is null, and then returns it
     * @return the server reservation handler
     * @throws RemoteException if connection fails
     */
    RMIReservationServer getServerReservation() throws RemoteException;
}

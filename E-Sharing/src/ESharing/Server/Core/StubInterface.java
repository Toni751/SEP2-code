package ESharing.Server.Core;

import ESharing.Shared.Networking.advertisement.RMIAdvertisementServer;
import ESharing.Shared.Networking.chat.RMIChatServer;
import ESharing.Shared.Networking.reservation.RMIReservationServer;
import ESharing.Shared.Networking.user.RMIUserServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StubInterface extends Remote {
    RMIChatServer getServerChatHandler() throws RemoteException;
    RMIUserServer getServerRMI() throws RemoteException;
    RMIAdvertisementServer getServerAdHandler() throws RemoteException;
    RMIReservationServer getServerReservation() throws RemoteException;
}

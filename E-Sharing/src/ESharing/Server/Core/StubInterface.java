package ESharing.Server.Core;

import ESharing.Shared.Networking.chat.RMIChatServer;
import ESharing.Shared.Networking.user.RMIServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StubInterface extends Remote {
    public RMIChatServer getServerChatHandler() throws RemoteException;
    public RMIServer getServerRMI() throws RemoteException;
}

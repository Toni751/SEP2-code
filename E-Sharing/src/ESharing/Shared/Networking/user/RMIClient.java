package ESharing.Shared.Networking.user;

import ESharing.Shared.TransferedObject.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The interface used by the client handler for remote method calling on the server
 * @version 1.0
 * @author Group1
 */
public interface RMIClient extends Remote
{
    void newUserReceived(User newUser) throws RemoteException;

    void userRemoved(User user) throws RemoteException;

    void userUpdated(User user) throws RemoteException;

    void avatarUpdated(byte[] avatar) throws RemoteException;
}

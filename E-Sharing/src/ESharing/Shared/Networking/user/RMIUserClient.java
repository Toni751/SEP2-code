package ESharing.Shared.Networking.user;

import ESharing.Shared.TransferedObject.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The interface used by the client handler for remote method calling on the server
 * @version 1.0
 * @author Group1
 */
public interface RMIUserClient extends Remote
{
    /**
     * Notifies the client's networking about a new user
     * @param newUser the new user
     * @throws RemoteException if the remote method invocation fails
     */
    void newUserReceived(User newUser) throws RemoteException;

    /**
     * Notifies the client's networking about a newly removed user
     * @param user the removed user
     * @throws RemoteException if the remote method invocation fails
     */
    void userRemoved(User user) throws RemoteException;

    /**
     * Notifies the client's networking about an updated user
     * @param user the updated user
     * @throws RemoteException if the remote method invocation fails
     */
    void userUpdated(User user) throws RemoteException;

    /**
     * Notifies the client's networking about an user's updated avatar
     * @param userId the id of the user
     * @param avatar the new image for the avatar
     * @throws RemoteException if the remote method invocation fails
     */
    void avatarUpdated(int userId, byte[] avatar) throws RemoteException;

    /**
     * Notifies the client's networking about a newly reported user
     * @param userID the reported user
     * @param reports the user's current number of reports
     * @throws RemoteException if the remote method invocation fails
     */
    void userReported(int userID, int reports) throws RemoteException;
}

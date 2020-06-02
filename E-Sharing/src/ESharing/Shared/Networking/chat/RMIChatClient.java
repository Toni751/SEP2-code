package ESharing.Shared.Networking.chat;

import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI networking interface for handling callbacks on the client regarding the chat
 * @version 1.0
 * @author Group1
 */
public interface RMIChatClient extends Remote
{
  /**
   * Notifies the client's networking about a new message
   * @param message the new received message
   * @throws RemoteException if the remote method invocation fails
   */
  void newMessageReceived(Message message) throws RemoteException;

  /**
   * Notifies the client's networking about a new online user
   * @param user the new online user
   * @throws RemoteException if the remote method invocation fails
   */
  void newOnlineUser(User user) throws RemoteException;

  /**
   * Notifies the client's networking about a new offline user
   * @param user the new offline user
   * @throws RemoteException if the remote method invocation fails
   */
  void newOfflineUser(User user) throws RemoteException;

  /**
   * Notifies the client's networking about a new read message
   * @param message the message marked as read
   * @throws RemoteException if the remote method invocation fails
   */
  void messageRead(Message message) throws RemoteException;

  /**
   * Requests from the client the currently logged in user
   * @return the logged user
   * @throws RemoteException if the remote method invocation fails
   */
  User getLoggedUser() throws RemoteException;
}


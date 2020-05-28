package ESharing.Shared.Networking.user;

import ESharing.Shared.TransferedObject.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * The interface used by the server handler for remote method calling on the server
 * @version 1.0
 * @author Group1
 */
public interface RMIUserServer extends Remote
{
  /**
   * Sends the request for creating a new user account to the server model
   * and waits for validation
   * @param user the new user account to be created
   * @return true, if the request was approved and the account has been created,
   * false otherwise
   * @throws RemoteException if the method invocation fails
   */
  boolean addUser (User user) throws RemoteException;

  /**
   * Sends the request for removing user account to the server model
   * and waits for validation
   * @param user the user account to be removed
   * @return true, if the request was approved and the account has been removed,
   * false otherwise
   * @throws RemoteException if the method invocation fails
   */
  boolean removeUser (User user) throws RemoteException;

  /**
   * Sends the request for editing a user account's data to the server model
   * and waits for validation
   * @param user the new user account's data
   * @return true, if the request was approved and the account has been updated,
   * false otherwise
   * @throws RemoteException if the method invocation fails
   */
  boolean editUser (User user) throws RemoteException;

  /**
   * Sends a request for logging in an already existing user account and
   * registers the client for rmi inside listeners
   * @param username the username of the account
   * @param password the password of the account
   * @return the user's account data, if the request was approved, null otherwise
   * @throws RemoteException if the method invocation fails
   */
  User loginUser (String username, String password) throws RemoteException;

  /**
   * Initializes all callbacks which are related to the administrator account
   * @param client the client to be registered
   * @throws RemoteException if the method invocation fails
   */
  void registerAdministratorCallback(RMIUserClient client) throws RemoteException;

  void registerGeneralCallback(RMIUserClient client) throws RemoteException;

  /**
   * Pass the collection of all users to the client side
   * @return the collection of all users
   * @throws RemoteException if the method invocation fails
   */
  List<User> getAllUsers() throws RemoteException;

  void unRegisterUserAsAListener() throws RemoteException;

  void changeUserAvatar(byte[] avatarImage, int userID) throws RemoteException;

  boolean addNewUserReport(int user_id) throws RemoteException;
}


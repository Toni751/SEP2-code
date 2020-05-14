package ESharing.Server.Model.user;

import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.PropertyChangeSubject;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface to enable the communication between the server handler and the server model
 * @version 1.0
 * @author Group 1
 */
public interface ServerModel extends PropertyChangeSubject
{
  /**
   * Sends a request to the data base for adding a new user and waits for a response
   * @param user the new user account to be added
   * @return true if the user was added successfully, false otherwise
   */
  boolean addNewUser (User user);

  /**
   * Sends a request to the data base for removing a user and waits for a response
   * @param user the user account to be deleted
   * @return true if the user was deleted successfully, false otherwise
   */
  boolean removeUser (User user);

  /**
   * Sends a request to the data base for editing a user's data and waits for a response
   * @param user the new data for the user account to be modified
   * @return true if the user account was updated successfully, false otherwise
   */
  boolean editUser (User user);

  /**
   * Sends a request to the database to access a user's account through the username and database
   * @param username the username for the requested account
   * @param password the password for the requested account
   * @return a user account, if the username and password match with it, null otherwise
   */
  User loginUser (String username, String password);

  /**
   * Returns the collection of all users existing in the system database
   * @return the collection of all users existing in the system database
   */
  List<User> getAllUsers();
  void userLoggedOut(User user);
  ArrayList<User> getAllOnlineUsers();
}

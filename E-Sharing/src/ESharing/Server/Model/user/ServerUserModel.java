package ESharing.Server.Model.user;

import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.PropertyChangeSubject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface to enable the communication between the server handler and the server model
 * @version 1.0
 * @author Group 1
 */
public interface ServerUserModel extends PropertyChangeSubject
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

  /**
   * Removes a user from the online users list and fires an event
   * @param user the user to be removed from the list
   */
  void userLoggedOut(User user);

  /**
   * Returns the list with all online users
   * @return the list with all online users
   */
  List<User> getAllOnlineUsers();

  /**
   * Changes a user's avatar with the given image and sends the request to the database
   * @param avatarImage the new avatar for the user
   * @param userId the id of the user
   */
   void changeUserAvatar(byte[] avatarImage, int userId);

  /**
   * Prints to the console the number of listeners the property change support instance has
   */
  void listeners();

  /**
   * Sends a new request to the DAO for incrementing a user's number of reports, and fires an event afterwards
   * @param user_id the id of the reported user
   * @return true if the reports were updated successfully, false otherwise
   */
   boolean addNewUserReport(int user_id);
}

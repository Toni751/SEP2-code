package ESharing.Client.Networking.user;

import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.PropertyChangeSubject;

import java.util.List;

/**
 * The interface the model will user for calling methods on the client
 * and for sending/ receiving data to the server
 * @version 1.0
 * @author Group1
 */
public interface UserClient extends PropertyChangeSubject
{

  void initializeConnection();
  /**
   * Sends a request for creating a new user and waits for the response
   * @param user the new user account to be created
   * @return true, if the request was approved and the account created, false otherwise
   */
  boolean addUserRequest (User user);

  /**
   * Sends a request for removing a user and waits for the response
   * @param user the user account to be deleted
   * @return true, if the request was approved and the account deleted, false otherwise
   */
  boolean removeUserRequest (User user);

  /**
   * Sends a request for editing a user's data with the given value and waits for the response
   * @param user the new user account's data
   * @return true, if the request was approved and the account updated, false otherwise
   */
  boolean editUserRequest (User user);

  /**
   * Sends a request for logging in an already existing user account
   * @param username the username of the account
   * @param password the password of the account
   * @return the user's account data, if the request was approved, null otherwise
   */
  User loginUserRequest (String username, String password);

  /**
   * Sends a request to get a list with all system users and gets the result as a collection
   * @return the collection of all system users
   */
  List<User> getAllUsersRequest();

  void logout();

   void changeAvatar(byte[] avatarImage);

    boolean addNewUserReport(int user_id);
}

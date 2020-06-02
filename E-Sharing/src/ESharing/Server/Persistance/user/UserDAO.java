package ESharing.Server.Persistance.user;

import ESharing.Shared.TransferedObject.User;

import java.nio.file.Path;

/**
 * The DAO interface for managing requests regarding user accounts
 * @version 1.0
 * @author Group1
 */
public interface UserDAO
{
  /**
   * Adds a new user to the database user_account table
   * @param user the new user to be added
   * @return true if the user was added successfully, false otherwise
   */
  boolean create(User user);

  /**
   * Retrieves from the database a user by the id
   * @param user_id the id of the user to be retrieved
   * @return the user with the given id, null if there isn't one
   */
  User readById(int user_id);

  /**
   * Retrieves a user with the given username and password
   * @param searchString the username
   * @param password the password
   * @return the user with the given username and password, or null if there isn't one
   */
  User readByUserNameAndPassword(String searchString, String password);

  /**
   * Updates a user with the new values in the database
   * @param user the new value for the user's attributes
   * @return true if the user was updated successfully, false otherwise
   */
  boolean update(User user);

  /**
   * Deletes from the database a given user
   * @param user the user to be deleted
   * @return true if the user was deleted successfully, false otherwise
   */
  boolean delete(User user);

  /**
   * Changes a user's avatar path to the given value
   * @param path the new value to be set for the path
   * @param userId the id of the user
   * @return true if the path was updated successfully, false otherwise
   */
  boolean changeAvatar(String path, int userId);

  /**
   * Increments in the database a user's number of reports
   * @param user_id the id of the reported user
   * @return the user's total number of reports, after it was incremented
   */
  int addNewUserReport(int user_id);
}

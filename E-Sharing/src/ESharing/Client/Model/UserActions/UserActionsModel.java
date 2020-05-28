package ESharing.Client.Model.UserActions;

import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.PropertyChangeSubject;
import java.io.File;

/**
 * The interface used to connect a view model layer with a model layer for all user features.
 * @version 1.0
 * @author Group1
 */
public interface UserActionsModel extends PropertyChangeSubject
{
    /**
     * Sends a new created user to a model
     * @param newUser the new user object
     * @return the string object with a result of the action
     */
    String createNewUser(User newUser);

    /**
     * Sends a request for login to a model
     * @param username the requested username
     * @param password the requested password
     * @return the string object with a result of the action
     */
    String onLoginRequest(String username, String password);

    /**
     * Sends a request for modify general information about logged user
     * @param updatedUser the user object with updated information
     * @return the string object with a result of the action
     */
    boolean modifyUserInformation(User updatedUser);

    /**
     * Removes account which belongs to current logged user and goes to the welcome view
     */
    void removeAccount();

    /**
     * Logouts user from the system
     */
    void logoutUser();

    /**
     * Sends a request to the networking layer for changing the account avatar
     * @param avatarImage the new avatar as a file object
     */
    void changeAvatar(File avatarImage);

    /**
     * Sends a request to the networking layer for reporting the given user
     * @param user_id the id of the given user
     * @return the result of the reporting part
     */
    boolean addNewUserReport(int user_id);
}

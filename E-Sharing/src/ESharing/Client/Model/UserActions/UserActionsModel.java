package ESharing.Client.Model.UserActions;

import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.PropertyChangeSubject;

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
}

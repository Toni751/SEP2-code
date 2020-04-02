package ESharing.Client.Model.UserAccount;

import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.PropertyChangeSubject;

/**
 * The interface used to connect a view model layer with a model layer for all user features.
 * @version 1.0
 * @author Group1
 */
public interface UserAccountModel extends PropertyChangeSubject
{
    /**
     * Sends a new created user to a model
     * @param newUser the new user object
     */
    void createNewUser(User newUser);

    /**
     * Sends a request for login to a model
     * @param username the requested username
     * @param password the requested password
     */
    void onLoginRequest(String username, String password);
}

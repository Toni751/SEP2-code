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
     */
    String createNewUser(User newUser);

    /**
     * Sends a request for login to a model
     * @param username the requested username
     * @param password the requested password
     */
    String onLoginRequest(String username, String password);

    String modifyUserInformation(User updatedUser);

    String verifyUserInfo(String username, String password, String passwordAgain, String phoneNumber);

    String verifyAddress(Address address);

    void removeAccount();
}

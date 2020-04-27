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
    String modifyUserInformation(User updatedUser);

    /**
     * Verifies user information and returns a result as a string object
     * @param username the username value from the text field
     * @param password the password value from the text field
     * @param passwordAgain the password confirmation value from the text field
     * @param phoneNumber the phone number value from the text field
     * @return the result of the verification as a string object
     */
    String verifyUserInfo(String username, String password, String passwordAgain, String phoneNumber);

    /**
     * Verifies address information and returns a result as a string object
     * @param address the address object for verification
     * @return the result of the verification as a string object
     */
    String verifyAddress(Address address);

    /**
     * Verifies change password action
     * @param oldPassword the old user password
     * @param newPassword the new password
     * @return the result of the verification as a string object
     */
    String verifyChangePassword(String oldPassword, String newPassword);

    /**
     * Removes account which belongs to current logged user and goes to the welcome view
     */
    void removeAccount();
}

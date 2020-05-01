package ESharing.Client.Model.AdministratorModel;

import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.PropertyChangeSubject;

import java.util.List;

/**
 * The interface used to connect a view model layer with a model layer for all administrator features.
 * @version 1.0
 * @author Group1
 */
public interface AdministratorActionsModel extends PropertyChangeSubject {

    /**
     * Returns list of all users registered in the system
     * @return the list with all users registered in the system
     */
    List<User> getAllUsers();

    /**
     * Removes a selected by the administrator user account and returns boolean value as a result
     * @return the result of removing user account
     */
    boolean removeUserAccount(User user);

    /**
     * Sets new random password for the given user account and sends it to networking layer
     * @param user the given user object with a password to reset
     * @return the new random password
     */
    String resetUserPassword(User user);

}

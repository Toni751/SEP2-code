package ESharing.Client.Model.UserActions;

import ESharing.Client.Core.ClientFactory;
import ESharing.Client.Networking.user.Client;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The class from the model layer which contains all user features and connects them with a networking part
 * @version 1.0
 * @author Group1
 */
public class UserActionsModelManager implements UserActionsModel {

    private Client client;
    private PropertyChangeSupport support;

    /**
     * A constructor initializes all fields
     */
    public UserActionsModelManager()
    {
        this.client = ClientFactory.getClientFactory().getClient();
        support = new PropertyChangeSupport(this);
    }

    @Override
    public String createNewUser(User newUser) {
        boolean verification = client.addUserRequest(newUser);
        if(!verification)
            return VerificationList.getVerificationList().getVerifications().get(Verifications.DATABASE_CONNECTION_ERROR);
        else {
            return null;
        }
    }

    @Override
    public String onLoginRequest(String username, String password) {
            User requestedUser = client.loginUserRequest(username,password);
            if(requestedUser == null)
                return VerificationList.getVerificationList().getVerifications().get(Verifications.USER_NOT_EXIST);
            else {
                LoggedUser.getLoggedUser().loginUser(requestedUser);
                return null;
            }
    }

    @Override
    public boolean modifyUserInformation(User updatedUser) {
        boolean verification = client.editUserRequest(updatedUser);
        if(verification) {
            LoggedUser.getLoggedUser().loginUser(updatedUser);
            return true;
        }
        else
            return false;
    }

    @Override
    public void removeAccount() {
        User accountToRemove = new User(LoggedUser.getLoggedUser());
        client.removeUserRequest(accountToRemove);
        LoggedUser.getLoggedUser().logoutUser();
    }

    @Override
    public void logoutUser() {
      client.logout();
    }

    @Override
    public void addPropertyChangeListener(String eventName, PropertyChangeListener listener)
    {
        if ("".equals(eventName) || eventName == null)
            addPropertyChangeListener(listener);
        else
            support.addPropertyChangeListener(eventName, listener);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(String eventName, PropertyChangeListener listener)
    {
        if ("".equals(eventName) || eventName == null)
            removePropertyChangeListener(listener);
        else
            support.removePropertyChangeListener(eventName, listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}

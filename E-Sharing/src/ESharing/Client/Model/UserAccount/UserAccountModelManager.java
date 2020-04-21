package ESharing.Client.Model.UserAccount;

import ESharing.Client.Networking.Client;
import ESharing.Shared.TransferedObject.User;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The class from the model layer which contains all user features and connects them with a networking part
 * @version 1.0
 * @author Group1
 */
public class UserAccountModelManager implements UserAccountModel{

    private Client client;
    private PropertyChangeSupport support;

    /**
     * One-argument constructor initializes all fields
     * @param client the client used for the networking connection
     */
    public UserAccountModelManager(Client client)
    {
        this.client = client;
        support = new PropertyChangeSupport(this);
    }


    @Override
    public boolean createNewUser(User newUser) {
        return client.addUserRequest(newUser);
    }

    @Override
    public boolean onLoginRequest(String username, String password) {
        User loggedUser = client.loginUserRequest(username, password);
        if(loggedUser == null)
            return false;
        else{
            LoggedUser.getLoggedUser().loginUser(loggedUser);
            return true;
        }
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
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        support.removePropertyChangeListener(listener);
    }
}

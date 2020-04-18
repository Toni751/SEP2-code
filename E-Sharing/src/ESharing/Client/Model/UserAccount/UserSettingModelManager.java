package ESharing.Client.Model.UserAccount;

import ESharing.Client.Networking.Client;
import ESharing.Shared.TransferedObject.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class UserSettingModelManager implements UserSettingModel{

    private Client client;
    private PropertyChangeSupport support;

    public UserSettingModelManager(Client client)
    {
        this.client = client;
        support = new PropertyChangeSupport(this);

    }

    @Override
    public void modifyAddress(User updatedUser) {
        //code for modify address
    }

    @Override
    public boolean modifyGeneralInformation(User updatedUser) {
        //code for modify general info
        System.out.println("Update user in model");
        return client.editUserRequest(updatedUser);
    }

    @Override
    public void removeAccount(User loggedUser) {
        client.removeUserRequest(loggedUser);
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

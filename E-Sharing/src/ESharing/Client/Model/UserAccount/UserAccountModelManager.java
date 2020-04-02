package ESharing.Client.Model.UserAccount;

import ESharing.Client.Networking.Client;
import ESharing.Shared.TransferedObject.Events;
import ESharing.Shared.Util.FailedConnection.FailedConnectionViewController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class UserAccountModelManager implements UserAccountModel{

    private Client client;
    private PropertyChangeSupport support;

    public UserAccountModelManager(Client client)
    {
        this.client = client;
        support = new PropertyChangeSupport(this);
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

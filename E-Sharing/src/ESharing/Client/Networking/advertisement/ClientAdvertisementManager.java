package ESharing.Client.Networking.advertisement;

import ESharing.Shared.Networking.advertisement.RMIAdvertisementClient;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ClientAdvertisementManager implements ClientAdvertisement, RMIAdvertisementClient {

    private PropertyChangeSupport support;

    public ClientAdvertisementManager() {
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

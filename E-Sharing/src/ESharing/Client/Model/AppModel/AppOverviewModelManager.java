package ESharing.Client.Model.AppModel;

import ESharing.Client.Networking.Client;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class AppOverviewModelManager implements AppOverviewModel{

    private Client client;
    private PropertyChangeSupport support;

    public AppOverviewModelManager(Client client)
    {
        this.client = client;
        support = new PropertyChangeSupport(this);
    }

    @Override
    public void loadAdvertisements() {
        //Load advertisements from the database
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

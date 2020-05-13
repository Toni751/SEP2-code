package ESharing.Client.Model.AppModel;

import ESharing.Client.Core.ClientFactory;
import ESharing.Client.Networking.user.Client;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The class from the model layer which contains all main application features and connects them with a networking part
 * @version 1.0
 * @author Group1
 */
public class AppOverviewModelManager implements AppOverviewModel{

    private Client client;
    private PropertyChangeSupport support;

    /**
     * A constructor initializes fields
     */
    public AppOverviewModelManager()
    {
        this.client = ClientFactory.getClientFactory().getClient();
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

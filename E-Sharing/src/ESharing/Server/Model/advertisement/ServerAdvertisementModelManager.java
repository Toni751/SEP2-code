package ESharing.Server.Model.advertisement;

import ESharing.Server.Persistance.*;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.Util.Events;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ServerAdvertisementModelManager implements ServerAdvertisementModel{

    private AdvertisementDAO advertisementDAO;
    private PropertyChangeSupport support;

    public ServerAdvertisementModelManager()
    {
        this.advertisementDAO = AdvertisementDAOManager.getInstance();
        support = new PropertyChangeSupport(this);
    }


    @Override
    public boolean addAdvertisement(Advertisement advertisement) {
        boolean result = advertisementDAO.create(advertisement);
        if(result)
            support.firePropertyChange(Events.NEW_AD_REQUEST.toString(), null, advertisement);
        return result;
    }

    @Override
    public void approveAdvertisement(Advertisement ad)
    {
        //send ad to be made approved in the database
        support.firePropertyChange(Events.NEW_APPROVED_AD.toString(), null, ad);
    }

    @Override
    public boolean removeAdvertisement(Advertisement advertisement) {
        boolean result = advertisementDAO.removeAdvertisement(advertisement);
        if(result)
            support.firePropertyChange(Events.AD_REMOVED.toString(), null, advertisement);
        return result;
    }

    @Override
    public boolean editAdvertisement(Advertisement ad)
    {
        return false;
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

package ESharing.Client.Networking.advertisement;

import ESharing.Client.Networking.Connection;
import ESharing.Shared.Networking.advertisement.RMIAdvertisementClient;
import ESharing.Shared.Networking.advertisement.RMIAdvertisementServer;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.Util.Events;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientAdvertisementManager implements ClientAdvertisement, RMIAdvertisementClient {

    private PropertyChangeSupport support;
    private RMIAdvertisementServer server;

    public ClientAdvertisementManager() {
        support = new PropertyChangeSupport(this);
        try
        {
            UnicastRemoteObject.exportObject(this, 0);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
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

    @Override
    public void initializeConnection()
    {
        try
        {
            server = Connection.getStubInterface().getServerAdHandler();
        }
        catch (RemoteException | NullPointerException ignored) {}
    }

    @Override
    public boolean addAdvertisement(Advertisement ad)
    {
        try
        {
            return server.addAdvertisement(ad);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void approveAdvertisement(Advertisement ad)
    {
        try
        {
            server.approveAdvertisement(ad);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean removeAdvertisement(Advertisement ad)
    {
        try
        {
            return server.removeAdvertisement(ad);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean editAdvertisement(Advertisement ad)
    {
        try
        {
            return server.editAdvertisement(ad);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void newAdRequest(Advertisement ad)
    {
        support.firePropertyChange(Events.NEW_AD_REQUEST.toString(), null, ad);
    }

    @Override
    public void newApprovedAd(Advertisement ad)
    {
        support.firePropertyChange(Events.NEW_APPROVED_AD.toString(), null, ad);
    }

    @Override
    public void updatedAd(Advertisement ad)
    {
        support.firePropertyChange(Events.AD_UPDATED.toString(), null, ad);
    }

    @Override
    public void removedAd(Advertisement ad)
    {
        support.firePropertyChange(Events.AD_REMOVED.toString(), null, ad);
    }
}
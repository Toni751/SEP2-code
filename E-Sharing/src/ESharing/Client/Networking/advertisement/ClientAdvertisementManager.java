package ESharing.Client.Networking.advertisement;

import ESharing.Client.Networking.Connection;
import ESharing.Shared.Networking.advertisement.RMIAdvertisementClient;
import ESharing.Shared.Networking.advertisement.RMIAdvertisementServer;
import ESharing.Shared.TransferedObject.AdCatalogueAdmin;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.Util.Events;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * The class which handles the requests from the model and communicates
 * with the server through remote method invocation
 * @version 1.0
 * @author Group 1
 */
public class ClientAdvertisementManager implements ClientAdvertisement, RMIAdvertisementClient {

    private PropertyChangeSupport support;
    private RMIAdvertisementServer server;

    /**
     * A constructor which initializes fields and tries to establish connection with the server
     */
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
    public boolean approveAdvertisement(int id)
    {
        try
        {
            return server.approveAdvertisement(id);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeAdvertisement(int id)
    {
        try
        {
            return server.removeAdvertisement(id);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Advertisement> selectAllAdvertisement() {
        try
        {
            return server.selectAllAdvertisements();
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean reportAdvertisement(int advertisementID) {
        try {
            return server.addNewAdvertisementReport(advertisementID);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<CatalogueAd> getAllCatalogues() {
        try {
            return server.getAdvertisementsCatalogue();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<AdCatalogueAdmin> getAdminAdCatalogue()
    {
        try
        {
            return server.getAdminAdCatalogue();
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Advertisement getAdvertisement(int advertisementID) {
        try {
            return server.getAdvertisementById(advertisementID);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getAdvertisementNumber() {
        try {
            return server.getAdvertisementNumber();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void registerForCallBack() {
        try {
            server.registerClientCallback(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CatalogueAd> getAllUserCatalogues(int user_id) {
        try {
            return server.getAdvertisementsByUser(user_id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addRating(int ad_id, int user_id, double rating)
    {
        try
        {
            return server.addRating(ad_id, user_id, rating);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public double retrieveAdRating(int ad_id)
    {
        try
        {
            return server.retrieveAdRating(ad_id);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void newAdRequest(AdCatalogueAdmin ad)
    {
        support.firePropertyChange(Events.NEW_AD_REQUEST.toString(), null, ad);
    }

    @Override
    public void newApprovedAd(CatalogueAd ad)
    {
        support.firePropertyChange(Events.NEW_APPROVED_AD.toString(), null, ad);
    }

    @Override
    public void removedAd(int id)
    {
        support.firePropertyChange(Events.AD_REMOVED.toString(), null, id);
    }

    @Override
    public void newReportReceived(int id, int newValue){
        support.firePropertyChange(Events.NEW_ADVERTISEMENT_REPORT.toString(), id, newValue);
    }
}

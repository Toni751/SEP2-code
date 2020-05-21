package ESharing.Client.Views.UserAdvertisementView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdvertisementModel.AdvertisementModel;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.Util.Events;
import ESharing.Shared.Util.PropertyChangeSubject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class UserAdvertisementViewModel implements PropertyChangeSubject {

    private AdvertisementModel advertisementModel;
    private ObservableList<CatalogueAd> ownCatalogues;
    private PropertyChangeSupport support;


    public UserAdvertisementViewModel(){
        advertisementModel = ModelFactory.getModelFactory().getAdvertisementModel();

        ownCatalogues = FXCollections.observableArrayList();
        support = new PropertyChangeSupport(this);

        advertisementModel.addPropertyChangeListener(Events.AD_REMOVED.toString(), this::adRemoved);
    }

    private void adRemoved(PropertyChangeEvent propertyChangeEvent) {
        int removedId = (int) propertyChangeEvent.getNewValue();

        for(int i = 0 ; i < ownCatalogues.size() ; i++)
        {
            if(ownCatalogues.get(i).getAdvertisementID() == removedId) {
                ownCatalogues.remove(ownCatalogues.get(i));
                support.firePropertyChange(Events.AD_REMOVED.toString(), null, ownCatalogues);
            }
        }
    }

    public void defaultView()
    {
        ownCatalogues.clear();
        ownCatalogues.addAll(advertisementModel.getAllCataloguesForUser(LoggedUser.getLoggedUser().getUser().getUser_id()));
        System.out.println(ownCatalogues);
    }

    public ObservableList<CatalogueAd> getOwnCatalogues() {
        return ownCatalogues;
    }

    public boolean selectAdvertisement(int id)
    {
        Advertisement advertisement = advertisementModel.getAdvertisement(id);
        if(advertisement  != null){
            LoggedUser.getLoggedUser().selectAdvertisement(advertisement);
            return true;
        }
        return false;
    }

    @Override
    public void addPropertyChangeListener(String eventName, PropertyChangeListener listener) {
        if ("".equals(eventName) || eventName == null)
            addPropertyChangeListener(listener);
        else
            support.addPropertyChangeListener(eventName, listener);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(String eventName, PropertyChangeListener listener) {
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

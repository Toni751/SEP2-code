package ESharing.Client.Views.AdvertisementsOverview;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdvertisementModel.AdvertisementModel;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.Util.Events;
import ESharing.Shared.Util.PropertyChangeSubject;
import ESharing.Shared.Util.Vehicles;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdsOverviewViewModel implements PropertyChangeSubject {

    private ObservableList<String> searchItemsProperty;
    private PropertyChangeSupport support;
    private List<CatalogueAd> catalogueAds;
    private AdvertisementModel advertisementModel;
    private StringProperty searchValueProperty;


    public AdsOverviewViewModel(){
        advertisementModel = ModelFactory.getModelFactory().getAdvertisementModel();
        searchValueProperty = new SimpleStringProperty();
        catalogueAds = new ArrayList<>();
        searchItemsProperty = FXCollections.observableArrayList();
        support = new PropertyChangeSupport(this);

        advertisementModel.addPropertyChangeListener(Events.NEW_APPROVED_AD.toString(), this::newApprovedAd);
        advertisementModel.addPropertyChangeListener(Events.AD_REMOVED.toString(), this::adRemoved);
    }

    public void setDefaultView()
    {
        if(searchItemsProperty.isEmpty()) {
            for (int i = 0; i < Vehicles.values().length; i++) {
                searchItemsProperty.add(Vehicles.values()[i].toString());
            }
        }
        searchValueProperty.setValue(Vehicles.All.toString());
        catalogueAds.addAll(advertisementModel.getAllCatalogues());
    }

        public void selectAdvertisement(CatalogueAd catalogueAd) {
        Advertisement selectedAdvertisement = advertisementModel.getAdvertisement(catalogueAd.getAdvertisementID());
        LoggedUser.getLoggedUser().selectAdvertisement(selectedAdvertisement);
        LoggedUser.getLoggedUser().setSelectedUser(selectedAdvertisement.getOwner());
    }

    public List<CatalogueAd> getCatalogueAds()
    {
        catalogueAds.clear();
        catalogueAds.addAll(advertisementModel.getAllCatalogues());
        return catalogueAds;
    }

    public ObservableList<String> getSearchItemsProperty() {
        return searchItemsProperty;
    }

    public StringProperty getSearchValueProperty() {
        return searchValueProperty;
    }

    public void searchAdvertisements() {
        System.out.println("search");
        List<CatalogueAd> filteredAds = new ArrayList<>();

        for(int i = 0 ; i <catalogueAds.size() ; i++)
        {
            System.out.println(searchValueProperty.get());
            if((searchValueProperty).getValue().equals(Vehicles.All.toString())){
                filteredAds = catalogueAds;
            }
            else if(catalogueAds.get(i).getVehicleType().equals(searchValueProperty.getValue())) {
                filteredAds.add(catalogueAds.get(i));
                System.out.println(catalogueAds.get(i).getTitle());
            }
        }

        support.firePropertyChange(Events.UPDATE_AD_LIST.toString(), null, filteredAds);
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

    private void adRemoved(PropertyChangeEvent propertyChangeEvent) {
        int advertisementID = (int) propertyChangeEvent.getNewValue();
        for(int i = 0 ; i < catalogueAds.size() ; i++){
            if(catalogueAds.get(i).getAdvertisementID() == advertisementID) {
                catalogueAds.remove(catalogueAds.get(i));
                support.firePropertyChange(Events.AD_REMOVED.toString(), null, advertisementID);
            }
        }

    }

    private void newApprovedAd(PropertyChangeEvent propertyChangeEvent) {
        CatalogueAd newCatalogue = (CatalogueAd) propertyChangeEvent.getNewValue();
        catalogueAds.add(newCatalogue);

        support.firePropertyChange(Events.NEW_APPROVED_AD.toString(), null, newCatalogue);
    }
}

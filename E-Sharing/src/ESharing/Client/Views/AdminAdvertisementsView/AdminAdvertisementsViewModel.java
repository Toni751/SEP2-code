package ESharing.Client.Views.AdminAdvertisementsView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdvertisementModel.AdvertisementModel;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Shared.TransferedObject.AdCatalogueAdmin;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.Util.Events;
import ESharing.Shared.Util.Vehicles;
import ESharing.Shared.Util.VerificationList;
import ESharing.Shared.Util.Verifications;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jdk.jfr.Event;

import java.beans.PropertyChangeEvent;

public class AdminAdvertisementsViewModel {

    private StringProperty warningStyleProperty;
    private StringProperty totalProperty;
    private StringProperty notApprovedProperty;
    private StringProperty warningProperty;
    private StringProperty comboValueProperty;
    private BooleanProperty warningVisibleProperty;
    private BooleanProperty openAdDisableProperty;
    private BooleanProperty removeAdDisableProperty;
    private BooleanProperty approveAdDisableProperty;

    private ObservableList<String> comboItems;
    private ObservableList<AdCatalogueAdmin> advertisementList;

    private AdvertisementModel advertisementModel;


    public AdminAdvertisementsViewModel()
    {
        advertisementModel = ModelFactory.getModelFactory().getAdvertisementModel();

        warningProperty = new SimpleStringProperty();
        notApprovedProperty = new SimpleStringProperty();
        totalProperty = new SimpleStringProperty();
        comboValueProperty = new SimpleStringProperty();
        warningStyleProperty = new SimpleStringProperty();
        warningVisibleProperty = new SimpleBooleanProperty();
        openAdDisableProperty = new SimpleBooleanProperty();
        removeAdDisableProperty = new SimpleBooleanProperty();
        approveAdDisableProperty = new SimpleBooleanProperty();

        comboItems = FXCollections.observableArrayList();
        advertisementList = FXCollections.observableArrayList();

        advertisementModel.addPropertyChangeListener(Events.AD_REMOVED.toString(), this::adRemoved);
        advertisementModel.addPropertyChangeListener(Events.NEW_AD_REQUEST.toString(), this::newAdRequest);
        advertisementModel.addPropertyChangeListener(Events.NEW_APPROVED_AD.toString(), this::newApprovedAd);
        advertisementModel.addPropertyChangeListener(Events.NEW_ADVERTISEMENT_REPORT.toString(), this::newReportReceived);
    }

    public void loadDefaultView() {
        LoggedUser.getLoggedUser().selectAdvertisement(null);
        advertisementList.clear();
        if(advertisementModel.selectAllAdvertisements() != null)
            advertisementList.setAll(advertisementModel.getAllAdminCatalogues());

        removeAdDisableProperty.setValue(true);
        openAdDisableProperty.setValue(true);
        approveAdDisableProperty.set(true);
        warningVisibleProperty.setValue(false);

        totalProperty.set(String.valueOf(advertisementList.size()));

        int notApproved = 0;
        for(AdCatalogueAdmin advertisement : advertisementList){
            if(!advertisement.isApproved())
                notApproved++;
        }
        notApprovedProperty.set(String.valueOf(notApproved));

        comboItems.clear();
        comboValueProperty.set("All");
        comboItems.add("Not Approved");
        comboItems.add("Approved");
        for(int i = 0; i < Vehicles.values().length ; i++) {
            comboItems.add(Vehicles.values()[i].toString());
        }
    }

    public void enableUserManagingProperty() {
        if(LoggedUser.getLoggedUser().getSelectedAdvertisement().ifAdApproved())
            approveAdDisableProperty.set(true);
        else
            approveAdDisableProperty.set(false);
        openAdDisableProperty.setValue(false);

        removeAdDisableProperty.setValue(false);
    }

    public void disableUserManagingProperty() {
        removeAdDisableProperty.setValue(true);
        openAdDisableProperty.setValue(true);
        approveAdDisableProperty.set(true);
    }

    public void searchAdvertisements() {
        advertisementList.clear();
        LoggedUser.getLoggedUser().selectAdvertisement(null);
        disableUserManagingProperty();

        if(comboValueProperty.get().equals("Not Approved")){
            for(AdCatalogueAdmin advertisement : advertisementList) {
                if(!advertisement.isApproved())
                    advertisementList.add(advertisement);
            }
        }
        else if(comboValueProperty.get().equals("Approved"))
            for(AdCatalogueAdmin advertisement : advertisementList) {
                if(advertisement.isApproved())
                    advertisementList.add(advertisement);
                System.out.println(advertisement.isApproved());
            }
        else{
            for(AdCatalogueAdmin advertisement : advertisementList) {
                    if (comboValueProperty.get().equals(advertisement.getType()))
                        advertisementList.add(advertisement);
            }
        }

    }

    public void approveAdvertisement() {
        if(advertisementModel.approveAdvertisement(LoggedUser.getLoggedUser().getSelectedAdvertisement().getAdvertisementID())){
            warningProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.ACTION_SUCCESS));
            warningStyleProperty.setValue("-fx-background-color: #4CDBC4; -fx-text-fill: black");
        }
        else {
            warningProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.DATABASE_CONNECTION_ERROR));
            warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
        }
        warningVisibleProperty.set(true);


    }

    public void removeSelectedAdvertisement() {
        if(advertisementModel.removeAdvertisement(LoggedUser.getLoggedUser().getSelectedAdvertisement().getAdvertisementID())){
            warningProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.ACTION_SUCCESS));
            warningStyleProperty.setValue("-fx-background-color: #4CDBC4; -fx-text-fill: black");
        }
        else {
            warningProperty.set(VerificationList.getVerificationList().getVerifications().get(Verifications.DATABASE_CONNECTION_ERROR));
            warningStyleProperty.setValue("-fx-background-color: #DB5461; -fx-text-fill: white");
        }
        warningVisibleProperty.set(true);
    }

    public void selectAdvertisement(AdCatalogueAdmin adCatalogueAdmin) {
        Advertisement advertisement = advertisementModel.getAdvertisement(adCatalogueAdmin.getAdvertisementID());
        if(advertisement != null){
            LoggedUser.getLoggedUser().selectAdvertisement(advertisement);
            enableUserManagingProperty();
        }
    }

    public BooleanProperty getOpenAdDisableProperty() {
        return openAdDisableProperty;
    }

    public BooleanProperty getRemoveDisableProperty() {
        return removeAdDisableProperty;
    }

    public BooleanProperty getApproveDisableProperty() {
        return approveAdDisableProperty;
    }

    public ObservableList<String> getComboItems() {
        return comboItems;
    }

    public StringProperty getNotApprovedProperty() {
        return notApprovedProperty;
    }

    public StringProperty getTotalProperty() {
        return totalProperty;
    }

    public StringProperty getWarningProperty() {
        return warningProperty;
    }

    public BooleanProperty getWarningVisibleProperty() {
        return warningVisibleProperty;
    }

    public StringProperty getWarningStyleProperty() {
        return warningStyleProperty;
    }

    public ObservableList<AdCatalogueAdmin> getAllAdvertisement() {
        return advertisementList;
    }

    public StringProperty getComboValueProperty() {
        return comboValueProperty;
    }

    private void newReportReceived(PropertyChangeEvent propertyChangeEvent) {

        int advertisementID = (int) propertyChangeEvent.getOldValue();
        int reports = (int) propertyChangeEvent.getNewValue();

        for(int i = 0 ; i < advertisementList.size() ; i++)
        {
            if(advertisementList.get(i).getAdvertisementID() == advertisementID) {
                AdCatalogueAdmin updated = advertisementList.get(i);
                updated.setReports(reports);
                advertisementList.set(i, updated);
            }
        }
    }

    private void newApprovedAd(PropertyChangeEvent propertyChangeEvent) {
        CatalogueAd newAd = (CatalogueAd) propertyChangeEvent.getNewValue();
        for(AdCatalogueAdmin catalogueAdmin : advertisementList)
        {
            if(catalogueAdmin.getAdvertisementID() == newAd.getAdvertisementID())
                catalogueAdmin.makeApproved();
        }
        disableUserManagingProperty();
    }

    private void newAdRequest(PropertyChangeEvent propertyChangeEvent) {
        AdCatalogueAdmin newAdvertisement = (AdCatalogueAdmin) propertyChangeEvent.getNewValue();
        advertisementList.add(newAdvertisement);
        disableUserManagingProperty();
    }

    private void adRemoved(PropertyChangeEvent propertyChangeEvent) {
        int removedID = (int) propertyChangeEvent.getNewValue();
        for(int i = 0 ; i < advertisementList.size() ; i++)
        {
            if(advertisementList.get(i).getAdvertisementID() == removedID)
                advertisementList.remove(advertisementList.get(i));
        }
        disableUserManagingProperty();
    }
}

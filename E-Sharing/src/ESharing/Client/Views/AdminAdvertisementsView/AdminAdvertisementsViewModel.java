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
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * The class in a view model layer contains all functions which are used in the administrator advertisement view.
 * @version 1.0
 * @author Group1
 */
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


    /**
     * A constructor initializes the model layer for a administrator advertisement features and all fields
     */
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

    /**
     * Sets default version of the view
     */
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
        if(comboItems.isEmpty()) {
            for (int i = 0; i < Vehicles.values().length; i++) {
                comboItems.add(Vehicles.values()[i].toString());
            }
            comboItems.add("Not Approved");
            comboItems.add("Approved");
        }
        comboValueProperty.setValue(Vehicles.All.toString());
    }

    /**
     * Enables managing properties
     */
    public void enableUserManagingProperty() {
        if(LoggedUser.getLoggedUser().getSelectedAdvertisement().ifAdApproved()) {
            approveAdDisableProperty.set(true);

        }
        else
            approveAdDisableProperty.set(false);
        openAdDisableProperty.setValue(false);

        removeAdDisableProperty.setValue(false);
    }

    /**
     * Disables managing properties
     */
    public void disableUserManagingProperty() {
        removeAdDisableProperty.setValue(true);
        openAdDisableProperty.setValue(true);
        approveAdDisableProperty.set(true);
    }

    /**
     * Searches for advertisements regarding the search value property
     */
    public void searchAdvertisements() {
        System.out.println("Admin search");
        advertisementList.setAll(advertisementModel.getAllAdminCatalogues());
        LoggedUser.getLoggedUser().selectAdvertisement(null);
        disableUserManagingProperty();
        List<AdCatalogueAdmin> filteredList = new ArrayList<>();

        if(comboValueProperty.get().equals(Vehicles.All.toString())){
            filteredList.addAll(getAllAdvertisement());
        }
        else if(comboValueProperty.get().equals("Not Approved")){
            for(AdCatalogueAdmin advertisement : advertisementList) {
                if(!advertisement.isApproved())
                    filteredList.add(advertisement);
            }
        }
        else if(comboValueProperty.get().equals("Approved"))
            for(AdCatalogueAdmin advertisement : advertisementList) {
                if(advertisement.isApproved())
                    filteredList.add(advertisement);
                System.out.println(advertisement.isApproved());
            }
        else{
            for(AdCatalogueAdmin advertisement : advertisementList) {
                    if (advertisement.getType().equals(comboValueProperty.getValue()))
                        filteredList.add(advertisement);
            }
        }
        advertisementList.setAll(filteredList);
    }

    /**
     * Sends a request to approve advertisement
     */
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


    /**
     * Sends a request to remove advertisement
     */
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

    /**
     * Selects advertisement
     * @param adCatalogueAdmin the advertisement in the form for admin
     */
    public void selectAdvertisement(AdCatalogueAdmin adCatalogueAdmin) {
        Advertisement advertisement = advertisementModel.getAdvertisement(adCatalogueAdmin.getAdvertisementID());
        System.out.println(advertisement.ifAdApproved());
        if(advertisement != null){
            LoggedUser.getLoggedUser().selectAdvertisement(advertisement);
            enableUserManagingProperty();
        }
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the disable of the open button
     */
    public BooleanProperty getOpenAdDisableProperty() {
        return openAdDisableProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the disable of the remove button
     */
    public BooleanProperty getRemoveDisableProperty() {
        return removeAdDisableProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the disable of the approve button
     */
    public BooleanProperty getApproveDisableProperty() {
        return approveAdDisableProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the list of all search values
     */
    public ObservableList<String> getComboItems() {
        return comboItems;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of the not approved label
     */
    public StringProperty getNotApprovedProperty() {
        return notApprovedProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of the total label
     */
    public StringProperty getTotalProperty() {
        return totalProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of the warning label
     */
    public StringProperty getWarningProperty() {
        return warningProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the visible property of the warning pane
     */
    public BooleanProperty getWarningVisibleProperty() {
        return warningVisibleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of the not approved label
     */
    public StringProperty getWarningStyleProperty() {
        return warningStyleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the list with all advertisements in admin form
     */
    public ObservableList<AdCatalogueAdmin> getAllAdvertisement() {
        return advertisementList;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of the search box
     */
    public StringProperty getComboValueProperty() {
        return comboValueProperty;
    }

    /**
     * Starts when reportReceived event appears. Change properties regarding the event
     * @param propertyChangeEvent the given event
     */
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

    /**
     * Starts when adApproved event appears. Change properties regarding the event
     * @param propertyChangeEvent the given event
     */
    private void newApprovedAd(PropertyChangeEvent propertyChangeEvent) {
        CatalogueAd newAd = (CatalogueAd) propertyChangeEvent.getNewValue();
        for(AdCatalogueAdmin catalogueAdmin : advertisementList)
        {
            if(catalogueAdmin.getAdvertisementID() == newAd.getAdvertisementID())
                catalogueAdmin.makeApproved();
        }
        disableUserManagingProperty();
    }

    /**
     * Starts when adRequested event appears. Change properties regarding the event
     * @param propertyChangeEvent the given event
     */
    private void newAdRequest(PropertyChangeEvent propertyChangeEvent) {
        AdCatalogueAdmin newAdvertisement = (AdCatalogueAdmin) propertyChangeEvent.getNewValue();
        advertisementList.add(newAdvertisement);
        disableUserManagingProperty();
    }

    /**
     * Starts when adRemoved event appears. Change properties regarding the event
     * @param propertyChangeEvent the given event
     */
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

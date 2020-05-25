package ESharing.Client.Views.MainAppView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorActionsModel;
import ESharing.Client.Model.AdvertisementModel.AdvertisementModel;
import ESharing.Client.Model.AppModel.AppOverviewModel;
import ESharing.Client.Model.ChatModel.ChatModel;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.Util.Events;
import ESharing.Shared.Util.PropertyChangeSubject;
import ESharing.Shared.Util.Vehicles;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * The class in a view model layer contains all functions which are used in the main system view.
 * @version 1.0
 * @author Group1
 */
public class MainAppViewModel implements PropertyChangeSubject {


    private BooleanProperty adRectangleVisibleProperty;
    private BooleanProperty messageRectangleVisibleProperty;
    private BooleanProperty settingRectangleVisibleProperty;
    private BooleanProperty homeRectangleVisibleProperty;
    private StringProperty notificationProperty;
    private StringProperty searchValueProperty;
    private ObservableList<String> searchItemsProperty;

    private PropertyChangeSupport support;

    private List<CatalogueAd> catalogueAds;

    private AdministratorActionsModel administratorActionsModel;
    private ChatModel chatModel;
    private UserActionsModel userActionsModel;
    private AdvertisementModel advertisementModel;
    private AppOverviewModel model;

    /**
     * A constructor initializes model layer for a main system features and all fields
     */
    public MainAppViewModel() {
        model = ModelFactory.getModelFactory().getAppOverviewModel();
        chatModel = ModelFactory.getModelFactory().getChatModel();
        userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();
        administratorActionsModel = ModelFactory.getModelFactory().getAdministratorActionsModel();
        advertisementModel = ModelFactory.getModelFactory().getAdvertisementModel();

        notificationProperty = new SimpleStringProperty();
        settingRectangleVisibleProperty = new SimpleBooleanProperty();
        adRectangleVisibleProperty = new SimpleBooleanProperty();
        homeRectangleVisibleProperty = new SimpleBooleanProperty();
        messageRectangleVisibleProperty = new SimpleBooleanProperty();
        searchValueProperty = new SimpleStringProperty();

        catalogueAds = new ArrayList<>();
        searchItemsProperty = FXCollections.observableArrayList();

        support = new PropertyChangeSupport(this);

        administratorActionsModel.addPropertyChangeListener(Events.USER_REMOVED.toString(), this::userRemoved);
        chatModel.addPropertyChangeListener(Events.MAKE_MESSAGE_READ.toString(), this::makeMessageRead);
        chatModel.addPropertyChangeListener(Events.NEW_MESSAGE_RECEIVED.toString(), this::newMessageReceived);
        advertisementModel.addPropertyChangeListener(Events.NEW_APPROVED_AD.toString(), this::newApprovedAd);
        advertisementModel.addPropertyChangeListener(Events.AD_REMOVED.toString(), this::adRemoved);
    }

    private void adRemoved(PropertyChangeEvent propertyChangeEvent) {
        int advertisementID = (int) propertyChangeEvent.getNewValue();
        for(int i = 0 ; i < catalogueAds.size() ; i++){
            if(catalogueAds.get(i).getAdvertisementID() == advertisementID) {
                catalogueAds.remove(catalogueAds.get(i));
                support.firePropertyChange(Events.UPDATE_AD_LIST.toString(), null, catalogueAds);
            }
        }

    }

    private void newApprovedAd(PropertyChangeEvent propertyChangeEvent) {
        CatalogueAd newCatalogue = (CatalogueAd) propertyChangeEvent.getNewValue();
        catalogueAds.add(newCatalogue);

        support.firePropertyChange(Events.UPDATE_AD_LIST.toString(), null, catalogueAds);
    }

    /**
     * Sets default visible properties for rectangles objects
     */
    public void resetRectanglesVisibleProperty()
    {
        catalogueAds.addAll(advertisementModel.getAllCatalogues());

        messageRectangleVisibleProperty.setValue(false);
        adRectangleVisibleProperty.setValue(false);
        settingRectangleVisibleProperty.setValue(false);
        homeRectangleVisibleProperty.setValue(false);

        searchItemsProperty.clear();
        for(int i = 0; i < Vehicles.values().length ; i++) {
            searchItemsProperty.add(Vehicles.values()[i].toString());
        }

//        LoggedUser.getLoggedUser().selectAdvertisement(null);
    }

   public List<CatalogueAd> getCatalogueAds()
   {
       catalogueAds.clear();
       catalogueAds.addAll(advertisementModel.getAllCatalogues());
       return catalogueAds;
   }








    /**
     * Sets visible property of the setting rectangle object as true
     */
    public void setSettingRectangleSelected()
    {
        resetRectanglesVisibleProperty();
        settingRectangleVisibleProperty.setValue(true);
    }

    /**
     * Sets visible property of the advertisement rectangle object as true
     */
    public void setAdRectangleSelected()
    {
        resetRectanglesVisibleProperty();
        adRectangleVisibleProperty.setValue(true);
    }

    /**
     * Sets visible property of the message rectangle object as true
     */
    public void setMessageRectangleSelected()
    {
        resetRectanglesVisibleProperty();
        messageRectangleVisibleProperty.setValue(true);
    }

    public void setHomeRectangleSelected()
    {
        resetRectanglesVisibleProperty();
        homeRectangleVisibleProperty.setValue(true);
    }

    /**
     * Sends a request to the model layer for logging out current logged user
     */
    public void userLoggedOut() {
        userActionsModel.logoutUser();
    }

    /**
     * Sends a request to the model layer for loading all unread messages as a text property of the notification label
     */
    public void loadNotifications(){
        notificationProperty.setValue(String.valueOf(chatModel.getAllUnreadMessages()));
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the string property of a notification label
     */
    public StringProperty getNotificationProperty() {
        return notificationProperty; }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the visible property of a advertisement rectangle
     */
    public BooleanProperty getAdRectangleVisibleProperty() {
        return adRectangleVisibleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the visible property of a setting rectangle
     */
    public BooleanProperty getSettingRectangleVisibleProperty() {
        return settingRectangleVisibleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the visible property of a message rectangle
     */
    public BooleanProperty getMessageRectangleVisibleProperty() {
        return messageRectangleVisibleProperty;
    }

    /**
     * Reloads the text property of the notification label when new message appears
     * @param propertyChangeEvent the new message received event
     */
    private void newMessageReceived(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(() ->{
            notificationProperty.setValue(String.valueOf(chatModel.getAllUnreadMessages()));
        });
        //support.firePropertyChange(propertyChangeEvent);
    }

    /**
     * Sends an event to the view when administrator removed the current logged user account
     * @param propertyChangeEvent the administrator remove account event
     */
    private void userRemoved(PropertyChangeEvent propertyChangeEvent) {
        support.firePropertyChange(Events.USER_LOGOUT.toString(), null, null);
    }

    /**
     * Reloads the text property of the notification label when read message event appears
     * @param propertyChangeEvent the read message event
     */
    private void makeMessageRead(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(() ->{
            System.out.println("MESSAGE BECOME READ IN MAIN VIEW MODEL");
            notificationProperty.setValue(String.valueOf(chatModel.getAllUnreadMessages()));
        });
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

    public void selectAdvertisement(CatalogueAd catalogueAd) {
        Advertisement selectedAdvertisement = advertisementModel.getAdvertisement(catalogueAd.getAdvertisementID());
        LoggedUser.getLoggedUser().selectAdvertisement(selectedAdvertisement);
        LoggedUser.getLoggedUser().setSelectedUser(selectedAdvertisement.getOwner());
    }

    public BooleanProperty getHomeRectangleVisibleProperty() {
        return homeRectangleVisibleProperty;
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

        for(CatalogueAd catalogueAd : catalogueAds)
        {
            System.out.println(catalogueAd.getVehicleType());
            if(catalogueAd.getVehicleType().equals(searchValueProperty.getValue())) {
                filteredAds.add(catalogueAd);
            }
        }

        support.firePropertyChange(Events.UPDATE_AD_LIST.toString(), null, filteredAds);
    }
}
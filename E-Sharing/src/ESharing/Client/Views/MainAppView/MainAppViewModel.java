package ESharing.Client.Views.MainAppView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorActionsModel;
import ESharing.Client.Model.ChatModel.ChatModel;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Shared.Util.Events;
import ESharing.Shared.Util.PropertyChangeSubject;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

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

    private PropertyChangeSupport support;

    private AdministratorActionsModel administratorActionsModel;
    private ChatModel chatModel;
    private UserActionsModel userActionsModel;

    /**
     * A constructor initializes model layer for a main system features and all fields
     */
    public MainAppViewModel() {
        chatModel = ModelFactory.getModelFactory().getChatModel();
        userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();
        administratorActionsModel = ModelFactory.getModelFactory().getAdministratorActionsModel();

        notificationProperty = new SimpleStringProperty();
        settingRectangleVisibleProperty = new SimpleBooleanProperty();
        adRectangleVisibleProperty = new SimpleBooleanProperty();
        homeRectangleVisibleProperty = new SimpleBooleanProperty();
        messageRectangleVisibleProperty = new SimpleBooleanProperty();

        support = new PropertyChangeSupport(this);

        administratorActionsModel.addPropertyChangeListener(Events.USER_REMOVED.toString(), this::userRemoved);
        chatModel.addPropertyChangeListener(Events.MAKE_MESSAGE_READ.toString(), this::makeMessageRead);
        chatModel.addPropertyChangeListener(Events.NEW_MESSAGE_RECEIVED.toString(), this::newMessageReceived);
    }

    /**
     * Sets default visible properties for rectangles objects
     */
    public void resetRectanglesVisibleProperty()
    {
        messageRectangleVisibleProperty.setValue(false);
        adRectangleVisibleProperty.setValue(false);
        settingRectangleVisibleProperty.setValue(false);
        homeRectangleVisibleProperty.setValue(false);
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

    public BooleanProperty getHomeRectangleVisibleProperty() {
        return homeRectangleVisibleProperty;
    }
}
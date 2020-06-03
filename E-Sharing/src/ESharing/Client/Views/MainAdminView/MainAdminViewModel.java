package ESharing.Client.Views.MainAdminView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorActionsModel;
import ESharing.Client.Model.AdministratorModel.AdministratorLists;
import ESharing.Client.Model.AdvertisementModel.AdvertisementModel;
import ESharing.Client.Model.ChatModel.ChatModel;
import ESharing.Client.Model.ReservationModel.ReservationModel;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Shared.TransferedObject.Reservation;
import ESharing.Shared.Util.Events;
import com.sun.webkit.Timer;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;

import java.beans.PropertyChangeEvent;

/**
 * The class in a view model layer contains all functions which are used in the main admin setting view.
 * @version 1.0
 * @author Group1
 */
public class MainAdminViewModel {

    private AdministratorActionsModel administratorActionsModel;
    private UserActionsModel userActionsModel;
    private ChatModel chatModel;
    private BooleanProperty rectangleProperty;
    private BooleanProperty usersProperty;
    private BooleanProperty dashboardProperty;

    private StringProperty notificationProperty;

    /**
     * A constructor initializes model layer for a user setting features
     */
    public MainAdminViewModel() {
        this.administratorActionsModel = ModelFactory.getModelFactory().getAdministratorActionsModel();
        this.userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();
        this.chatModel = ModelFactory.getModelFactory().getChatModel();

        dashboardProperty = new SimpleBooleanProperty();
        usersProperty = new SimpleBooleanProperty();
        rectangleProperty = new SimpleBooleanProperty();

        notificationProperty = new SimpleStringProperty();
        chatModel.addPropertyChangeListener(Events.NEW_MESSAGE_RECEIVED.toString(), this::increaseNotification);
    }

    /**
     * Loads users
     */
    public void loadUsersListRequest() {
        AdministratorLists.getInstance().setUserList(administratorActionsModel.getAllUsers());
    }

    /**
     * Sets default view
     */
    public void setDefaultView(){
        hideRectangle();
        dashboardProperty.setValue(true);
        loadNotifications();

    }

    /**
     * Change rectangles visible property
     */
    public void hideRectangle(){
        dashboardProperty.setValue(false);
        usersProperty.setValue(false);
        rectangleProperty.setValue(false);
    }

    /**
     * Sends a request to logout
     */
    public void onLogoutRequest() {
        userActionsModel.logoutUser();
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a notification
     */
    public StringProperty getNotificationProperty() {
        return notificationProperty;
    }

    /**
     * Increases the message notification
     * @param propertyChangeEvent
     */
    private void increaseNotification(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(() ->{
            notificationProperty.setValue(String.valueOf(chatModel.getAllUnreadMessages()));
        });
    }

    /**
     * Loads message notification
     */
    public void loadNotifications()
    {
        notificationProperty.setValue(String.valueOf(chatModel.getAllUnreadMessages()));
    }
    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a ad rectangle
     */
    public BooleanProperty getAdRectangleProperty() {
        return rectangleProperty;
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of dashboard rectangle
     */
    public BooleanProperty getDashboardProperty() {
        return dashboardProperty;
    }
    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a user rectangle
     */
    public BooleanProperty getUsersProperty() {
        return usersProperty;
    }

    /**
     * Change rectangle visible property
     */
    public void setUsersRequest() {
        hideRectangle();
        usersProperty.setValue(true);
    }
    /**
     * Change rectangle visible property
     */
    public void onDashboardRequest() {
        hideRectangle();
        dashboardProperty.setValue(true);
    }
    /**
     * Change rectangle visible property
     */
    public void setAdvertisementsRequest() {
        hideRectangle();
        rectangleProperty.setValue(true);
    }
}

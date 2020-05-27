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

public class MainAdminViewModel {

    private AdministratorActionsModel administratorActionsModel;
    private UserActionsModel userActionsModel;
    private ChatModel chatModel;
    private BooleanProperty rectangleProperty;
    private BooleanProperty usersProperty;
    private BooleanProperty dashboardProperty;
    private AdvertisementModel advertisementModel;

    private StringProperty notificationProperty;

    public MainAdminViewModel() {
        this.administratorActionsModel = ModelFactory.getModelFactory().getAdministratorActionsModel();
        this.advertisementModel = ModelFactory.getModelFactory().getAdvertisementModel();
        this.userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();
        this.chatModel = ModelFactory.getModelFactory().getChatModel();

        dashboardProperty = new SimpleBooleanProperty();
        usersProperty = new SimpleBooleanProperty();
        rectangleProperty = new SimpleBooleanProperty();

        notificationProperty = new SimpleStringProperty();
        chatModel.addPropertyChangeListener(Events.NEW_MESSAGE_RECEIVED.toString(), this::increaseNotification);
    }

    public void loadUsersListRequest() {
        AdministratorLists.getInstance().setUserList(administratorActionsModel.getAllUsers());
    }

    public void setDefaultView(){
        hideRectangle();
        dashboardProperty.setValue(true);
        loadNotifications();

    }

    public void hideRectangle(){
        dashboardProperty.setValue(false);
        usersProperty.setValue(false);
        rectangleProperty.setValue(false);
    }

    public void onLogoutRequest() {
        userActionsModel.logoutUser();
    }

    public StringProperty getNotificationProperty() {
        return notificationProperty;
    }

    private void increaseNotification(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(() ->{
            notificationProperty.setValue(String.valueOf(chatModel.getAllUnreadMessages()));
        });
    }

    public void loadNotifications()
    {
        notificationProperty.setValue(String.valueOf(chatModel.getAllUnreadMessages()));
    }

    public BooleanProperty getAdRectangleProperty() {
        return rectangleProperty;
    }

    public BooleanProperty getDashboardProperty() {
        return dashboardProperty;
    }

    public BooleanProperty getUsersProperty() {
        return usersProperty;
    }

    public void setUsersRequest() {
        hideRectangle();
        usersProperty.setValue(true);
    }

    public void onDashboardRequest() {
        hideRectangle();
        dashboardProperty.setValue(true);
    }

    public void setAdvertisementsRequest() {
        hideRectangle();
        rectangleProperty.setValue(true);
    }
}

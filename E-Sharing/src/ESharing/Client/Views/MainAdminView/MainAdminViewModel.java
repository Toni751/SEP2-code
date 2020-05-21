package ESharing.Client.Views.MainAdminView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorActionsModel;
import ESharing.Client.Model.AdministratorModel.AdministratorLists;
import ESharing.Client.Model.AdvertisementModel.AdvertisementModel;
import ESharing.Client.Model.ChatModel.ChatModel;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Shared.Util.Events;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;

import java.beans.PropertyChangeEvent;

public class MainAdminViewModel {

    private AdministratorActionsModel administratorActionsModel;
    private UserActionsModel userActionsModel;
    private ChatModel chatModel;
    private AdvertisementModel advertisementModel;

    private StringProperty notificationProperty;

    public MainAdminViewModel() {
        this.administratorActionsModel = ModelFactory.getModelFactory().getAdministratorActionsModel();
        this.advertisementModel = ModelFactory.getModelFactory().getAdvertisementModel();
        this.userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();
        this.chatModel = ModelFactory.getModelFactory().getChatModel();

        notificationProperty = new SimpleStringProperty();
        chatModel.addPropertyChangeListener(Events.NEW_MESSAGE_RECEIVED.toString(), this::increaseNotification);
    }

    public void loadUsersListRequest() {
        AdministratorLists.getInstance().setUserList(administratorActionsModel.getAllUsers());
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
}

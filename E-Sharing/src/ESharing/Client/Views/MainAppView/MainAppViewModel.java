package ESharing.Client.Views.MainAppView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorActionsModel;
import ESharing.Client.Model.AppModel.AppOverviewModel;
import ESharing.Client.Model.ChatModel.ChatModel;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Shared.Util.Events;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.PropertyChangeSubject;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The class in a view model layer contains all functions which are used in the signUp view.
 * @version 1.0
 * @author Group1
 */
public class MainAppViewModel implements PropertyChangeSubject {

    private AppOverviewModel model;
    private LoggedUser loggedUser;

    private StringProperty notificationProperty;

    private AdministratorActionsModel administratorActionsModel;
    private ChatModel chatModel;
    private UserActionsModel userActionsModel;
    private PropertyChangeSupport support;

    /**
     * A constructor initializes model layer for a user features and all fields
     */
    public MainAppViewModel() {
        this.model = ModelFactory.getModelFactory().getAppOverviewModel();
        this.chatModel = ModelFactory.getModelFactory().getChatModel();
        this.loggedUser = LoggedUser.getLoggedUser();
        this.userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();
        support = new PropertyChangeSupport(this);
        administratorActionsModel = ModelFactory.getModelFactory().getAdministratorActionsModel();

        notificationProperty = new SimpleStringProperty();

        administratorActionsModel.addPropertyChangeListener(Events.USER_REMOVED.toString(), this::userRemoved);
        chatModel.addPropertyChangeListener(Events.MAKE_MESSAGE_READ.toString(), this::makeMessageRead);
        chatModel.addPropertyChangeListener(Events.NEW_MESSAGE_RECEIVED.toString(), this::newMessageReceived);
    }

    private void newMessageReceived(PropertyChangeEvent propertyChangeEvent) {
        support.firePropertyChange(propertyChangeEvent);
    }

    private void userRemoved(PropertyChangeEvent propertyChangeEvent) {
        if (LoggedUser.getLoggedUser().getUser().equals(propertyChangeEvent.getNewValue()))
            support.firePropertyChange(Events.USER_LOGOUT.toString(), null, "Your account has been removed from the system!");
    }

    public void userLoggedOut() {
        userActionsModel.logoutUser();
        //chatModel.userLoggedOut();
    }

    public StringProperty getNotificationProperty() {
        return notificationProperty; }

    private void makeMessageRead(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(() ->{
            System.out.println("MESSAGE BECOME READ IN MAIN VIEW MODEL");
            notificationProperty.setValue(String.valueOf(chatModel.getAllUnreadMessages()));
        });
    }

    public void loadNotifications(){
        notificationProperty.setValue(String.valueOf(chatModel.getAllUnreadMessages()));
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
package ESharing.Client.Views.MainAppView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorActionsModel;
import ESharing.Client.Model.AppModel.AppOverviewModel;
import ESharing.Client.Model.ChatModel.ChatModel;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Shared.TransferedObject.Events;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.PropertyChangeSubject;
import jdk.jfr.Event;

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

    private AdministratorActionsModel administratorActionsModel;
    private ChatModel chatModel;
    private PropertyChangeSupport support;

    /**
     * A constructor initializes model layer for a user features and all fields
     */
    public MainAppViewModel()
    {
        this.model = ModelFactory.getModelFactory().getAppOverviewModel();
        this.chatModel = ModelFactory.getModelFactory().getChatModel();
        this.loggedUser = LoggedUser.getLoggedUser();
        support = new PropertyChangeSupport(this);
        administratorActionsModel = ModelFactory.getModelFactory().getAdministratorActionsModel();

        administratorActionsModel.addPropertyChangeListener(Events.USER_REMOVED.toString(), this::userRemoved);
        administratorActionsModel.addPropertyChangeListener(Events.USER_UPDATED.toString(), this::userUpdated);
        chatModel.addPropertyChangeListener(Events.MAKE_CONVERSATION_READ.toString(), this::makeMessageRead);
    }

    private void makeMessageRead(PropertyChangeEvent propertyChangeEvent) {
        support.firePropertyChange(propertyChangeEvent);
    }

    private void userUpdated(PropertyChangeEvent propertyChangeEvent) {
        User updatedUser = (User) propertyChangeEvent.getNewValue();
        if(LoggedUser.getLoggedUser().getUser() != null && LoggedUser.getLoggedUser().getUser().getUser_id() == updatedUser.getUser_id()) {
            support.firePropertyChange(Events.USER_LOGOUT.toString(), null, "Your account has been changed by the administrator");
        }
    }

    private void userRemoved(PropertyChangeEvent propertyChangeEvent) {
        if(LoggedUser.getLoggedUser().getUser().equals(propertyChangeEvent.getNewValue()))
            support.firePropertyChange(Events.USER_LOGOUT.toString(), null, "Your account has been removed from the system!");
    }

    @Override
    public void addPropertyChangeListener(String eventName, PropertyChangeListener listener)
    {
        if ("".equals(eventName) || eventName == null)
            addPropertyChangeListener(listener);
        else
            support.addPropertyChangeListener(eventName, listener);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(String eventName, PropertyChangeListener listener)
    {
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

package ESharing.Client.Views.ChatView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.ChatModel.ChatModel;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Model.VerificationModel.VerificationModel;
import ESharing.Shared.Util.Events;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.GeneralFunctions;
import ESharing.Shared.Util.PropertyChangeSubject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/**
 * The class in a view model layer contains all functions which are used in the chhat view.
 *
 * @author Group1
 * @version 1.0
 */
public class ChatViewModel implements PropertyChangeSubject {
    private StringProperty messageProperty;
    private StringProperty receiverProperty;
    private ObservableList<Message> conversations;
    private ObservableList<User> users;
    private PropertyChangeSupport support;
    private User currentReceiver;

    private ChatModel chatModel;
    private VerificationModel verificationModel;

    /**
     * A constructor initializes model layer for a chat features and all fields
     */
    public ChatViewModel()
    {
        chatModel = ModelFactory.getModelFactory().getChatModel();
        verificationModel = ModelFactory.getModelFactory().getVerificationModel();
        support = new PropertyChangeSupport(this);

        conversations = FXCollections.observableArrayList();
        users = FXCollections.observableArrayList();
        messageProperty = new SimpleStringProperty();
        receiverProperty = new SimpleStringProperty();

        currentReceiver = null;

        chatModel.addPropertyChangeListener(Events.NEW_MESSAGE_RECEIVED.toString(), this::newMessageReceived);
        chatModel.addPropertyChangeListener(Events.USER_ONLINE.toString(), this::newOnlineUser);
        chatModel.addPropertyChangeListener(Events.USER_OFFLINE.toString(), this::userOffline);
    }

    /**
     * Sends a request for sending new message
     */
    public void sendMessage() {
        System.out.println(LoggedUser.getLoggedUser().getCurrentOpenConversation().size());
        if(LoggedUser.getLoggedUser().getCurrentOpenConversation() != null && !LoggedUser.getLoggedUser().getCurrentOpenConversation().isEmpty()) {
            if (verificationModel.verifyMessage(messageProperty.get())) {
                User receiver;
                if (LoggedUser.getLoggedUser().getCurrentOpenConversation().get(LoggedUser.getLoggedUser().getCurrentOpenConversation().size()-1).getSender().getUser_id() == LoggedUser.getLoggedUser().getUser().getUser_id())
                    receiver = LoggedUser.getLoggedUser().getCurrentOpenConversation().get(LoggedUser.getLoggedUser().getCurrentOpenConversation().size()-1).getReceiver();
                else
                    receiver = LoggedUser.getLoggedUser().getCurrentOpenConversation().get(LoggedUser.getLoggedUser().getCurrentOpenConversation().size()-1).getSender();
                chatModel.sendPrivateMessage(new Message(LoggedUser.getLoggedUser().getUser(), receiver, messageProperty.get(), false));
            }
        }
        if(currentReceiver != null)
        {
            Message message = new Message(LoggedUser.getLoggedUser().getUser(), currentReceiver, messageProperty.get(), false);
            LoggedUser.getLoggedUser().getCurrentOpenConversation().add(message);
            chatModel.sendPrivateMessage(message);
            currentReceiver = null;
        }
    }

    /**
     * Returns all online users
     * @return all online users
     */
    public ObservableList<User> getUsers() {
        System.out.println(chatModel.getOnlineUsers());
        users.setAll(chatModel.getOnlineUsers());
        return users;
    }

    /**
     * Clears lists
     */
    public void clearList() {
        users.clear();
        conversations.clear();
    }

    /**
     * Resets view
     */
    public void resetView()
    {
        receiverProperty.setValue("");
    }

    /**
     * Returns conversation shortcuts
     * @return the conversation shortcuts
     */
    public ObservableList<Message> getConversations() {
        conversations.clear();
        conversations.addAll(chatModel.loadConversationShortcuts());
        return conversations;
    }

    /**
     * Loads conversation
     * @param sender the sender
     * @param receiver the receiver
     */
    public void loadConversation(User sender, User receiver) {
            currentReceiver = null;
            LoggedUser.getLoggedUser().setCurrentOpenConversation(chatModel.loadConversation(sender, receiver));
            if(sender.getUser_id() == LoggedUser.getLoggedUser().getUser().getUser_id())
                receiverProperty.setValue(receiver.getUsername());
            else
                receiverProperty.setValue(sender.getUsername());
        LoggedUser.getLoggedUser().setSelectedUser(null);
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a message
     */
    public StringProperty getMessageProperty() {
        return messageProperty;
    }

    /**
     * Sends a request to make conversation read
     */
    public void makeConversationRead() {
        chatModel.makeMessageRead();
    }

    /**
     * Returns value used in the bind process between a controller and view model
     * @return the property of a receiver
     */
    public StringProperty getReceiverProperty() {
        return receiverProperty;
    }

    /**
     * Check if view model has listeners
     * @return result of checking
     */
    public boolean ifViewModelHasListeners()
    {
        return support.getPropertyChangeListeners().length != 0;
    }

    /**
     * If conversation exists loads that otherwise set a temporary receiver who will be receiver for next message
     * @param anotherUser the another user
     */
    public void ifConversationExists(User anotherUser) {
        currentReceiver = null;
        LoggedUser.getLoggedUser().setCurrentOpenConversation(new ArrayList<>());
        for(Message message : chatModel.loadConversationShortcuts())
        {
            if((message.getSender().getUser_id() == LoggedUser.getLoggedUser().getUser().getUser_id() && message.getReceiver().getUser_id() == anotherUser.getUser_id())) {
                loadConversation(LoggedUser.getLoggedUser().getUser(), anotherUser);
                makeConversationRead();
            }
            if((message.getReceiver().getUser_id() == LoggedUser.getLoggedUser().getUser().getUser_id() && message.getSender().getUser_id() == anotherUser.getUser_id())) {
                loadConversation(anotherUser, LoggedUser.getLoggedUser().getUser());
                makeConversationRead();
            }
        }
        if(LoggedUser.getLoggedUser().getCurrentOpenConversation().isEmpty()) {
            currentReceiver = anotherUser;
            receiverProperty.setValue(anotherUser.getUsername());
        }
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

    /**
     * When new event appears, the function reloads the view with new values
     * @param propertyChangeEvent the given event
     */
    private void userOffline(PropertyChangeEvent propertyChangeEvent) {
        support.firePropertyChange(propertyChangeEvent);
    }
    /**
     * When new event appears, the function reloads the view with new values
     * @param propertyChangeEvent the given event
     */
    private void newOnlineUser(PropertyChangeEvent propertyChangeEvent) {
        support.firePropertyChange(propertyChangeEvent);
    }
    /**
     * When new event appears, the function reloads the view with new values
     * @param propertyChangeEvent the given event
     */
    private void newMessageReceived(PropertyChangeEvent propertyChangeEvent) {
        System.out.println("MESSAGE CHAT VIEW MODEL!!!!!!!!!!!!!!");
        Message message = (Message) propertyChangeEvent.getNewValue();
        if (!LoggedUser.getLoggedUser().getCurrentOpenConversation().isEmpty()) {
            if(GeneralFunctions.usersInCurrentConversation(LoggedUser.getLoggedUser().getUser(), message.getSender())
                    || GeneralFunctions.usersInCurrentConversation(LoggedUser.getLoggedUser().getUser(), message.getReceiver())
                    || currentReceiver != null)
            {
                LoggedUser.getLoggedUser().getCurrentOpenConversation().add(message);
                support.firePropertyChange(propertyChangeEvent);
                makeConversationRead();
            }
        }
        else if(currentReceiver == null) {}
        else if(message.getSender().getUser_id() == currentReceiver.getUser_id()) {
            LoggedUser.getLoggedUser().getCurrentOpenConversation().add(message);
            support.firePropertyChange(propertyChangeEvent);
            makeConversationRead();
            loadConversation(currentReceiver, LoggedUser.getLoggedUser().getUser());
            currentReceiver = null;
        }
        getConversations();
        support.firePropertyChange(Events.UPDATE_CONVERSATION_LIST.toString(), null, null);
    }

}

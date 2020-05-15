package ESharing.Client.Model.ChatModel;

import ESharing.Client.Core.ClientFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Networking.chat.ClientChat;
import ESharing.Client.Networking.user.Client;
import ESharing.Shared.TransferedObject.Conversation;
import ESharing.Shared.TransferedObject.Events;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;
import jdk.jfr.Event;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class ChatModelManager implements ChatModel{

    private ClientChat client;
    private PropertyChangeSupport support;

    public ChatModelManager()
    {
        client = ClientFactory.getClientFactory().getChatClient();
        support = new PropertyChangeSupport(this);
        client.addPropertyChangeListener(Events.NEW_MESSAGE_RECEIVED.toString(), this::newMessageReceived);
        client.addPropertyChangeListener(Events.USER_ONLINE.toString(), this::newOnlineUser);
        client.addPropertyChangeListener(Events.USER_OFFLINE.toString(),this::newOfflineUser);
    }

    private void newOfflineUser(PropertyChangeEvent event)
    {
        support.firePropertyChange(event);
        System.out.println("chat model fired new offline user event");
    }

    private void newOnlineUser(PropertyChangeEvent event)
    {
        support.firePropertyChange(event);
        System.out.println("chat model fired new user event");
    }

    private void newMessageReceived(PropertyChangeEvent propertyChangeEvent) {
        support.firePropertyChange(propertyChangeEvent);
    }

    @Override
    public List<User> getOnlineUsers() {
        return client.getOnlineUsers();
    }

    @Override
    public void sendPrivateMessage(Message message) {
       client.addMessage(message);
    }

    @Override
    public void makeMessageRead(Message message) {
        client.makeMessageRead(message);
        support.firePropertyChange(Events.MAKE_CONVERSATION_READ.toString(), null, message);
    }

    @Override
    public List<Message> loadConversationShortcuts() {
        return client.getLastMessageWithEveryone(LoggedUser.getLoggedUser().getUser());
    }

    @Override
    public List<Message> loadConversation(User sender, User receiver) {
        return client.loadConversation(sender, receiver);
    }

    @Override
    public int getAllUnreadMessages() {
        return client.getNoUnreadMessages(LoggedUser.getLoggedUser().getUser());
    }

    @Override public void userLoggedOut()
    {
        client.userLoggedOut();
        client.logout();
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

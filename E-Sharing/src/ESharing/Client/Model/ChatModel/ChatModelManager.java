package ESharing.Client.Model.ChatModel;

import ESharing.Client.Core.ClientFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Networking.chat.ClientChat;
import ESharing.Shared.Util.Events;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.GeneralFunctions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
        client.addPropertyChangeListener(Events.MAKE_MESSAGE_READ.toString(), this::readMessageReceived);
    }

    private void readMessageReceived(PropertyChangeEvent propertyChangeEvent) {
        Message newMessage = (Message) propertyChangeEvent.getNewValue();
        if(loggedUserPartOfTheMessage(newMessage)) {
            support.firePropertyChange(propertyChangeEvent);
            if(LoggedUser.getLoggedUser().getCurrentOpenConversation().isEmpty())
                support.firePropertyChange(Events.MAKE_MESSAGE_READ.toString(), null, null);
            else if(!GeneralFunctions.usersInCurrentConversation(LoggedUser.getLoggedUser().getUser(), newMessage.getSender())
                    && !GeneralFunctions.usersInCurrentConversation(LoggedUser.getLoggedUser().getUser(), newMessage.getReceiver()))
                support.firePropertyChange(Events.MAKE_MESSAGE_READ.toString(), null, null);
        }
    }

    private void newOfflineUser(PropertyChangeEvent event)
    {
        support.firePropertyChange(event);
    }

    private void newOnlineUser(PropertyChangeEvent event)
    {
        support.firePropertyChange(event);
    }

    private void newMessageReceived(PropertyChangeEvent propertyChangeEvent) {
        System.out.println("ESSAGE RECEIVED IN CHAT MODEL");
        Message newMessage = (Message) propertyChangeEvent.getNewValue();
        if(loggedUserPartOfTheMessage(newMessage)) {
            support.firePropertyChange(propertyChangeEvent);
        }
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
    public void makeMessageRead() {
        for(Message message : LoggedUser.getLoggedUser().getCurrentOpenConversation()) {
            if (message.getSender().getUser_id() != LoggedUser.getLoggedUser().getUser().getUser_id())
                if (!message.isRead())
                    client.makeMessageRead(message);
        }
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


    private boolean loggedUserPartOfTheMessage(Message newMessage)
    {
        System.out.println(LoggedUser.getLoggedUser().getUser().getUsername());
        System.out.println(newMessage.getSender().getUsername());
        System.out.println(newMessage.getReceiver().getUsername());

        if(LoggedUser.getLoggedUser().getUser().getUser_id() == newMessage.getSender().getUser_id()
                || LoggedUser.getLoggedUser().getUser().getUser_id() == newMessage.getReceiver().getUser_id())
            return true;
        return false;
    }
}

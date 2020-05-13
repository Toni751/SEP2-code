package ESharing.Client.Model.ChatModel;

import ESharing.Client.Core.ClientFactory;
import ESharing.Client.Networking.chat.ClientChat;
import ESharing.Client.Networking.user.Client;
import ESharing.Shared.TransferedObject.Conversation;
import ESharing.Shared.TransferedObject.Events;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class ChatModelManager implements ChatModel{

    private ClientChat client;
    private PropertyChangeSupport support;

    public ChatModelManager()
    {
        client = ClientFactory.getClientFactory().getChatClient();
        support = new PropertyChangeSupport(this);
    }

    @Override
    public ArrayList<User> getOnlineUsers() {
        return null;
    }

    @Override
    public boolean sendPrivateMessage(Message message) {
        return false;
    }

    @Override
    public Conversation createNewConversation(User sender, User receiver) {
        return null;
    }

    @Override
    public void makeConversationRead(Conversation conversation) {
        support.firePropertyChange(Events.MAKE_CONVERSATION_READ.toString(), null, conversation);
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

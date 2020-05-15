package ESharing.Client.Views.ChatView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.ChatModel.ChatModel;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Model.VerificationModel.VerificationModel;
import ESharing.Shared.TransferedObject.Conversation;
import ESharing.Shared.TransferedObject.Events;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.PropertyChangeSubject;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jdk.jfr.Event;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class ChatViewModel implements PropertyChangeSubject {
    private StringProperty searchProperty;
    private StringProperty messageProperty;
    private StringProperty receiverProperty;
    private ObservableList<Message> conversations;
    private ObservableList<User> users;
    private PropertyChangeSupport support;
    private User currentReceiver;

    private ChatModel chatModel;
    private VerificationModel verificationModel;

    public ChatViewModel()
    {
        chatModel = ModelFactory.getModelFactory().getChatModel();
        verificationModel = ModelFactory.getModelFactory().getVerificationModel();
        support = new PropertyChangeSupport(this);

        conversations = FXCollections.observableArrayList();
        users = FXCollections.observableArrayList();

        searchProperty = new SimpleStringProperty();
        messageProperty = new SimpleStringProperty();
        receiverProperty = new SimpleStringProperty();

        chatModel.addPropertyChangeListener(Events.NEW_MESSAGE_RECEIVED.toString(), this::newMessageReceived);
        chatModel.addPropertyChangeListener(Events.USER_ONLINE.toString(), this::newOnlineUser);
        chatModel.addPropertyChangeListener(Events.USER_OFFLINE.toString(), this::userOffline);
    }

    private void userOffline(PropertyChangeEvent propertyChangeEvent) {
        support.firePropertyChange(propertyChangeEvent);
    }

    private void newOnlineUser(PropertyChangeEvent propertyChangeEvent) {
        support.firePropertyChange(propertyChangeEvent);
    }

    private void newMessageReceived(PropertyChangeEvent propertyChangeEvent) {
        Message message = (Message) propertyChangeEvent.getNewValue();
        getConversations();
        if ((!LoggedUser.getLoggedUser().getCurrentOpenConversation().isEmpty() && (LoggedUser.getLoggedUser().getUser().getUser_id() == LoggedUser.getLoggedUser().getCurrentOpenConversation().get(0).getSender().getUser_id()
        || LoggedUser.getLoggedUser().getUser().getUser_id() == LoggedUser.getLoggedUser().getCurrentOpenConversation().get(0).getReceiver().getUser_id())) || currentReceiver != null) {
            if (currentReceiver != null)
                currentReceiver = null;
            LoggedUser.getLoggedUser().getCurrentOpenConversation().add(message);
            support.firePropertyChange(propertyChangeEvent);
            makeConversationRead();
            System.out.println("Message recived in view model");
        }
        support.firePropertyChange(Events.UPDATE_CONVERSATION_LIST.toString(), null, null);
    }

    public void sendMessage() {
        if(LoggedUser.getLoggedUser().getCurrentOpenConversation() != null && !LoggedUser.getLoggedUser().getCurrentOpenConversation().isEmpty()) {
            if (verificationModel.verifyMessage(messageProperty.get())) {
                User receiver;
                if (LoggedUser.getLoggedUser().getCurrentOpenConversation().get(0).getSender().getUser_id() == LoggedUser.getLoggedUser().getUser().getUser_id())
                    receiver = LoggedUser.getLoggedUser().getCurrentOpenConversation().get(0).getReceiver();
                else
                    receiver = LoggedUser.getLoggedUser().getCurrentOpenConversation().get(0).getSender();
                chatModel.sendPrivateMessage(new Message(LoggedUser.getLoggedUser().getUser(), receiver, messageProperty.get(), false));
            }
        }
        if(currentReceiver != null)
        {
            System.out.println(currentReceiver.getUser_id() + currentReceiver.getUsername());
            loadConversation(LoggedUser.getLoggedUser().getUser(), currentReceiver);
            getConversations();
            chatModel.sendPrivateMessage(new Message(LoggedUser.getLoggedUser().getUser(), currentReceiver, messageProperty.get(), false));
            System.out.println("Reload conversations");
        }
    }

    public StringProperty getSearchProperty() {
        return searchProperty;
    }

    public void searchConversation()
    {
        Platform.runLater(() -> {
            conversations.clear();
            for(Message conversation : conversations)
            {
                if (conversation.getReceiver().getUsername().contains(conversation.getReceiver().getUsername()))
                    conversations.add(conversation);
            }
        });
    }

    public ObservableList<User> getUsers() {
        users.setAll(chatModel.getOnlineUsers());
        return users;
    }

    public ObservableList<Message> getConversations() {
        conversations.clear();
        conversations.addAll(chatModel.loadConversationShortcuts());
        System.out.println("Conversations updated");
        return conversations;
    }

    public void loadConversation(User sender, User receiver) {
            LoggedUser.getLoggedUser().setCurrentOpenConversation(chatModel.loadConversation(sender, receiver));
            if(sender.getUser_id() == LoggedUser.getLoggedUser().getUser().getUser_id())
                receiverProperty.setValue(receiver.getUsername());
            else
                receiverProperty.setValue(sender.getUsername());
    }

    public StringProperty getMessageProperty() {
        return messageProperty;
    }

    public void makeConversationRead()
    {
        for(Message message : LoggedUser.getLoggedUser().getCurrentOpenConversation()) {
            if(message.getSender().getUser_id() != LoggedUser.getLoggedUser().getUser().getUser_id())
                if(!message.isRead())
                    chatModel.makeMessageRead(message);
        }
    }

    public StringProperty getReceiverProperty() {
        return receiverProperty;
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

    public void ifConversationExists(User anotherUser) {
        currentReceiver = null;
        LoggedUser.getLoggedUser().setCurrentOpenConversation(new ArrayList<>());
        for(Message message : conversations)
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
            System.out.println("xxxxxxxxxxxxxxxx" + anotherUser);
            receiverProperty.setValue(anotherUser.getUsername());
            System.out.println("Current receiver is set");
        }

    }

    public void clearList() {
        users.clear();
        conversations.clear();
    }

    public void resetView()
    {
        receiverProperty.setValue("");
    }


}

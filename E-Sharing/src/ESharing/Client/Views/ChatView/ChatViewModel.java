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
    private List<Message> currentConversation;
    private PropertyChangeSupport support;

    private ChatModel chatModel;
    private VerificationModel verificationModel;

    public ChatViewModel()
    {
        chatModel = ModelFactory.getModelFactory().getChatModel();
        verificationModel = ModelFactory.getModelFactory().getVerificationModel();
        support = new PropertyChangeSupport(this);

        currentConversation = new ArrayList<>();

        conversations = FXCollections.observableArrayList();
        users = FXCollections.observableArrayList();

        searchProperty = new SimpleStringProperty();
        messageProperty = new SimpleStringProperty();
        receiverProperty = new SimpleStringProperty();

        chatModel.addPropertyChangeListener(Events.NEW_MESSAGE_RECEIVED.toString(), this::newMessageReceived);
    }

    private void newMessageReceived(PropertyChangeEvent propertyChangeEvent) {
        Message message = (Message) propertyChangeEvent.getNewValue();
        if (!currentConversation.isEmpty() && (LoggedUser.getLoggedUser().getUser().getUser_id() == currentConversation.get(0).getSender().getUser_id()
        || LoggedUser.getLoggedUser().getUser().getUser_id() == currentConversation.get(0).getReceiver().getUser_id())) {
            currentConversation.add(message);
            makeConversationRead();
        }
        support.firePropertyChange(propertyChangeEvent);
    }

    public void sendMessage() {
        if(verificationModel.verifyMessage(messageProperty.get())) {
            User receiver;
            if(currentConversation.get(0).getSender().getUser_id() == LoggedUser.getLoggedUser().getUser().getUser_id())
                receiver = currentConversation.get(0).getReceiver();
            else
                receiver = currentConversation.get(0).getSender();
            chatModel.sendPrivateMessage(new Message(LoggedUser.getLoggedUser().getUser(), receiver, messageProperty.get()));
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
        return users;
    }

    public ObservableList<Message> getConversations() {
        conversations.addAll(chatModel.loadConversationShortcuts());
        return conversations;

    }

    public void loadConversation(User sender, User receiver)
    {
        if(currentConversation.isEmpty()) {
            currentConversation = chatModel.loadConversation(sender, receiver);
            System.out.println(currentConversation.size());
        }
        else if(currentConversation.get(0).getReceiver().getUser_id() == LoggedUser.getLoggedUser().getUser().getUser_id())
        {
            if(currentConversation.get(0).getSender().getUser_id() != sender.getUser_id())
            {
                currentConversation = chatModel.loadConversation(sender,receiver);
            }
        }
        else if(currentConversation.get(0).getSender().getUser_id() == LoggedUser.getLoggedUser().getUser().getUser_id())
        {
            if(currentConversation.get(0).getReceiver().getUser_id() != receiver.getUser_id())
            {
                currentConversation = chatModel.loadConversation(sender, receiver);
            }
        }
    }

    public StringProperty getMessageProperty() {
        return messageProperty;
    }

    public Conversation createNewConversation(User receiver) {
//        boolean exists = false;
//        for(Conversation conversation : conversations){
//            if(conversation.getReceiver().equals(receiver) && conversation.getSender().equals(LoggedUser.getLoggedUser().getUser())) {
//                currentConversation = conversation;
//                exists = true;
//            }
//        }
//        if(!exists)
//            currentConversation = chatModel.createNewConversation(LoggedUser.getLoggedUser().getUser(), receiver);
//        return currentConversation;
        return null;
    }

    public void makeConversationRead()
    {
        for(Message message : currentConversation) {
            if(message.getSender().getUser_id() != LoggedUser.getLoggedUser().getUser().getUser_id())
                chatModel.makeMessageRead(message);
        }
    }


    public List<Message> getCurrentConversation() {
        return currentConversation;
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
}

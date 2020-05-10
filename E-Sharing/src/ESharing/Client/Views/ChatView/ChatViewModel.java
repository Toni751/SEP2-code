package ESharing.Client.Views.ChatView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.ChatModel.ChatModel;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Model.VerificationModel.VerificationModel;
import ESharing.Shared.TransferedObject.Conversation;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ChatViewModel {
    private StringProperty searchProperty;
    private StringProperty messageProperty;
    private StringProperty receiverProperty;
    private ObservableList<Conversation> conversations;
    private ObservableList<User> users;
    private Conversation currentConversation;

    private ChatModel chatModel;
    private VerificationModel verificationModel;

    public ChatViewModel()
    {
        chatModel = ModelFactory.getModelFactory().getChatModel();
        verificationModel = ModelFactory.getModelFactory().getVerificationModel();

        conversations = FXCollections.observableArrayList();
        users = FXCollections.observableArrayList();

        searchProperty = new SimpleStringProperty();
        messageProperty = new SimpleStringProperty();
        receiverProperty = new SimpleStringProperty();
    }

    public void sentMessage() {
        if(verificationModel.verifyMessage(messageProperty.get()))
            chatModel.sendPrivateMessage(new Message(LoggedUser.getLoggedUser().getUser(), currentConversation.getReceiver(), messageProperty.get()));
    }

    public StringProperty getSearchProperty() {
        return searchProperty;
    }

    public void searchConversation()
    {
        Platform.runLater(() -> {
            conversations.clear();
            for(Conversation conversation : conversations)
            {
                if (conversation.getReceiver().getUsername().contains(conversation.getReceiver().getUsername()))
                    conversations.add(conversation);
            }
        });
    }

    public ObservableList<User> getUsers() {
        return users;
    }

    public ObservableList<Conversation> getConversations() {
        conversations.addAll(LoggedUser.getLoggedUser().getUser().getConversations());
        return conversations;
    }

    public StringProperty getMessageProperty() {
        return messageProperty;
    }

    public void setCurrentConversation(Conversation conversation) {
        this.currentConversation = conversation;
        chatModel.makeConversationRead(currentConversation);
        receiverProperty.setValue(currentConversation.getReceiver().getUsername());
    }

    public Conversation createNewConversation(User receiver) {
        boolean exists = false;
        for(Conversation conversation : conversations){
            if(conversation.getReceiver().equals(receiver) && conversation.getSender().equals(LoggedUser.getLoggedUser().getUser())) {
                currentConversation = conversation;
                exists = true;
            }
        }
        if(!exists)
            currentConversation = chatModel.createNewConversation(LoggedUser.getLoggedUser().getUser(), receiver);
        return currentConversation;
    }

    public StringProperty getReceiverProperty() {
        return receiverProperty;
    }
}

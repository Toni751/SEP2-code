package ESharing.Client.Views.ChatView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorLists;
import ESharing.Client.Model.ChatModel.ChatModel;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Model.VerificationModel.VerificationModel;
import ESharing.Shared.TransferedObject.Conversation;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.util.ArrayList;

public class ChatViewModel {
    private StringProperty searchProperty;
    private StringProperty messageProperty;
    private ArrayList<Conversation> conversations;
    private ArrayList<User> users;
    private Conversation currentConversation;

    private ChatModel chatModel;
    private VerificationModel verificationModel;

    public ChatViewModel()
    {
        chatModel = ModelFactory.getModelFactory().getChatModel();
        verificationModel = ModelFactory.getModelFactory().getVerificationModel();

        searchProperty = new SimpleStringProperty();
        messageProperty = new SimpleStringProperty();
        conversations = new ArrayList<>();
        users = new ArrayList<>();
    }

    public ObservableList<HBox> loadConversations() {
        ObservableList<HBox> conversationComponents = FXCollections.observableArrayList();
        conversations = chatModel.getAllConversations(LoggedUser.getLoggedUser().getUser());

        for(Conversation conversation : conversations)
            conversationComponents.add(createConversationComponent(conversation));


        return conversationComponents;
    }

    public ObservableList<HBox> loadOnlineUsers()
    {
        ObservableList<HBox> onlineUserComponents = FXCollections.observableArrayList();
        users = chatModel.getOnlineUsers();

        for(User user : users)
            onlineUserComponents.add(creatOnlineUserComponent(user));

        return onlineUserComponents;
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
            for(User user : AdministratorLists.getInstance().getUserList())
            {
                if(user.getUsername().contains(searchProperty.get()))
                    users.add(user);
            }
        });
    }

    private HBox createConversationComponent(Conversation conversation)
    {
        HBox conversationHBox = new HBox();

        conversationHBox.setPrefWidth(170);
        conversationHBox.setPrefHeight(50);

        ImageView userImage = new ImageView(new Image("ESharing/Images/icons/avatar-icon.png"));
        userImage.setFitWidth(40);
        userImage.setFitHeight(40);

        VBox infoBox = new VBox();
        infoBox.setPrefHeight(50);
        infoBox.setPrefWidth(110);

        Label username = new Label(conversation.getSender().getUsername());
        Label lastMessage = new Label(conversation.getMessages().get(conversation.getMessages().size() - 1).getContent());

        infoBox.getChildren().addAll(username, lastMessage);
        conversationHBox.getChildren().addAll(userImage, infoBox);

        return conversationHBox;
    }

    private HBox creatOnlineUserComponent(User user)
    {
        HBox onlineUser = new HBox(10);
        Circle circle = new Circle();
        Image userAvatar = new Image("ESharing/Images/icons/avatar-icon.png");
        Label username = new Label(user.getUsername());
        username.setStyle("-fx-text-fill: #fff; -fx-font-size: 14px;");
        circle.setRadius(20);
        circle.setFill(new ImagePattern(userAvatar));
        onlineUser.setAlignment(Pos.CENTER_LEFT);

        onlineUser.getChildren().setAll(circle, username);
        return  onlineUser;
    }

    public StringProperty getMessageProperty() {
        return messageProperty;
    }
}

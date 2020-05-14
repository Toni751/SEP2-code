package ESharing.Client.Views.ChatView;

import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorLists;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.TransferedObject.Conversation;
import ESharing.Shared.TransferedObject.Events;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.beans.PropertyChangeEvent;
import java.nio.file.LinkOption;
import java.util.ArrayList;

public class ChatViewController extends ViewController {
    @FXML private TextField searchConversationBox;
    @FXML private TextField messageTextField;
    @FXML private VBox conversationsPane;
    @FXML private VBox onlineUsersPane;
    @FXML private VBox messagesPane;
    @FXML private Label receiverUsernameLabel;

    private ChatViewModel viewModel;

    public void init()
    {

        viewModel = ViewModelFactory.getViewModelFactory().getChatViewModel();
        searchConversationBox.textProperty().bindBidirectional(viewModel.getSearchProperty());
        messageTextField.textProperty().bindBidirectional(viewModel.getMessageProperty());
        receiverUsernameLabel.textProperty().bind(viewModel.getReceiverProperty());

        setComponentsStyling();
        loadAllComponents();
        messagesPane.getChildren().clear();
        viewModel.resetView();

        if(LoggedUser.getLoggedUser().getUser().isAdministrator())
            viewModel.ifConversationExists(AdministratorLists.getInstance().getSelectedUser());


        viewModel.addPropertyChangeListener(Events.NEW_MESSAGE_RECEIVED.toString(), this::newMessageReceived);
        viewModel.addPropertyChangeListener(Events.USER_ONLINE.toString(), this::newOnlineUser);
        viewModel.addPropertyChangeListener(Events.USER_OFFLINE.toString(), this::userOffline);
        viewModel.addPropertyChangeListener(Events.UPDATE_CONVERSATION_LIST.toString(), this::updateConversationList);
    }

    private void updateConversationList(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(() ->{
            loadAllComponents();
        });
    }

    private void userOffline(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(() ->{
            User userToRemove = (User) propertyChangeEvent.getNewValue();
            onlineUsersPane.getChildren().remove(creatOnlineUserComponent(userToRemove));
            System.out.println("User removed from the view");
        });

    }

    private void newOnlineUser(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(() ->{
            User newOnlineUser = (User) propertyChangeEvent.getNewValue();
            onlineUsersPane.getChildren().add(creatOnlineUserComponent(newOnlineUser));
            System.out.println("User added to the view");
        });
    }



    private void newMessageReceived(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(() ->{
            Message newMessage = (Message) propertyChangeEvent.getNewValue();
            System.out.println("New message received");

            if(newMessage.getReceiver().getUser_id() == LoggedUser.getLoggedUser().getUser().getUser_id())
                messagesPane.getChildren().add(createMessageComponent(newMessage, Pos.CENTER_LEFT, "#fff"));
            else
                messagesPane.getChildren().add(createMessageComponent(newMessage, Pos.CENTER_RIGHT, "#54d38a"));
        });
    }


    public void searchConversation() {
        viewModel.searchConversation();
    }

    public void sendMessageByEnter(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            viewModel.sendMessage();
            messageTextField.clear();
        }
    }

    public void sendMessageByButton() {
        viewModel.sendMessage();
        messageTextField.clear();
    }


    private void loadAllComponents()
    {
        conversationsPane.getChildren().clear();
        onlineUsersPane.getChildren().clear();
        viewModel.clearList();
        System.out.println("reafreash");
        Platform.runLater(() -> {
            for(Message conversation: viewModel.getConversations())
                conversationsPane.getChildren().add(createConversationComponent(conversation));
            for(User user :viewModel.getUsers()) {
                if (user.getUser_id() != LoggedUser.getLoggedUser().getUser().getUser_id())
                    onlineUsersPane.getChildren().add(creatOnlineUserComponent(user));
            }
        });
    }

    private HBox creatOnlineUserComponent(User user)
    {
        HBox onlineUser = new HBox(10);
        Circle circle = new Circle();
        Image userAvatar = new Image("ESharing/Addition/Images/icons/avatar-icon.png");
        Label username = new Label(user.getUsername());
        username.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
        circle.setRadius(20);
        circle.setFill(new ImagePattern(userAvatar));
        onlineUser.setAlignment(Pos.CENTER_LEFT);

        onlineUser.getChildren().setAll(circle, username);

        //
        // On click event loads conversation or create new one
        //
        onlineUser.addEventHandler(MouseEvent.MOUSE_CLICKED , mouseEvent -> {
            viewModel.ifConversationExists(user);
            messagesPane.getChildren().clear();
            for(Message message : LoggedUser.getLoggedUser().getCurrentOpenConversation()){
                if(message.getSender().getUser_id() == LoggedUser.getLoggedUser().getUser().getUser_id()) {
                    messagesPane.getChildren().add(createMessageComponent(message, Pos.CENTER_RIGHT, "#54d38a"));
                    System.out.println("my message");
                }
                else {
                    messagesPane.getChildren().add(createMessageComponent(message, Pos.CENTER_LEFT, "#fff"));
                    System.out.println("not my message");
                }
            }
//            Conversation conversation = viewModel.createNewConversation(user);
//            if(!conversation.getMessages().isEmpty()) {
//                for (Message message : conversation.getMessages()) {
//                    if (message.getSender().getUser_id() == LoggedUser.getLoggedUser().getUser().getUser_id())
//                        messagesPane.getChildren().add(createMessageComponent(message, Pos.CENTER_RIGHT, "#54d38a"));
//                    else
//                        messagesPane.getChildren().add(createMessageComponent(message, Pos.CENTER_LEFT, "#fff"));
//                }
//            }
        });
        return  onlineUser;
    }

    private HBox createConversationComponent(Message conversation)
    {
        HBox conversationHBox = new HBox();

        conversationHBox.setPrefWidth(170);
        conversationHBox.setPrefHeight(50);

        ImageView userImage = new ImageView(new Image("ESharing/Addition/Images/icons/avatar-icon.png"));
        userImage.setFitWidth(40);
        userImage.setFitHeight(40);

        VBox infoBox = new VBox();
        infoBox.setPrefHeight(50);
        infoBox.setPrefWidth(110);
        Label username = null;
        if(conversation.getSender().getUser_id() == LoggedUser.getLoggedUser().getUser().getUser_id())
            username = new Label(conversation.getReceiver().getUsername());
        else
            username = new Label(conversation.getSender().getUsername());
        Label lastMessage = new Label(conversation.getContent());

        infoBox.getChildren().addAll(username, lastMessage);
        conversationHBox.getChildren().addAll(userImage, infoBox);
        conversationHBox.setCursor(Cursor.HAND);

        //
        //On clicked event loads the current conversation
        //
        conversationHBox.addEventHandler(MouseEvent.MOUSE_CLICKED , mouseEvent -> {
            LoggedUser.getLoggedUser().setCurrentOpenConversation(new ArrayList<>());
            if(LoggedUser.getLoggedUser().getUser().getUser_id() == conversation.getSender().getUser_id())
                viewModel.loadConversation(LoggedUser.getLoggedUser().getUser(), conversation.getReceiver());
            else
                viewModel.loadConversation(conversation.getSender(), LoggedUser.getLoggedUser().getUser());
            System.out.println("wtf");
            viewModel.makeConversationRead();
            messagesPane.getChildren().clear();
            for(Message message : LoggedUser.getLoggedUser().getCurrentOpenConversation()){
                if(message.getSender().getUser_id() == LoggedUser.getLoggedUser().getUser().getUser_id()) {
                    messagesPane.getChildren().add(createMessageComponent(message, Pos.CENTER_RIGHT, "#54d38a"));
                    System.out.println("my message");
                }
                else {
                    messagesPane.getChildren().add(createMessageComponent(message, Pos.CENTER_LEFT, "#fff"));
                    System.out.println("not my message");
                }
            }
        });
        return conversationHBox;
    }

    private HBox createMessageComponent(Message message, Pos pos, String color)
    {
        HBox messageComponent = new HBox(10);
        Circle avatarCircle = new Circle();
        Image userAvatar = new Image("ESharing/Addition/Images/icons/avatar-icon.png");

        avatarCircle.setRadius(20);
        avatarCircle.setFill(new ImagePattern(userAvatar));
        TextArea messageArea = new TextArea(message.getContent());
        messageArea.getStyleClass().add("message-component");
        if(message.getContent().length() > 22)
        {
            int lines = message.getContent().length()/22;
            int height = 27 + (lines * 27);
            messageArea.setPrefHeight(height);
            messageArea.setMinHeight(height);
        }
        else
        {
            messageArea.setPrefHeight(30);
            messageArea.setMinHeight(30);
        }
        messageArea.setEditable(false);
        messageArea.setWrapText(true);
        messageComponent.setAlignment(pos);
        messageArea.setStyle("-fx-background-color: " + color +";");
        if(pos == Pos.CENTER_LEFT)
            messageComponent.getChildren().addAll(avatarCircle, messageArea);
        else
            messageComponent.getChildren().addAll(messageArea, avatarCircle);

        return messageComponent;
    }

    private void setComponentsStyling()
    {
        onlineUsersPane.setSpacing(5);
        messagesPane.setSpacing(15);
        conversationsPane.getChildren().clear();
        onlineUsersPane.getChildren().clear();
    }
}

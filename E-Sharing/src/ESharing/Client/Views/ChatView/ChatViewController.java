package ESharing.Client.Views.ChatView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorLists;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.Util.Events;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
import jdk.jfr.Event;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class ChatViewController extends ViewController {
    @FXML private ImageView administratorBackImage;
    @FXML private ScrollPane scrollPane;
    @FXML private TextField searchConversationBox;
    @FXML private TextField messageTextField;
    @FXML private VBox conversationsPane;
    @FXML private VBox onlineUsersPane;
    @FXML private VBox messagesPane;
    @FXML private Label receiverUsernameLabel;

    private ViewHandler viewHandler;
    private ChatViewModel viewModel;

    public void init()
    {

        viewModel = ViewModelFactory.getViewModelFactory().getChatViewModel();
        viewHandler = ViewHandler.getViewHandler();
        searchConversationBox.textProperty().bindBidirectional(viewModel.getSearchProperty());
        messageTextField.textProperty().bindBidirectional(viewModel.getMessageProperty());
        receiverUsernameLabel.textProperty().bind(viewModel.getReceiverProperty());

        setComponentsStyling();
        loadAllComponents();
        viewModel.resetView();
        checkIfUserIsAdmin();

        if(!viewModel.ifViewModelHasListeners()) {
            viewModel.addPropertyChangeListener(Events.NEW_MESSAGE_RECEIVED.toString(), this::newMessageReceived);
            viewModel.addPropertyChangeListener(Events.USER_ONLINE.toString(), this::newOnlineUser);
            viewModel.addPropertyChangeListener(Events.USER_OFFLINE.toString(), this::userOffline);
            viewModel.addPropertyChangeListener(Events.UPDATE_CONVERSATION_LIST.toString(), this::updateConversationList);
        }
    }

    private void updateConversationList(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(() ->{
            conversationsPane.getChildren().clear();
            for(Message conversation: viewModel.getConversations())
                conversationsPane.getChildren().add(createConversationComponent(conversation));
        });
    }

    private void userOffline(PropertyChangeEvent propertyChangeEvent) {
        System.out.println("OFFLINE USER CHAT CONTROLLER");
        Platform.runLater(() ->{
            onlineUsersPane.getChildren().clear();
            for(User user : viewModel.getUsers())
                if(user.getUser_id() != LoggedUser.getLoggedUser().getUser().getUser_id())
                    onlineUsersPane.getChildren().add(creatOnlineUserComponent(user));
        });

    }

    private void newOnlineUser(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(() ->{
            User newOnlineUser = (User) propertyChangeEvent.getNewValue();
            onlineUsersPane.getChildren().add(creatOnlineUserComponent(newOnlineUser));
        });
    }

    private void newMessageReceived(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(() ->{
            System.out.println(LoggedUser.getLoggedUser().getUser());
            Message newMessage = (Message) propertyChangeEvent.getNewValue();
            if(newMessage.getSender().getUser_id() == LoggedUser.getLoggedUser().getUser().getUser_id()) {
                messagesPane.getChildren().add(createMessageComponent(newMessage, Pos.CENTER_RIGHT, "#54d38a", LoggedUser.getLoggedUser().getUser().getAvatar()));
            }
            else {
                messagesPane.getChildren().add(createMessageComponent(newMessage, Pos.CENTER_LEFT, "#fff", newMessage.getSender().getAvatar()));
            }
            scrollPane.vvalueProperty().bind(messagesPane.heightProperty());
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
        messagesPane.getChildren().clear();
        viewModel.clearList();
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
        Label username = new Label(user.getUsername());
        username.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
        circle.setRadius(20);
        circle.setFill(new ImagePattern(user.getAvatar()));
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
                    messagesPane.getChildren().add(createMessageComponent(message, Pos.CENTER_RIGHT, "#54d38a", LoggedUser.getLoggedUser().getUser().getAvatar()));
                }
                else {
                    messagesPane.getChildren().add(createMessageComponent(message, Pos.CENTER_LEFT, "#fff", message.getSender().getAvatar()));
                }
            }
        });
        return  onlineUser;
    }

    private HBox createConversationComponent(Message conversation)
    {
        HBox conversationHBox = new HBox();

        conversationHBox.setPrefWidth(170);
        conversationHBox.setPrefHeight(50);

        ImageView userImage = new ImageView();
        userImage.setFitWidth(40);
        userImage.setFitHeight(40);

        VBox infoBox = new VBox();
        infoBox.setPrefHeight(50);
        infoBox.setPrefWidth(110);
        Label username = null;
        if(conversation.getSender().getUser_id() == LoggedUser.getLoggedUser().getUser().getUser_id()) {
            username = new Label(conversation.getReceiver().getUsername());
            userImage.setImage(conversation.getReceiver().getAvatar());
        }
        else{
            username = new Label(conversation.getSender().getUsername());
            userImage.setImage(conversation.getSender().getAvatar());
        }
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
            viewModel.makeConversationRead();
            messagesPane.getChildren().clear();
            for(Message message : LoggedUser.getLoggedUser().getCurrentOpenConversation()){
                if(message.getSender().getUser_id() == LoggedUser.getLoggedUser().getUser().getUser_id()) {
                    messagesPane.getChildren().add(createMessageComponent(message, Pos.CENTER_RIGHT, "#54d38a", LoggedUser.getLoggedUser().getUser().getAvatar()));
                }
                else {
                    messagesPane.getChildren().add(createMessageComponent(message, Pos.CENTER_LEFT, "#fff", message.getSender().getAvatar()));
                }
            }
            scrollPane.vvalueProperty().bind(messagesPane.heightProperty());
        });
        return conversationHBox;
    }

    private HBox createMessageComponent(Message message, Pos pos, String color, Image avatar)
    {
        HBox messageComponent = new HBox(10);
        Circle avatarCircle = new Circle();

        avatarCircle.setRadius(20);
        avatarCircle.setFill(new ImagePattern(avatar));
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
        administratorBackImage.setVisible(false);
        administratorBackImage.setDisable(true);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
    }

    private void adminStyling()
    {
        administratorBackImage.setVisible(true);
        administratorBackImage.setDisable(false);
    }

    public void onAdministratorBack() {
        if(LoggedUser.getLoggedUser().getUser().isAdministrator()){

            viewHandler.openAdminMainView();
        }
    }

    private void checkIfUserIsAdmin(){
        if(LoggedUser.getLoggedUser().getUser().isAdministrator()) {
            if(AdministratorLists.getInstance().getSelectedUser() != null) {
                viewModel.ifConversationExists(AdministratorLists.getInstance().getSelectedUser());
                messagesPane.getChildren().clear();
                for(Message message : LoggedUser.getLoggedUser().getCurrentOpenConversation()){
                    if(message.getSender().getUser_id() == LoggedUser.getLoggedUser().getUser().getUser_id()) {
                        messagesPane.getChildren().add(createMessageComponent(message, Pos.CENTER_RIGHT, "#54d38a", LoggedUser.getLoggedUser().getUser().getAvatar()));
                    }
                    else {
                        messagesPane.getChildren().add(createMessageComponent(message, Pos.CENTER_LEFT, "#fff", message.getSender().getAvatar()));
                    }
                }
                    scrollPane.vvalueProperty().bind(messagesPane.heightProperty());
                }
            adminStyling();
        }
    }
}

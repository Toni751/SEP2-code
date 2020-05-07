package ESharing.Client.Views.ChatView;

import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Views.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class ChatViewController extends ViewController {
    @FXML private TextField searchConversationBox;
    @FXML private TextField messageTextField;
    @FXML private VBox conversationsPane;
    @FXML private VBox onlineUsersPane;
    @FXML private VBox messagesPane;
    @FXML private Label receiverUsernameLabel;

    private ChatViewModel viewModel;

    public  void init()
    {
        viewModel = ViewModelFactory.getViewModelFactory().getChatViewModel();
        searchConversationBox.textProperty().bindBidirectional(viewModel.getSearchProperty());
        messageTextField.textProperty().bindBidirectional(viewModel.getMessageProperty());
        conversationsPane.getChildren().setAll(viewModel.loadConversations());
        onlineUsersPane.getChildren().setAll(viewModel.loadOnlineUsers());
        setComponentsStyling();

        viewModel.loadConversations();
        viewModel.loadOnlineUsers();
    }


    public void searchConversation() {
        viewModel.searchConversation();
    }

    public void sendMessageByEnter(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            viewModel.sentMessage();
            messageTextField.clear();
        }
    }

    public void sendMessageByButton() {
        viewModel.sentMessage();
        messageTextField.clear();
    }


    private void setComponentsStyling()
    {
        onlineUsersPane.setSpacing(5);
        messagesPane.setSpacing(15);
    }
}

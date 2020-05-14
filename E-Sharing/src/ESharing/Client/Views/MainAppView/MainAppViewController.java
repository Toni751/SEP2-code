package ESharing.Client.Views.MainAppView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.ChatModel.ChatModel;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.TransferedObject.Conversation;
import ESharing.Shared.TransferedObject.Events;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.Util.GeneralFunctions;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import jdk.jfr.Event;

import java.beans.PropertyChangeEvent;

/**
 * The controller class used to manage all functions and components from the fxml file
 * @version 1.0
 * @author Group1
 */
public class MainAppViewController extends ViewController {

    public Label messageNotification;
    @FXML private Rectangle messageRectangle;
    @FXML private Rectangle settingRectangle;
    @FXML private Pane contentPane;
    private ViewHandler viewHandler;
    private MainAppViewModel mainAppViewModel;
    private ChatModel chatModel;
    private LoggedUser loggerUser;

    /**
     * Initializes controller with all components
     */
    public void init()
    {
        this.viewHandler = ViewHandler.getViewHandler();
        this.mainAppViewModel = ViewModelFactory.getViewModelFactory().getMainAppViewModel();
        this.chatModel = ModelFactory.getModelFactory().getChatModel();
        this.loggerUser = LoggedUser.getLoggedUser();
        hideNavigateRectangles();
        setMessageNotification();

        mainAppViewModel.addPropertyChangeListener(Events.USER_LOGOUT.toString(), this::onLogoutEvent);
        mainAppViewModel.addPropertyChangeListener(Events.NEW_MESSAGE_RECEIVED.toString(), this::newMessageReceived);
        mainAppViewModel.addPropertyChangeListener(Events.MAKE_CONVERSATION_READ.toString(), this::onUpdateNotification);
    }

    /**
     * Opens a user setting view
     */
    public void onSettingButton()
    {
        viewHandler.openMainSettingView(contentPane);
        hideNavigateRectangles();
        settingRectangle.setVisible(true);
        GeneralFunctions.fadeNode("FadeIn", settingRectangle, 500);
    }

    /**
     * Logs out current user and opens a welcome view
     */
    public void onLogout() {
        mainAppViewModel.userLoggedOut();
        LoggedUser.getLoggedUser().logoutUser();
        viewHandler.openWelcomeView();
    }

    public void onChatButton()
    {
        viewHandler.openChatView(contentPane);
        hideNavigateRectangles();
        messageRectangle.setVisible(true);
        GeneralFunctions.fadeNode("FadeIn", messageRectangle, 500);
    }

    private void newMessageReceived(PropertyChangeEvent propertyChangeEvent) {
        Message message = (Message) propertyChangeEvent.getNewValue();
        if(LoggedUser.getLoggedUser().getUser().getUser_id() != message.getSender().getUser_id()) {
            Platform.runLater(() -> {
                if (!messageNotification.isVisible())
                    messageNotification.setVisible(true);
                if (!messageNotification.textProperty().get().equals("")) {
                    int currentNotification = Integer.parseInt(messageNotification.textProperty().get());
                    currentNotification++;
                    messageNotification.textProperty().set(String.valueOf(currentNotification));
                } else
                    messageNotification.textProperty().set("1");
            });
        }
    }

    private void onLogoutEvent(PropertyChangeEvent propertyChangeEvent) {
        System.out.println("Admin changes setting");
        LoggedUser.getLoggedUser().logoutUser();
        viewHandler.openWelcomeView();
    }

    private void hideNavigateRectangles()
    {
        messageRectangle.setVisible(false);
        settingRectangle.setVisible(false);
    }

    private void onUpdateNotification(PropertyChangeEvent propertyChangeEvent) {
        setMessageNotification();
        System.out.println("Notification updated");
    }

    private void setMessageNotification()
    {
        Platform.runLater(() -> {
            int unreadConversation = chatModel.getAllUnreadMessages();
            if(unreadConversation > 0) {
                messageNotification.setVisible(true);
                messageNotification.textProperty().set(String.valueOf(unreadConversation));
            }
            else
                messageNotification.setVisible(false);
        });
    }
}

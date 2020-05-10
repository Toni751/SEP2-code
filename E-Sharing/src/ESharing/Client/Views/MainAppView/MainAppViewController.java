package ESharing.Client.Views.MainAppView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.TransferedObject.Conversation;
import ESharing.Shared.TransferedObject.Events;
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
    private LoggedUser loggerUser;

    /**
     * Initializes controller with all components
     */
    public void init()
    {
        this.viewHandler = ViewHandler.getViewHandler();
        this.mainAppViewModel = ViewModelFactory.getViewModelFactory().getMainAppViewModel();
        this.loggerUser = LoggedUser.getLoggedUser();
        hideNavigateRectangles();
        setMessageNotification();

        mainAppViewModel.addPropertyChangeListener(Events.USER_LOGOUT.toString(), this::onLogoutEvent);
        mainAppViewModel.addPropertyChangeListener(Events.NEW_MESSAGE_RECEIVED.toString(), this::newMessageReceived);
        mainAppViewModel.addPropertyChangeListener(Events.MAKE_CONVERSATION_READ.toString(), this::onUpdateNotification);
    }

    private void onUpdateNotification(PropertyChangeEvent propertyChangeEvent) {
        for(Conversation conversation : LoggedUser.getLoggedUser().getUser().getConversations())
        {
            if(conversation.equals(propertyChangeEvent.getNewValue()))
                conversation.setRead(true);
        }
        setMessageNotification();
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
        Platform.runLater(() -> {
            if(!messageNotification.isVisible())
                messageNotification.setVisible(true);
            if(!messageNotification.textProperty().get().equals("")) {
                int currentNotification = Integer.parseInt(messageNotification.textProperty().get());
                currentNotification++;
                messageNotification.textProperty().set(String.valueOf(currentNotification));
            }
            else
                messageNotification.textProperty().set("1");
        });
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

    private void setMessageNotification()
    {
        Platform.runLater(() -> {
            int unreadConversation = 0;
            for(Conversation conversation : LoggedUser.getLoggedUser().getUser().getConversations()) {
                if(!conversation.isRead())
                    unreadConversation++;
            }
            if(unreadConversation > 0) {
                messageNotification.setVisible(true);
                messageNotification.textProperty().set(String.valueOf(unreadConversation));
            }
            else
                messageNotification.setVisible(false);
        });
    }
}

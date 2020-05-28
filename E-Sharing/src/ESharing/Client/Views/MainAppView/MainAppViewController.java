package ESharing.Client.Views.MainAppView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.Util.Events;
import ESharing.Shared.Util.Views;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import java.beans.PropertyChangeEvent;

/**
 * The controller class used to display the the main system view with all JavaFX components
 * @version 1.0
 * @author Group1
 */
public class MainAppViewController extends ViewController {

    @FXML private Rectangle homeRectangle;
    @FXML private Label messageNotification;
    @FXML private Rectangle addAdvertisementRectangle;
    @FXML private Rectangle messageRectangle;
    @FXML private Rectangle settingRectangle;
    @FXML private Pane contentPane;
    private ViewHandler viewHandler;
    private MainAppViewModel mainAppViewModel;
    /**
     * Initializes and opens the main system view with all components,
     * initializes a binding properties of the JavaFX components
     */
    public void init()
    {

        this.viewHandler = ViewHandler.getViewHandler();
        this.mainAppViewModel = ViewModelFactory.getViewModelFactory().getMainAppViewModel();
        selectSubViewToOpen();

        mainAppViewModel.resetRectanglesVisibleProperty();

        messageNotification.textProperty().bind(mainAppViewModel.getNotificationProperty());
        addAdvertisementRectangle.visibleProperty().bindBidirectional(mainAppViewModel.getAdRectangleVisibleProperty());
        settingRectangle.visibleProperty().bindBidirectional(mainAppViewModel.getSettingRectangleVisibleProperty());
        messageRectangle.visibleProperty().bindBidirectional(mainAppViewModel.getMessageRectangleVisibleProperty());
        homeRectangle.visibleProperty().bindBidirectional(mainAppViewModel.getHomeRectangleVisibleProperty());
        mainAppViewModel.loadNotifications();

        mainAppViewModel.addPropertyChangeListener(Events.USER_LOGOUT.toString(), this::onAdminRemoveAccount);
    }

    /**
     * Sends a request to the view model layer to setting visible property of the setting rectangle object
     * Sends a request to the view handler to open the main user setting view
     */
    public void onSettingButton()
    {
        mainAppViewModel.setSettingRectangleSelected();
        viewHandler.openMainSettingView(contentPane);
    }

    /**
     * Sends a request to the view model layer to setting visible property of the message rectangle object
     * Sends a request to the view handler to open the chat view
     */
    public void onChatButton()
    {
        mainAppViewModel.setMessageRectangleSelected();
        viewHandler.openChatView(contentPane);
    }

    /**
     * Sends a request to the view model layer to setting visible property of the add advertisement rectangle object
     * Sends a request to the view handler to open the create advertisement view
     */
    public void onGoToAddAdvertisement() {
        mainAppViewModel.setAdRectangleSelected();
        viewHandler.openAddAdvertisementView(contentPane);
    }

    /**
     * Logs out current user and sends a request to the view handler to open the welcome view
     */
    public void onLogout() {
        mainAppViewModel.userLoggedOut();
        viewHandler.openWelcomeView();
    }

    /**
     * Sends a request to the view handler to minimizing the current stage
     */
    public void onMinimizeAction() {
        viewHandler.minimizeWindow();
    }

    /**
     * Sends a request to the view model layer to logout the user and closes the application
     */
    public void onCloseButtonAction() {
        mainAppViewModel.userLoggedOut();
        System.exit(0);
    }

    /**
     * Sends a request to rest view
     */
    public void onHomeAction() {
        mainAppViewModel.setHomeRectangleSelected();
        viewHandler.resetMainView();
        viewHandler.openMainAppView();
    }

    /**
     * Opens selected view
     */
    private void selectSubViewToOpen(){
        Views requestedView = LoggedUser.getLoggedUser().getSelectedView();
        if(requestedView == Views.ADVERTISEMENT_VIEW)
            viewHandler.openAdvertisementView(contentPane);
        else if(requestedView == Views.USER_VIEW)
            viewHandler.openUserView(contentPane);
        else if(requestedView == Views.CHAT_VIEW)
            viewHandler.openChatView(contentPane);
        else
            viewHandler.openAdsOverviewView(contentPane);

        LoggedUser.getLoggedUser().setSelectedView(null);
    }

    /**
     * Sends a request to the view handler to open a welcome view when an administrator removes the account of a current logged user
     * @param propertyChangeEvent the administrator removing account event
     */
    private void onAdminRemoveAccount(PropertyChangeEvent propertyChangeEvent) {
        viewHandler.openWelcomeView();
    }
}

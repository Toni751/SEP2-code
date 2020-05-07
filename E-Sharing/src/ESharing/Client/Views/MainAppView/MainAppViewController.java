package ESharing.Client.Views.MainAppView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.Util.GeneralFunctions;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * The controller class used to manage all functions and components from the fxml file
 * @version 1.0
 * @author Group1
 */
public class MainAppViewController extends ViewController {

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

    private void hideNavigateRectangles()
    {
        messageRectangle.setVisible(false);
        settingRectangle.setVisible(false);
    }
}

package ESharing.Client.Views.UserView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.Util.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

/**
 * The controller class used to display the welcome view with all JavaFX components
 * @version 1.0
 * @author Group1
 */
public class UserViewController extends ViewController {

    @FXML private Pane warningPane;
    @FXML private Label warningLabel;
    @FXML private Circle avatarCircle;
    @FXML private Label usernameLabel;
    @FXML private Label phoneLabel;
    @FXML private Label numberLabel;
    @FXML private Label streetLabel;

    private UserViewModel userViewModel;
    private ViewHandler viewHandler;

    /**
     * Initializes and opens welcome view with all components,
     * initializes a binding properties of the JavaFX components
     */
    public void init()
    {
        viewHandler = ViewHandler.getViewHandler();
        userViewModel = ViewModelFactory.getViewModelFactory().getUserViewModel();
        userViewModel.setDefaultView();

        avatarCircle.fillProperty().bindBidirectional(userViewModel.getAvatarFillProperty());
        usernameLabel.textProperty().bind(userViewModel.getUsernameProperty());
        streetLabel.textProperty().bind(userViewModel.getStreetProperty());
        phoneLabel.textProperty().bind(userViewModel.getPhoneProperty());
        numberLabel.textProperty().bind(userViewModel.getNumberProperty());
        warningLabel.textProperty().bind(userViewModel.getWarningProperty());
        warningPane.visibleProperty().bindBidirectional(userViewModel.getWarningVisibleProperty());
        warningPane.styleProperty().bindBidirectional(userViewModel.getWarningStyleProperty());
    }

    /**
     * Opens chat view
     */
    public void onGoToChat() {
        if(LoggedUser.getLoggedUser().getUser().isAdministrator())
            viewHandler.openChatView(null);
        else {
            LoggedUser.getLoggedUser().setSelectedView(Views.CHAT_VIEW);
            viewHandler.openMainAppView();
        }
    }

    /**
     * Opens previous view
     */
    public void onGoBackAction(){
        if(LoggedUser.getLoggedUser().getSelectedAdvertisement() != null) {
            LoggedUser.getLoggedUser().setSelectedView(Views.ADVERTISEMENT_VIEW);
        }
        else {
            LoggedUser.getLoggedUser().setSelectedUser(null);
        }
        viewHandler.openMainAppView();
    }

    /**
     * Sends a request for reporting user
     */
    public void reportUser() {
        userViewModel.reportUser();
    }
}

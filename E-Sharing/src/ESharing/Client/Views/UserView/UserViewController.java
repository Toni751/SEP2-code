package ESharing.Client.Views.UserView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Views.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class UserViewController extends ViewController {

    @FXML private Pane warningPane;
    @FXML private Label warningLabel;
    @FXML private Circle avatarCircle;
    @FXML private Label usernameLabel;
    @FXML private Label phoneLabel;
    @FXML private Label numberLabel;
    @FXML private Label streetLabel;
    @FXML private AnchorPane mainPane;

    private UserViewModel userViewModel;
    private ViewHandler viewHandler;

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

    public void onGoToChat() {
        viewHandler.openMainAppView();
    }

    public void onGoBackAction(){
       viewHandler.openAdvertisementView();
    }

    public void reportUser() {
        userViewModel.reportUser();
    }
}

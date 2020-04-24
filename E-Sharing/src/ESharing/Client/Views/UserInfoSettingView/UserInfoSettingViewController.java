package ESharing.Client.Views.UserInfoSettingView;

import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.Util.GeneralFunctions;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * The controller class used to manage all functions and components from the fxml file
 * @version 1.0
 * @author Group1
 */
public class UserInfoSettingViewController extends ViewController {

    @FXML private JFXTextField usernameTextField;
    @FXML private JFXTextField phoneTextField;
    @FXML private JFXPasswordField oldPasswordField;
    @FXML private JFXPasswordField newPasswordField;
    @FXML private Pane warningPane;
    @FXML private Label warningLabel;
    private UserInfoSettingViewModel viewModel;
    private LoggedUser loggedUser;

    /**
     * Initializes controller with all components
     */
    public void init()
    {
        this.loggedUser = LoggedUser.getLoggedUser();
        this.viewModel = ViewModelFactory.getViewModelFactory().getUserInfoSettingViewModel();

        viewModel.loadUserInfo();
        warningPane.setVisible(false);
        warningLabel.setVisible(false);

        usernameTextField.textProperty().bindBidirectional(viewModel.getUsernameProperty());
        phoneTextField.textProperty().bindBidirectional(viewModel.getPhoneProperty());
        oldPasswordField.textProperty().bindBidirectional(viewModel.getOldPasswordProperty());
        newPasswordField.textProperty().bindBidirectional(viewModel.getNewPasswordProperty());
        warningLabel.textProperty().bind(viewModel.getWarningLabel());
    }

    /**
     * Gets verification result and if given values are incorrect displays a warning
     */
    public void onSaveButton() {
        warningPane.setStyle("-fx-background-color: #DB5461");
        warningLabel.setStyle("-fx-text-fill: white");
        warningPane.setVisible(true);
        warningLabel.setVisible(true);
        GeneralFunctions.fadeNode("FadeIn", warningPane, 400);
        if (viewModel.modifyUserRequest()) {
            warningPane.setStyle("-fx-background-color: #4cdbc4");
            warningLabel.setStyle("-fx-text-fill: black");
        }
    }

    /**
     * Fill all text fields with default user values, clears change password text fields
     */
    public void onDefaultButton() {

        viewModel.loadUserInfo();
        oldPasswordField.clear();
        newPasswordField.clear();
    }

    @FXML private void hideWarningPane()
    {
        if(warningPane.isVisible()) {
            GeneralFunctions.fadeNode("FadeOut", warningPane, 400);
            warningPane.setVisible(false);
            warningLabel.setVisible(false);
        }
    }
}

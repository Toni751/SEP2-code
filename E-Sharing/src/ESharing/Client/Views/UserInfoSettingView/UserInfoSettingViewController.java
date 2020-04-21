package ESharing.Client.Views.UserInfoSettingView;

import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.GeneralFunctions;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * The controller class used to manage all functions and components from the fxml file
 * @version 1.0
 * @author Group1
 */
public class UserInfoSettingViewController extends ViewController {

    @FXML private TextField usernameTextField;
    @FXML private TextField phoneTextField;
    @FXML private PasswordField oldPasswordField;
    @FXML private PasswordField newPasswordField;
    @FXML private Pane warningPane;
    @FXML private Label warningLabel;
    private UserInfoSettingViewModel viewModel;
    private User loggedUser;

    /**
     * Initializes controller with all components
     * @param loggedUser the User object which is current logged in the system
     */
    public void init(User loggedUser)
    {
        this.loggedUser = loggedUser;
        this.viewModel = ViewModelFactory.getViewModelFactory().getUserInfoSettingViewModel();

        viewModel.setLoggedUser(loggedUser);
        viewModel.loadUserInfo();

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
        if(!viewModel.verifyNewValues());
        {
            warningPane.setVisible(true);
            GeneralFunctions.fadeNode("FadeIn", warningPane, 400);
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

}

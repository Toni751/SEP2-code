package ESharing.Client.Views.UserInfoSettingView;

import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.GeneralFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class UserInfoSettingViewController extends ViewController {

    @FXML private TextField usernameTextField;
    @FXML private TextField phoneTextField;
    @FXML private PasswordField oldPasswordField;
    @FXML private PasswordField newPasswordField;
    @FXML private Pane warningPane;
    @FXML private Label warningLabel;
    private UserInfoSettingViewModel viewModel;
    private User loggedUser;

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

    public void onSaveButton(ActionEvent actionEvent) {
        if(!viewModel.verifyNewValues());
        {
            warningPane.setVisible(true);
            GeneralFunctions.fadeNode("FadeIn", warningPane, 400);
        }
    }

    public void onDefaultButton(ActionEvent actionEvent) {

        viewModel.loadUserInfo();
        oldPasswordField.clear();
        newPasswordField.clear();
    }
}

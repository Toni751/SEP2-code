package ESharing.Client.Views.UserInfoSettingView;

import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Views.ViewController;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * The controller class used to display the edit user info view with all JavaFX components
 * @version 1.0
 * @author Group1
 */
public class UserInfoSettingViewController extends ViewController {

    @FXML private Circle avatarCircle;
    @FXML private ImageView uploadImageView;
    @FXML private JFXTextField usernameTextField;
    @FXML private JFXTextField phoneTextField;
    @FXML private JFXPasswordField oldPasswordField;
    @FXML private JFXPasswordField newPasswordField;
    @FXML private JFXPasswordField confirmPassword;
    @FXML private Pane warningPane;
    @FXML private Label warningLabel;

    private UserInfoSettingViewModel viewModel;
    private File selectedAvatar;

    /**
     * Initializes and opens the edit user info view with all components,
     * initializes a binding properties of the JavaFX components
     */
    public void init()
    {
        this.viewModel = ViewModelFactory.getViewModelFactory().getUserInfoSettingViewModel();
        viewModel.loadDefaultView();

        usernameTextField.textProperty().bindBidirectional(viewModel.getUsernameProperty());
        phoneTextField.textProperty().bindBidirectional(viewModel.getPhoneProperty());
        oldPasswordField.textProperty().bindBidirectional(viewModel.getOldPasswordProperty());
        newPasswordField.textProperty().bindBidirectional(viewModel.getNewPasswordProperty());
        confirmPassword.textProperty().bindBidirectional(viewModel.getConfirmPasswordProperty());
        warningLabel.textProperty().bind(viewModel.getWarningLabel());
        warningPane.visibleProperty().bindBidirectional(viewModel.getWarningVisibleProperty());
        warningPane.styleProperty().bindBidirectional(viewModel.getWarningStyleProperty());
        avatarCircle.fillProperty().bindBidirectional(viewModel.getAvatarFillProperty());
        uploadImageView.visibleProperty().bindBidirectional(viewModel.getUploadImageVisibleProperty());
        avatarCircle.opacityProperty().bindBidirectional(viewModel.getAvatarOpacity());
    }

    /**
     * Sends a request to the view model layer for saving user with all modifications
     */
    public void onSaveButton() {
        viewModel.checkAndUpdateInformation();
    }

    /**
     * Sends a request to the view model layer for changing a password for the current user
     */
    public void onChangePassword() {
        viewModel.changePassword();
    }

    /**
     * Sends a request to the view model layer for changing the visible property of the warning pane to false
     */
    public void hideWarningPane() {
       viewModel.hideWarningPane();
    }

    /**
     * Opens a file chooser to choosing the picture which can be an avatar of the user account
     * If picture is selected sends the request to the view model layer for displaying it in the avatar circle
     */
    public void onAvatarClicked() {
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(filter);
        fileChooser.setTitle("Select your avatar");
        selectedAvatar = fileChooser.showOpenDialog(new Stage());
        if(selectedAvatar != null)
            viewModel.setSelectedAvatar(new Image(selectedAvatar.toURI().toString()));
    }

    /**
     * Sends a request to the view model layer for changing the account avatar
     */
    public void onSetAvatar() {
        if(selectedAvatar != null) {
            viewModel.changeAvatarRequest(selectedAvatar);
        }
    }

    /**
     * Sends a request to the view model layer for setting the avatar circle property when mouse enters to the component
     */
    public void onMouseOnAvatar() {
        viewModel.setAvatarPropertyMouseEntered();
    }

    /**
     * Sends a request to the view model layer for setting the avatar circle property when mouse exit from the component
     */
    public void onMouseOutAvatar() {
        viewModel.setAvatarPropertyMouseExit();
    }

    /**
     * Sends a request to the view model layer for setting the default view
     */
    public void onDefaultButton() {
        viewModel.loadDefaultView();
    }
}

package ESharing.Client.Views.UserInfoSettingView;

import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.Util.GeneralFunctions;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * The controller class used to manage all functions and components from the fxml file
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
    private LoggedUser loggedUser;
    private File selectedAvatar;

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
        uploadImageView.setVisible(false);
        avatarCircle.setFill(new ImagePattern(LoggedUser.getLoggedUser().getUser().getAvatar()));

        usernameTextField.textProperty().bindBidirectional(viewModel.getUsernameProperty());
        phoneTextField.textProperty().bindBidirectional(viewModel.getPhoneProperty());
        oldPasswordField.textProperty().bindBidirectional(viewModel.getOldPasswordProperty());
        newPasswordField.textProperty().bindBidirectional(viewModel.getNewPasswordProperty());
        confirmPassword.textProperty().bindBidirectional(viewModel.getConfirmPasswordProperty());
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
        if (viewModel.checkAndUpdateInformation()) {
            warningPane.setStyle("-fx-background-color: #4cdbc4");
            warningLabel.setStyle("-fx-text-fill: black");
        }
    }

    /**
     * Gets verification result and if given values are incorrect displays a warning
     */
    public void onChangePassword() {
        warningPane.setStyle("-fx-background-color: #DB5461");
        warningLabel.setStyle("-fx-text-fill: white");
        warningPane.setVisible(true);
        warningLabel.setVisible(true);
        GeneralFunctions.fadeNode("FadeIn", warningPane, 400);
        if (viewModel.changePassword()) {
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
        confirmPassword.clear();
    }

    /**
     * Hides pane with warning label
     */
    @FXML private void hideWarningPane()
    {
        if(warningPane.isVisible()) {
            GeneralFunctions.fadeNode("FadeOut", warningPane, 400);
            warningPane.setVisible(false);
            warningLabel.setVisible(false);
        }
    }

    public void onAvatarClicked() {
        uploadImageView.setVisible(false);
        avatarCircle.opacityProperty().setValue(1);
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(filter);
        fileChooser.setTitle("Select your avatar");
        selectedAvatar = fileChooser.showOpenDialog(new Stage());
        if(selectedAvatar != null) {
            Image image1 = new Image(selectedAvatar.toURI().toString());
            avatarCircle.setFill(new ImagePattern(image1));
        }
    }

    public void onSetAvatar() {
        if(selectedAvatar != null) {
            viewModel.changeAvatarRequest(selectedAvatar);
        }
    }

    public void onMouseOnAvatar() {
        if(!uploadImageView.isVisible()) {
            uploadImageView.setVisible(true);
            GeneralFunctions.fadeNode("fadeIn", uploadImageView, 500);
            avatarCircle.opacityProperty().setValue(0.3);
        }
    }

    public void onMouseOutAvatar() {
        GeneralFunctions.fadeNode("fadeOut", uploadImageView, 500);
        uploadImageView.setVisible(false);
        avatarCircle.opacityProperty().setValue(1);
    }
}

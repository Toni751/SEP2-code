package ESharing.Client.Views.EditAdminView;

import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Views.ViewController;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * The controller class used to display the edit administrator view with all JavaFX components
 * @version 1.0
 * @author Group1
 */
public class EditAdminViewController extends ViewController {

    @FXML private JFXTextField usernameTextfield;
    @FXML private JFXTextField phoneNumberTextfield;
    @FXML private JFXPasswordField newPasswordTextField;
    @FXML private JFXPasswordField oldPasswordTextField;
    @FXML private JFXPasswordField confirmPasswordTextField;
    @FXML private Pane warningPane;
    @FXML private Label warningLabel;
    private EditAdminViewModel viewModel;

    /**
     * Initializes and opens the edit administrator view with all components,
     * initializes a binding properties of the JavaFX components
     */
    public void init()
    {
        viewModel = ViewModelFactory.getViewModelFactory().getEditAdminViewModel();
        viewModel.loadDefaultValues();

        usernameTextfield.textProperty().bindBidirectional(viewModel.getUsernameProperty());
        phoneNumberTextfield.textProperty().bindBidirectional(viewModel.getPhoneProperty());
        newPasswordTextField.textProperty().bindBidirectional(viewModel.getNewPasswordProperty());
        oldPasswordTextField.textProperty().bindBidirectional(viewModel.getOldPasswordProperty());
        confirmPasswordTextField.textProperty().bindBidirectional(viewModel.getConfirmPassword());
        warningPane.visibleProperty().bindBidirectional(viewModel.getWarningVisibleProperty());
        warningPane.styleProperty().bindBidirectional(viewModel.getWarningStyleProperty());
        warningLabel.textProperty().bind(viewModel.getWarningProperty());
    }

    /**
     * Sends a request to the view model layer for saving administrator with all modifications
     */
    public void onSaveAction() {
       viewModel.changeAdminInfo();
    }

    /**
     * Sends a request to the view model layer for saving user with all modifications
     */
    public void onDefaultAction() {
        viewModel.loadDefaultValues();
    }

    /**
     * Sends a request to the view model layer for changing administrator password
     */
    public void onChangePassword() {
        viewModel.changePassword();
    }

    /**
     * Sends a request to the view model layer for saving user with all modifications
     */
    public void hideWarningPane() {
        viewModel.hideWarningPane();
    }
}

package ESharing.Client.Views.EditAdminView;

import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.Util.GeneralFunctions;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class EditAdminViewController extends ViewController {

    @FXML private JFXTextField usernameTextfield;
    @FXML private JFXTextField phoneNumberTextfield;
    @FXML private JFXPasswordField newPasswordTextField;
    @FXML private JFXPasswordField oldPasswordTextField;
    @FXML private JFXPasswordField confirmPasswordTextField;
    @FXML private Pane warningPane;
    @FXML private Label warningLabel;
    private EditAdminViewModel viewModel;

    public void init()
    {
        viewModel = ViewModelFactory.getViewModelFactory().getEditAdminViewModel();
        usernameTextfield.textProperty().bindBidirectional(viewModel.getUsernameProperty());
        phoneNumberTextfield.textProperty().bindBidirectional(viewModel.getPhoneProperty());
        newPasswordTextField.textProperty().bindBidirectional(viewModel.getNewPasswordProperty());
        oldPasswordTextField.textProperty().bindBidirectional(viewModel.getOldPasswordProperty());
        confirmPasswordTextField.textProperty().bindBidirectional(viewModel.getConfirmPassword());

        warningLabel.textProperty().bind(viewModel.getWarningProperty());
        viewModel.loadDefaultValues();
        warningPane.setVisible(false);
    }

    public void onSaveAction() {
        defaultWarningPane();
        if(viewModel.changeAdminInfo()) {
            warningPane.setStyle("-fx-background-color: #4cdbc4");
            warningLabel.setStyle("-fx-text-fill: black");
        }
    }

    public void onDefaultAction() {
        viewModel.loadDefaultValues();
    }

    public void onChangePassword() {
        defaultWarningPane();
        if(viewModel.changePassword()) {
            warningPane.setStyle("-fx-background-color: #4cdbc4");
            warningLabel.setStyle("-fx-text-fill: black");
        }
    }

    private void defaultWarningPane() {
        warningPane.setVisible(true);
        GeneralFunctions.fadeNode("FadeIn", warningPane, 400);
        warningPane.setStyle("-fx-background-color: #DB5461");
        warningLabel.setStyle("-fx-text-fill: white");
    }

    public void hideWarningPane() {
        if (warningPane.isVisible())
            warningPane.setVisible(false);
    }
}

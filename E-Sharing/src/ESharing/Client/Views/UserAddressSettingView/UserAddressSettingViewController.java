package ESharing.Client.Views.UserAddressSettingView;

import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.UserAccount.LoggedUser;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.Util.GeneralFunctions;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import javax.swing.*;

/**
 * The controller class used to manage all functions and components from the fxml file
 * @version 1.0
 * @author Group1
 */
public class UserAddressSettingViewController extends ViewController {

    @FXML private Pane warningPane;
    @FXML private JFXTextField streetTextField;
    @FXML private JFXTextField numberTextField;
    @FXML private JFXTextField postalCodeTextField;
    @FXML private JFXTextField cityTextField;
    @FXML private Label usernameLabel;
    @FXML private Label phoneNumberLabel;
    @FXML private Label warningLabel;

    private UserAddressSettingViewModel viewModel;
    private LoggedUser loggedUser;

    public void init()
    {
        this.loggedUser = LoggedUser.getLoggedUser();
        this.viewModel = ViewModelFactory.getViewModelFactory().getUserAddressSettingViewModel();

        warningLabel.textProperty().bind(viewModel.getWarningProperty());
        streetTextField.textProperty().bindBidirectional(viewModel.getStreetProperty());
        cityTextField.textProperty().bindBidirectional(viewModel.getCityProperty());
        postalCodeTextField.textProperty().bindBidirectional(viewModel.getPostalCodeProperty());
        numberTextField.textProperty().bindBidirectional(viewModel.getNumberProperty());
        usernameLabel.textProperty().bind(viewModel.getUsernameProperty());
        phoneNumberLabel.textProperty().bind(viewModel.getPhoneNumberProperty());

        streetTextField.setDisable(false);
        streetTextField.setEditable(true);

        warningPane.setVisible(false);
        warningLabel.setVisible(false);

        viewModel.loadDefaultValues();
    }

    public void hideWarningPane() {
        warningLabel.setVisible(false);
        warningPane.setVisible(false);
    }

    public void onSaveButton() {
        warningPane.setVisible(true);
        warningLabel.setVisible(true);
        if (viewModel.verifyNewAddressValues()) {
            warningPane.setStyle("-fx-background-color: #4cdbc4");
            warningLabel.setStyle("-fx-text-fill: black");
            try {
                Thread.sleep(2000);
                GeneralFunctions.fadeNode("FadeOut", warningPane, 400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        GeneralFunctions.fadeNode("FadeIn", warningPane, 400);
    }


    public void onDefaultButton() {
        viewModel.loadDefaultValues();
    }
}

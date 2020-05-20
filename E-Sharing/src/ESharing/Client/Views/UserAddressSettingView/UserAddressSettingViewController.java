package ESharing.Client.Views.UserAddressSettingView;

import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Views.ViewController;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

/**
 * The controller class used to display the edit user address view with all JavaFX components
 * @version 1.0
 * @author Group1
 */
public class UserAddressSettingViewController extends ViewController {

    @FXML private Pane warningPane;
    @FXML private Circle avatarCircle;
    @FXML private JFXTextField streetTextField;
    @FXML private JFXTextField numberTextField;
    @FXML private JFXTextField postalCodeTextField;
    @FXML private JFXTextField cityTextField;
    @FXML private Label usernameLabel;
    @FXML private Label phoneNumberLabel;
    @FXML private Label warningLabel;

    private UserAddressSettingViewModel viewModel;

    /**
     * Initializes and opens the edit user address view with all components,
     * initializes a binding properties of the JavaFX components
     */
    public void init()
    {
        this.viewModel = ViewModelFactory.getViewModelFactory().getUserAddressSettingViewModel();
        viewModel.loadDefaultView();

        warningLabel.textProperty().bind(viewModel.getWarningProperty());
        streetTextField.textProperty().bindBidirectional(viewModel.getStreetProperty());
        cityTextField.textProperty().bindBidirectional(viewModel.getCityProperty());
        postalCodeTextField.textProperty().bindBidirectional(viewModel.getPostalCodeProperty());
        numberTextField.textProperty().bindBidirectional(viewModel.getNumberProperty());
        usernameLabel.textProperty().bind(viewModel.getUsernameProperty());
        phoneNumberLabel.textProperty().bind(viewModel.getPhoneNumberProperty());
        avatarCircle.fillProperty().bindBidirectional(viewModel.getAvatarCircleFillProperty());
        warningPane.visibleProperty().bindBidirectional(viewModel.getWarningVisibleProperty());
        warningPane.styleProperty().bindBidirectional(viewModel.getWarningStyleProperty());
    }

    /**
     * Sends a request to the view model layer for saving user with all modifications
     */
    public void onSaveButton() {
       viewModel.modifyAddressRequest();
    }

    /**
     * Sends a request to the view model layer for reset a view with default values
     */
    public void onDefaultButton() {
        viewModel.loadDefaultView();
    }

    /**
     * Sends a request to the view model layer for setting the visible property of the warning pane to false
     */
    public void hideWarningPane()
    {
       viewModel.hideWarningPane();
    }
}

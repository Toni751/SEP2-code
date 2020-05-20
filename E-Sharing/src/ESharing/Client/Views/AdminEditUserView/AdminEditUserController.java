package ESharing.Client.Views.AdminEditUserView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorLists;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.Util.GeneralFunctions;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 * The controller class used to display the administrator edit user view with all JavaFX components
 * @version 1.0
 * @author Group1
 */
public class AdminEditUserController extends ViewController {

    @FXML private Label warningLabel;
    @FXML private JFXTextField numberTextField;
    @FXML private JFXTextField streetTextfield;
    @FXML private JFXTextField cityTextfield;
    @FXML private JFXTextField postalCodeTextfield;
    @FXML private JFXTextField usernameTextfield;
    @FXML private JFXTextField phoneNumberTextfield;
    @FXML private Pane warningPane;

    private AdminEditUserViewModel viewModel;
    private ViewHandler viewHandler;

    /**
     * Initializes and opens the administrator dashboard view with all components,
     * initializes a binding properties of the JavaFX components
     */
    public void init()
    {
        viewModel = ViewModelFactory.getViewModelFactory().getAdminEditUserViewModel();
        viewHandler = ViewHandler.getViewHandler();
        viewModel.setSelectedUser(AdministratorLists.getInstance().getSelectedUser());
        viewModel.loadDefaultView();

        numberTextField.textProperty().bindBidirectional(viewModel.getNumberProperty());
        streetTextfield.textProperty().bindBidirectional(viewModel.getStreetProperty());
        cityTextfield.textProperty().bindBidirectional(viewModel.getCityProperty());
        postalCodeTextfield.textProperty().bindBidirectional(viewModel.getPostalCodeProperty());
        usernameTextfield.textProperty().bindBidirectional(viewModel.getUsernameProperty());
        phoneNumberTextfield.textProperty().bindBidirectional(viewModel.getPhoneNumberProperty());
        warningLabel.textProperty().bind(viewModel.getWarningLabel());

        warningPane.visibleProperty().bindBidirectional(viewModel.getWarningVisibleProperty());
        warningPane.styleProperty().bind(viewModel.warningStyleProperty());
    }

    /**
     * Sends a request to the view model layer for saving all modifications
     */
    public void onSaveAction() {
        viewModel.saveChanges();
    }

    /**
     * Sends a request to the view model layer for changing a password of the selected user
     */
    public void onResetPassword() {
        viewModel.resetPassword();
    }

    /**
     * Sends a request to the view model layer for setting a default view
     */
    public void onDefaultAction() {
        viewModel.loadDefaultView();
    }

    /**
     * Sends a request to the the view handler for opening the main admin view
     */
    public void onBackToUsersView() {
        viewHandler.openAdminMainView();
    }

    /**
     * Sends a request to the view model layer for changing a visible property of the warning pane
     */
    public void hideWarningPane() {
        viewModel.hideWarningPane();
    }
}

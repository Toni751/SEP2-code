package ESharing.Client.Views.AdminEditUserView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorLists;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.Util.GeneralFunctions;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class AdminEditUserController extends ViewController {

    @FXML private Pane contentPane;
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

    public void init()
    {
        viewModel = ViewModelFactory.getViewModelFactory().getAdminEditUserViewModel();
        viewHandler = ViewHandler.getViewHandler();

        numberTextField.textProperty().bindBidirectional(viewModel.getNumberProperty());
        streetTextfield.textProperty().bindBidirectional(viewModel.getStreetProperty());
        cityTextfield.textProperty().bindBidirectional(viewModel.getCityProperty());
        postalCodeTextfield.textProperty().bindBidirectional(viewModel.getPostalCodeProperty());
        usernameTextfield.textProperty().bindBidirectional(viewModel.getUsernameProperty());
        phoneNumberTextfield.textProperty().bindBidirectional(viewModel.getPhoneNumberProperty());
        warningLabel.textProperty().bind(viewModel.getWarningLabel());

        warningPane.setVisible(false);
        viewModel.setSelectedUser(AdministratorLists.getInstance().getSelectedUser());
        viewModel.loadUserInformation();
    }

    public void onSaveAction() {
        setWarningPaneDefault();
        GeneralFunctions.fadeNode("FadeIn", warningPane, 400);
        if (viewModel.saveChanges()) {
            warningPane.setStyle("-fx-background-color: #4cdbc4");
            warningLabel.setStyle("-fx-text-fill: black");
        }
    }

    public void onDefaultAction() {
        viewModel.loadUserInformation();
    }

    public void onResetPassword() {
        setWarningPaneDefault();
        GeneralFunctions.fadeNode("FadeIn", warningPane, 400);
        if (viewModel.resetPassword()) {
            warningPane.setStyle("-fx-background-color: #4cdbc4");
            warningLabel.setStyle("-fx-text-fill: black");
        }
    }

    public void onBackToUsersView() {
        viewHandler.openAdminMainView();
    }


    private void setWarningPaneDefault() {
        warningPane.setStyle("-fx-background-color: #DB5461");
        warningLabel.setStyle("-fx-text-fill: white");
        warningPane.setVisible(true);
        warningLabel.setVisible(true);
    }
}

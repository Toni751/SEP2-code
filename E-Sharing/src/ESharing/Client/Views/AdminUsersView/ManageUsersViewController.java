package ESharing.Client.Views.AdminUsersView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorLists;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.GeneralFunctions;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.util.Optional;

/**
 * The controller class used to display the administrator users list view with all JavaFX components
 * @version 1.0
 * @author Group1
 */
public class ManageUsersViewController extends ViewController {

    @FXML private TextField searchBox;
    @FXML private Label totalUserLabel;
    @FXML private Label reportedUserLabel;
    @FXML private JFXButton removeUserButton;
    @FXML private JFXButton goToEditButton;
    @FXML private JFXButton goToMessageButton;
    @FXML private Pane warningPane;
    @FXML private Label warningLabel;
    @FXML private TableColumn<User, Integer> userIdColumn;
    @FXML private TableColumn<User, String> usernameColumn;
    @FXML private TableColumn<User, String> phoneNumberColumn;
    @FXML private TableColumn<User, String> reportsColumn;
    @FXML private TableView usersTable;
    private ManageUsersViewModel manageUsersViewModel;
    private ViewHandler viewHandler;

    /**
     * Initializes and opens the administrator users list view with all components,
     * initializes a binding properties of the JavaFX components
     */
    public void init()
    {
        viewHandler = ViewHandler.getViewHandler();
        manageUsersViewModel = ViewModelFactory.getViewModelFactory().getManageUsersViewModel();
        manageUsersViewModel.loadDefaultView();

        userIdColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("user_id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<User, String>("phoneNumber"));
        reportsColumn.setCellValueFactory(new PropertyValueFactory<User, String>("reportsNumber"));

        warningLabel.textProperty().bind(manageUsersViewModel.getWarningLabelProperty());
        totalUserLabel.textProperty().bind(manageUsersViewModel.getTotalUsersProperty());
        reportedUserLabel.textProperty().bind(manageUsersViewModel.getReportedUsersProperty());
        searchBox.textProperty().bindBidirectional(manageUsersViewModel.getSearchProperty());
        warningPane.styleProperty().bindBidirectional(manageUsersViewModel.getWarningStyleProperty());
        warningPane.visibleProperty().bindBidirectional(manageUsersViewModel.getWarningVisibleProperty());
        removeUserButton.disableProperty().bindBidirectional(manageUsersViewModel.getRemoveUserDisableProperty());
        goToEditButton.disableProperty().bindBidirectional(manageUsersViewModel.getEditUserDisableProperty());
        goToMessageButton.disableProperty().bindBidirectional(manageUsersViewModel.getMessageUserDisableProperty());

        usersTable.setItems(manageUsersViewModel.loadAllUsers());

        usersTable.setOnMouseClicked(this :: selectUser);
    }

    /**
     * Sends a request to the view model layer for removing selected user
     */
    public void onRemoveUserAction() {
        Platform.runLater(() ->{
            Alert removeConfirmation =  GeneralFunctions.ShowConfirmationAlert("Confirm removing", "Are you sure? This operation can not be restored");
            Optional<ButtonType> result = removeConfirmation.showAndWait();
            if (result.get() == ButtonType.OK) {
                manageUsersViewModel.removeSelectedUser();
            } else {
                removeConfirmation.close();
            }
        });
    }

    /**
     * Sends a request to the view model layer for searching users in the table
     */
    public void onKeyPressedInSearchBox() {
        manageUsersViewModel.searchInTable();
    }

    /**
     * Sends a request to the view handler for opening the administrator edit user view
     */
    public void goToEditButton() {
        viewHandler.openAdminEditUserView();
    }

    /**
     * Sends a request to the view handler for opening the chat view with the selected user
     */
    public void goToChat() {
        viewHandler.openChatView(null);
    }

    /**
     * The mouse click event assigned in the users table.
     * Stores user, selected from the table in the AdministratorList class
     */
    private void selectUser(MouseEvent mouseEvent) {
        int index = usersTable.getSelectionModel().getSelectedIndex();
        AdministratorLists.getInstance().setSelectedUser((User) usersTable.getItems().get(index));
        manageUsersViewModel.enableUserManagingProperty();
    }
}

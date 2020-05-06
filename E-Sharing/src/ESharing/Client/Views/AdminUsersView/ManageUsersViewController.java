package ESharing.Client.Views.AdminUsersView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorLists;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.TransferedObject.User;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javax.swing.*;


public class ManageUsersViewController extends ViewController {

    @FXML private Pane contentPane;
    @FXML private TextField searchBox;
    @FXML private Label totalUserLabel;
    @FXML private Label reportedUserLabel;
    @FXML private JFXButton removeUserButton;
    @FXML private JFXButton goToEditButton;
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
     * Initializes controller with all components
     */
    public void init()
    {

        viewHandler = ViewHandler.getViewHandler();
        manageUsersViewModel = ViewModelFactory.getViewModelFactory().getManageUsersViewModel();

        usersTable.getItems().clear();
        userIdColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("user_id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<User, String>("phoneNumber"));
        reportsColumn.setCellValueFactory(new PropertyValueFactory<User, String>("reportsNumber"));
        warningLabel.textProperty().bind(manageUsersViewModel.getWarningLabelProperty());
        totalUserLabel.textProperty().bind(manageUsersViewModel.getTotalUsersProperty());
        reportedUserLabel.textProperty().bind(manageUsersViewModel.getReportedUsersProperty());
        searchBox.textProperty().bindBidirectional(manageUsersViewModel.getSearchProperty());

        usersTable.setItems(manageUsersViewModel.loadAllUsers());
        usersTable.setOnMouseClicked(this :: selectUser);
        hideWarningPane();
        removeUserButton.setDisable(true);
        goToEditButton.setDisable(true);
    }

    public void onRemoveUserAction() {
        setWarningVisibleAndDefault();
        if(manageUsersViewModel.removeSelectedUser())
            setWarningPaneAsSuccess();
    }

    @FXML
    private void hideWarningPane() {
        warningPane.setVisible(false);
    }

    private void setWarningPaneAsSuccess()
    {
        warningPane.setVisible(true);
        warningPane.setStyle("-fx-background-color: #4cdbc4");
        warningLabel.setStyle("-fx-text-fill: black");
    }

    private void setWarningVisibleAndDefault()
    {
        warningPane.setVisible(true);
        warningPane.setStyle("-fx-background-color: #DB5461");
        warningLabel.setStyle("-fx-text-fill: white");
    }

    private void selectUser(MouseEvent mouseEvent) {
        int index = usersTable.getSelectionModel().getSelectedIndex();
        AdministratorLists.getInstance().setSelectedUser((User) usersTable.getItems().get(index));
        removeUserButton.setDisable(false);
        goToEditButton.setDisable(false);
    }

    public void onKeyPressedInSearchBox() {
        manageUsersViewModel.searchInTable();
    }

    public void goToEditButton() {
        viewHandler.openAdminEditUserView();
    }
}

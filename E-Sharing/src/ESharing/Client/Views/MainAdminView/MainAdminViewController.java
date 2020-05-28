package ESharing.Client.Views.MainAdminView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorLists;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.Util.GeneralFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.awt.*;

/**
 * The controller class used to manage all functions and components from the fxml file
 * @version 1.0
 * @author Group1
 */
public class MainAdminViewController extends ViewController {

    @FXML private Rectangle dashboardRectangle;
    @FXML private Rectangle usersRectangle;
    @FXML private Rectangle advertisementsRectangle;
    @FXML private Pane contentPane;
    @FXML private Label messageNotification;
    private ViewHandler viewHandler;
    private MainAdminViewModel mainAdminViewModel;

    /**
     * Initializes controller with all components
     */
    public void init()
    {
        viewHandler = ViewHandler.getViewHandler();
        mainAdminViewModel = ViewModelFactory.getViewModelFactory().getMainAdminViewModel();

        messageNotification.textProperty().bind(mainAdminViewModel.getNotificationProperty());
        advertisementsRectangle.visibleProperty().bindBidirectional(mainAdminViewModel.getAdRectangleProperty());
        dashboardRectangle.visibleProperty().bindBidirectional(mainAdminViewModel.getDashboardProperty());
        usersRectangle.visibleProperty().bindBidirectional(mainAdminViewModel.getUsersProperty());


        if(AdministratorLists.getInstance().getUserList().isEmpty())
            mainAdminViewModel.loadUsersListRequest();
        mainAdminViewModel.setDefaultView();
        onDashboardAction();
    }


    /**
     * Opens view for managing users which are registered in the system
     */
    public void onManageUsersAction() {
        mainAdminViewModel.setUsersRequest();
        viewHandler.openManagesUsersView(contentPane);
    }


    /**
     * Opens admin dashboard in the content pane
     */
    public void onDashboardAction() {
        mainAdminViewModel.onDashboardRequest();
        viewHandler.openAdminDashboardView(contentPane);
    }

    /**
     * Sends a request to edit
     */
    public void onEditAdminAccount() {
        viewHandler.openEditAdminView(contentPane);
    }

    /**
     * Sends a request to logout
     */
    public void onLogoutAction() {
        mainAdminViewModel.onLogoutRequest();
        viewHandler.openWelcomeView();
    }

    /**
     * Opens chat view
     */
    public void onGoToChat() {
        AdministratorLists.getInstance().setSelectedUser(null);
        viewHandler.openChatView(null);
    }

    /**
     *  Opens advertisements view
     */
    public void onGoToAdvertisements() {
        mainAdminViewModel.setAdvertisementsRequest();
        viewHandler.openAdminAdvertisementView(contentPane);
    }
}

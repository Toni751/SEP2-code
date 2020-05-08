package ESharing.Client.Views.MainAdminView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.AdministratorModel.AdministratorLists;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.Util.GeneralFunctions;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * The controller class used to manage all functions and components from the fxml file
 * @version 1.0
 * @author Group1
 */
public class MainAdminViewController extends ViewController {

    @FXML private Rectangle dashboardRectangle;
    @FXML private Rectangle usersRectangle;
    @FXML private Rectangle advertisementsRectangle;
    @FXML private Rectangle systemLogsRectangle;
    @FXML private Pane contentPane;
    private ViewHandler viewHandler;
    private MainAdminViewModel mainAdminViewModel;

    /**
     * Initializes controller with all components
     */
    public void init()
    {
        viewHandler = ViewHandler.getViewHandler();
        mainAdminViewModel = ViewModelFactory.getViewModelFactory().getMainAdminViewModel();
        if(AdministratorLists.getInstance().getUserList().isEmpty())
            mainAdminViewModel.loadUsersListRequest();
        hideRectanglesStyle();
        onDashboardAction();
    }


    /**
     * Opens view for managing users which are registered in the system
     */
    public void onManageUsersAction() {
        hideRectanglesStyle();
        usersRectangle.setVisible(true);
        GeneralFunctions.fadeNode("FadeIn", usersRectangle, 500);
        viewHandler.openManagesUsersView(contentPane);
    }


    /**
     * Opens admin dashboard in the content pane
     */
    public void onDashboardAction() {
        hideRectanglesStyle();
        dashboardRectangle.setVisible(true);
        GeneralFunctions.fadeNode("FadeIn", dashboardRectangle, 600);
        viewHandler.openAdminDashboardView(contentPane);
    }


    private void hideRectanglesStyle()
    {
        dashboardRectangle.setVisible(false);
        usersRectangle.setVisible(false);
        advertisementsRectangle.setVisible(false);
        systemLogsRectangle.setVisible(false);
    }

    public void onEditAdminAccount() {
        viewHandler.openEditAdminView(contentPane);
    }
}

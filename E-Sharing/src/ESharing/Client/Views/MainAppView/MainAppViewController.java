package ESharing.Client.Views.MainAppView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.TransferedObject.User;

/**
 * The controller class used to manage all functions and components from the fxml file
 * @version 1.0
 * @author Group1
 */
public class MainAppViewController extends ViewController {

    private ViewHandler viewHandler;
    private MainAppViewModel mainAppViewModel;
    private User loggerUser;

    /**
     * Initializes controller with all components
     * @param loggedUser the User object which is current logged in the system
     */
    public void init(User loggedUser)
    {
        this.viewHandler = ViewHandler.getViewHandler();
        this.mainAppViewModel = ViewModelFactory.getViewModelFactory().getMainAppViewModel();
        this.loggerUser = loggedUser;
    }

    /**
     * Opens a user setting view
     */
    public void onSettingButton()
    {
        viewHandler.openMainSettingView(loggerUser);
    }

}

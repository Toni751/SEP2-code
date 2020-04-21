package ESharing.Client.Views.UserAddressSettingView;

import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.TransferedObject.User;

/**
 * The controller class used to manage all functions and components from the fxml file
 * @version 1.0
 * @author Group1
 */
public class UserAddressSettingViewController extends ViewController {

    private UserAddressSettingViewModel viewModel;
    private User loggedUser;

    public void init(User loggedUser)
    {
        this.loggedUser = loggedUser;
        this.viewModel = ViewModelFactory.getViewModelFactory().getUserAddressSettingViewModel();
    }
}

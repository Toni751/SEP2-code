package ESharing.Client.Views.MainAccountSetting;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Model.UserActions.UserActionsModel;

/**
 * The class in a view model layer contains all functions which are used in the signUp view.
 * @version 1.0
 * @author Group1
 */
public class MainSettingViewModel {

    private UserActionsModel userActionsModel;
    private LoggedUser loggedUser;

    /**
     * A constructor initializes model layer for a user features and all fields
     */
    public MainSettingViewModel()
    {
        this.loggedUser = LoggedUser.getLoggedUser();
        this.userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();
    }

    /**
     * Sends the remove current logged user request to the view model
     */
    public void removeAccount() {
            userActionsModel.removeAccount();
    }

}

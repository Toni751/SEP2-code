package ESharing.Client.Views.MainAccountSetting;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.UserActions.UserActionsModel;

/**
 * The class in a view model layer contains all functions which are used in the main user setting view.
 * @version 1.0
 * @author Group1
 */
public class MainSettingViewModel {

    private UserActionsModel userActionsModel;

    /**
     * A constructor initializes model layer for a user setting features
     */
    public MainSettingViewModel()
    {
        this.userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();
    }

    /**
     * Sends a request to the model layer to removing the current logged user
     */
    public void removeAccount() {
            userActionsModel.removeAccount();
    }

}

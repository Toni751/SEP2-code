package ESharing.Client.Views.WelcomeView;

import ESharing.Client.Core.ModelFactory;
import ESharing.Client.Model.UserActions.UserActionsModel;

/**
 * The class in a view model layer contains all functions which are used in the welcome view.
 * @version 1.0
 * @author Group1
 */
public class WelcomeViewModel {

    private UserActionsModel userActionsModel;

    /**
     * A constructor initializes model layer for a user features and all fields
     */
    public WelcomeViewModel()
    {
        this.userActionsModel = ModelFactory.getModelFactory().getUserActionsModel();
    }
}

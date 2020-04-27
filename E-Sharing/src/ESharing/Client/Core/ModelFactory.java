package ESharing.Client.Core;

import ESharing.Client.Model.AppModel.AppOverviewModel;
import ESharing.Client.Model.AppModel.AppOverviewModelManager;
import ESharing.Client.Model.UserActions.UserActionsModel;
import ESharing.Client.Model.UserActions.UserActionsModelManager;

/**
 * The class responsible for managing models
 * @version 1.0
 * @author Group1
 */
public class ModelFactory {
    private ClientFactory clientFactory;
    private UserActionsModel userActionsModel;
    private AppOverviewModel appOverviewModel;

    private static ModelFactory modelFactory;

    /**
     * A constructor initializes fields and sets class responsible for initialize client
     */
    private ModelFactory()
    {
        this.clientFactory = ClientFactory.getClientFactory();
        userActionsModel = new UserActionsModelManager(clientFactory.getClient());
        appOverviewModel = new AppOverviewModelManager(clientFactory.getClient());
    }

    /**
     * Returns ModelFactory object if it exists, otherwise creates new object
     * @return the ModelFactory object
     */
    public static ModelFactory getModelFactory() {
        if(modelFactory == null)
            modelFactory = new ModelFactory();
        return modelFactory;
    }

    /**
     * Returns initialized model for all features belongs to user service
     * @return initialized model for all features belongs to user service
     */
    public UserActionsModel getUserActionsModel() {
        return userActionsModel;
    }

    /**
     * Returns initialized model for all features belongs to system overview
     * @return initialized model for all features belongs to system overview
     */
    public AppOverviewModel getAppOverviewModel() {
        return appOverviewModel;
    }
}

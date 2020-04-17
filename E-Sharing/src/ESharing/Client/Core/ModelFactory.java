package ESharing.Client.Core;

import ESharing.Client.Model.AppModel.AppOverviewModel;
import ESharing.Client.Model.AppModel.AppOverviewModelManager;
import ESharing.Client.Model.UserAccount.UserAccountModel;
import ESharing.Client.Model.UserAccount.UserAccountModelManager;
import ESharing.Client.Model.UserAccount.UserSettingModel;
import ESharing.Client.Model.UserAccount.UserSettingModelManager;

/**
 * The class responsible for managing models
 * @version 1.0
 * @author Group1
 */
public class ModelFactory {
    private ClientFactory clientFactory;
    private UserAccountModel userAccountModel;
    private UserSettingModel userSettingModel;
    private AppOverviewModel appOverviewModel;

    private static ModelFactory modelFactory;

    /**
     * One-argument constructor initializes fields and sets class responsible for initialize client
     */
    private ModelFactory()
    {
        this.clientFactory = ClientFactory.getClientFactory();
        userAccountModel = new UserAccountModelManager(clientFactory.getClient());
        userSettingModel = new UserSettingModelManager(clientFactory.getClient());
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
    public UserAccountModel getUserAccountModel() {
        return userAccountModel;
    }

    /**
     * Returns initialized model for all features belongs to user settings
     * @return initialized model for all features belongs to user settings
     */
    public UserSettingModel getUserSettingModel() {
        return userSettingModel;
    }

    /**
     * Returns initialized model for all features belongs to system overview
     * @return initialized model for all features belongs to system overview
     */
    public AppOverviewModel getAppOverviewModel() {
        return appOverviewModel;
    }
}

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

    /**
     * One-argument constructor initializes fields and sets class responsible for initialize client
     * @param clientFactory the class which initializes client
     */
    public ModelFactory(ClientFactory clientFactory)
    {
        this.clientFactory = clientFactory;
        userAccountModel = new UserAccountModelManager(clientFactory.getClient());
        userSettingModel = new UserSettingModelManager(clientFactory.getClient());
        appOverviewModel = new AppOverviewModelManager(clientFactory.getClient());
    }

    /**
     * Returns initialized model for all features belongs to user service
     * @return initialized model for all features belongs to user service
     */
    public UserAccountModel getUserAccountModel() {
        return userAccountModel;
    }

    public UserSettingModel getUserSettingModel() {
        return userSettingModel;
    }

    public AppOverviewModel getAppOverviewModel() {
        return appOverviewModel;
    }
}

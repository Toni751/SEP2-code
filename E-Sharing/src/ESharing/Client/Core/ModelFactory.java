package ESharing.Client.Core;

import ESharing.Client.Model.UserAccount.UserAccountModel;
import ESharing.Client.Model.UserAccount.UserAccountModelManager;

/**
 * The class responsible for managing models
 * @version 1.0
 * @author Group1
 */
public class ModelFactory {
    private ClientFactory clientFactory;
    private UserAccountModel userAccountModel;

    /**
     * One-argument constructor initializes fields and sets class responsible for initialize client
     * @param clientFactory the class which initializes client
     */
    public ModelFactory(ClientFactory clientFactory)
    {
        this.clientFactory = clientFactory;
        userAccountModel = new UserAccountModelManager(clientFactory.getClient());
    }

    /**
     * Returns initialized model for all features belongs to user service
     * @return initialized model for all features belongs to user service
     */
    public UserAccountModel getUserAccountModel() {
        return userAccountModel;
    }
}

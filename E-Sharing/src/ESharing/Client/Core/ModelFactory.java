package ESharing.Client.Core;

import ESharing.Client.Model.UserAccount.UserAccountModel;
import ESharing.Client.Model.UserAccount.UserAccountModelManager;

public class ModelFactory {
    private ClientFactory clientFactory;
    private UserAccountModel userAccountModel;

    public ModelFactory(ClientFactory clientFactory)
    {
        this.clientFactory = clientFactory;
        userAccountModel = new UserAccountModelManager(clientFactory.getClient());
    }

    public UserAccountModel getUserAccountModel() {
        return userAccountModel;
    }
}

package ESharing.Client.Model.UserAccount;

import ESharing.Client.Networking.Client;

public class UserAccountModelManager implements UserAccountModel{

    private Client client;

    public UserAccountModelManager(Client client)
    {
        this.client = client;
    }
}

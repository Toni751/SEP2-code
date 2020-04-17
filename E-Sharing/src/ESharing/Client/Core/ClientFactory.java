package ESharing.Client.Core;

import ESharing.Client.Networking.Client;
import ESharing.Client.Networking.ClientHandler;

public class ClientFactory {
    private Client client;
    private static ClientFactory clientFactory;

    private ClientFactory() {}

    public static ClientFactory getClientFactory()
    {
        if(clientFactory == null)
            clientFactory = new ClientFactory();
        return clientFactory;
    }


    public Client getClient() {
        if(client == null)
            client = new ClientHandler();
        return client;
    }
}

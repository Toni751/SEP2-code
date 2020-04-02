package ESharing.Client.Core;

import ESharing.Client.Networking.Client;
import ESharing.Client.Networking.ClientHandler;

public class ClientFactory {
    private Client client;

    public Client getClient() {
        if(client == null)
            client = new ClientHandler();
        return client;
    }
}

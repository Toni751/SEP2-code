package ESharing.Client.Core;

import ESharing.Client.Networking.Client;
import ESharing.Client.Networking.ClientHandler;

/**
 * The class responsible for managing clients
 * @version 1.0
 * @author Group1
 */
public class ClientFactory {
    private Client client;
    private static ClientFactory clientFactory;

    /**
     * Returns instance of this object. If object is not set, creates new object
     * @return the object of this class
     */
    public static ClientFactory getClientFactory()
    {
        if(clientFactory == null)
            clientFactory = new ClientFactory();
        return clientFactory;
    }


    /**
     * Returns a client object
     * @return the client object
     */
    public Client getClient() {
        if(client == null)
            client = new ClientHandler();
        return client;
    }
}

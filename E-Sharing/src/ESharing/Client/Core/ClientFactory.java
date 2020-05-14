package ESharing.Client.Core;

import ESharing.Client.Networking.chat.ClientChat;
import ESharing.Client.Networking.chat.ClientChatHandler;
import ESharing.Client.Networking.user.Client;
import ESharing.Client.Networking.user.ClientHandler;

/**
 * The class responsible for managing clients
 * @version 1.0
 * @author Group1
 */
public class ClientFactory {
    private Client client;
    private ClientChat chatClient;
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

    /**
     * Returns a chat client object
     * @return the chat client object
     */
    public ClientChat getChatClient() {
        if(chatClient == null)
            chatClient = new ClientChatHandler();
        return chatClient;
    }
}

package ESharing.Client.Core;

import ESharing.Client.Networking.advertisement.ClientAdvertisement;
import ESharing.Client.Networking.advertisement.ClientAdvertisementManager;
import ESharing.Client.Networking.chat.ClientChat;
import ESharing.Client.Networking.chat.ClientChatHandler;
import ESharing.Client.Networking.reservation.ReservationClient;
import ESharing.Client.Networking.reservation.ReservationClientHandler;
import ESharing.Client.Networking.user.UserClient;
import ESharing.Client.Networking.user.UserClientHandler;

/**
 * The class responsible for managing clients
 * @version 1.0
 * @author Group1
 */
public class ClientFactory {
    private UserClient client;
    private ClientChat chatClient;
    private ClientAdvertisement clientAdvertisement;
    private ReservationClient reservationClient;
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
    public UserClient getClient() {
        if(client == null)
            client = new UserClientHandler();
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

    /**
     * Returns a advertisement client object
     * @return the reservation client object
     */
    public ClientAdvertisement getClientAdvertisement() {
        if(clientAdvertisement == null)
            clientAdvertisement = new ClientAdvertisementManager();
        return clientAdvertisement;
    }

    /**
     * Returns a reservation client object
     * @return the reservation client object
     */
    public ReservationClient getReservationClient() {
        if(reservationClient == null)
            reservationClient = new ReservationClientHandler();
        return reservationClient;
    }
}

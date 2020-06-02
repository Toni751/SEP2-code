package ESharing.Server.Core;

import ESharing.Server.Model.advertisement.ServerAdvertisementModel;
import ESharing.Server.Model.advertisement.ServerAdvertisementModelManager;
import ESharing.Server.Model.chat.ServerChatModel;
import ESharing.Server.Model.chat.ServerChatModelManager;
import ESharing.Server.Model.reservation.ServerReservationModel;
import ESharing.Server.Model.reservation.ServerReservationModelManager;
import ESharing.Server.Model.user.ServerUserModel;
import ESharing.Server.Model.user.ServerUserModelManager;
import ESharing.Server.Persistance.DAOFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The server model factory which helps create the server models only once
 * as well as initializing all the stubs
 * @version 1.0
 * @author Group1
 */
public class ServerModelFactory
{
    private ServerChatModel chatModel;
    private ServerUserModel serverUserModel;
    private ServerAdvertisementModel serverAdvertisementModel;
    private ServerReservationModel serverReservationModel;
    private DAOFactory daoFactory;
    private Lock lock = new ReentrantLock();

    /**
     * One argument constructor initializing the DAO factory, which helps initialize the server models
     * @param daoFactory the DAO factory
     */
    public ServerModelFactory(DAOFactory daoFactory)
    {
        this.daoFactory = daoFactory;
    }

    /**
     * Initializes the server chat model, if it is null, synchronizing it, and then returns it
     * @return the server chat model
     */
    public ServerChatModel getChatModel()
    {
        if (chatModel == null)
        {
            synchronized (lock)
            {
                if (chatModel == null)
                    chatModel = new ServerChatModelManager(daoFactory.getMessageDAO(), daoFactory.getAdministratorDAO());
            }
        }
        return chatModel;
    }

    /**
     * Initializes the server user model, if it is null, synchronizing it, and then returns it
     * @return the server user model
     */
    public ServerUserModel getServerUserModel()
    {
        if (serverUserModel == null)
        {
            synchronized (lock)
            {
                if (serverUserModel == null)
                    serverUserModel = new ServerUserModelManager(daoFactory.getUserDAO(), daoFactory.getAdministratorDAO());
            }
        }
        return serverUserModel;
    }

    /**
     * Initializes the server advertisement model, if it is null, synchronizing it, and then returns it
     * @return the server advertisement model
     */
    public ServerAdvertisementModel getServerAdvertisementModel()
    {
        if (serverAdvertisementModel == null)
        {
            synchronized (lock)
            {
                if (serverAdvertisementModel == null)
                    serverAdvertisementModel = new ServerAdvertisementModelManager(daoFactory.getAdvertisementDAO());
            }
        }
        return serverAdvertisementModel;
    }

    /**
     * Initializes the server reservation model, if it is null, synchronizing it, and then returns it
     * @return the server reservation model
     */
    public ServerReservationModel getServerReservationModel() {
        if (serverReservationModel == null)
        {
            synchronized (lock)
            {
                if (serverReservationModel == null)
                    serverReservationModel = new ServerReservationModelManager(daoFactory.getReservationDAO());
            }
        }
        return serverReservationModel;
    }
}

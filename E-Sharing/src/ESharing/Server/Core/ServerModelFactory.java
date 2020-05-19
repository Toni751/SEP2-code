package ESharing.Server.Core;

import ESharing.Server.Model.advertisement.ServerAdvertisementModel;
import ESharing.Server.Model.advertisement.ServerAdvertisementModelManager;
import ESharing.Server.Model.chat.ServerChatModel;
import ESharing.Server.Model.chat.ServerChatModelManager;
import ESharing.Server.Model.user.ServerModel;
import ESharing.Server.Model.user.ServerModelManager;
import ESharing.Server.Persistance.DAOFactory;

public class ServerModelFactory
{
    private ServerChatModel chatModel;
    private ServerModel serverModel;
    private ServerAdvertisementModel serverAdvertisementModel;
    private DAOFactory daoFactory;

    public ServerModelFactory(DAOFactory daoFactory)
    {
        this.daoFactory = daoFactory;
    }
//
//    public static ServerModelFactory getInstance()
//    {
//        if(instance == null)
//            instance = new ServerModelFactory();
//        return instance;
//    }

    public ServerChatModel getChatModel()
    {
        if (chatModel == null)
            chatModel = new ServerChatModelManager(daoFactory.getMessageDAO(), daoFactory.getAdministratorDAO());
        return chatModel;
    }

    public ServerModel getServerModel()
    {
        if (serverModel == null)
            serverModel = new ServerModelManager(daoFactory.getUserDAO(), daoFactory.getAdministratorDAO());
        return serverModel;
    }

    public ServerAdvertisementModel getServerAdvertisementModel()
    {
        if (serverAdvertisementModel == null)
            serverAdvertisementModel = new ServerAdvertisementModelManager(daoFactory.getAdvertisementDAO());
        return serverAdvertisementModel;
    }
}

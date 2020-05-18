package ESharing.Server.Core;

import ESharing.Server.Model.advertisement.ServerAdvertisementModel;
import ESharing.Server.Model.advertisement.ServerAdvertisementModelManager;
import ESharing.Server.Model.chat.ServerChatModel;
import ESharing.Server.Model.chat.ServerChatModelManager;
import ESharing.Server.Model.user.ServerModel;
import ESharing.Server.Model.user.ServerModelManager;

public class ServerModelFactory {
    private ServerChatModel chatModel;
    private ServerModel serverModel;
    private ServerAdvertisementModel serverAdvertisementModel;

    private static ServerModelFactory instance;

    private ServerModelFactory()
    {
        chatModel = new ServerChatModelManager();
        serverModel = new ServerModelManager();
        serverAdvertisementModel = new ServerAdvertisementModelManager();
    }

    public static ServerModelFactory getInstance()
    {
        if(instance == null)
            instance = new ServerModelFactory();
        return instance;
    }

    public ServerChatModel getChatModel() {
        return chatModel;
    }

    public ServerModel getServerModel() {
        return serverModel;
    }

    public ServerAdvertisementModel getServerAdvertisementModel() {
        return serverAdvertisementModel;
    }
}

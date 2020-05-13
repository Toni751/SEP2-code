package ESharing.Server.Core;

import ESharing.Client.Model.ChatModel.ChatModel;
import ESharing.Server.Model.chat.ServerChatModel;
import ESharing.Server.Model.chat.ServerChatModelManager;
import ESharing.Server.Model.user.ServerModel;
import ESharing.Server.Model.user.ServerModelManager;

public class ServerModelFactory {
    private ServerChatModel chatModel;
    private ServerModel serverModel;

    private static ServerModelFactory instance;

    private ServerModelFactory()
    {
        chatModel = new ServerChatModelManager();
        serverModel = new ServerModelManager();
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
}

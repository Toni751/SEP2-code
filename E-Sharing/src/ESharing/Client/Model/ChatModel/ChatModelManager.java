package ESharing.Client.Model.ChatModel;

import ESharing.Client.Core.ClientFactory;
import ESharing.Client.Networking.Client;
import ESharing.Shared.TransferedObject.Conversation;
import ESharing.Shared.TransferedObject.User;

import java.util.ArrayList;

public class ChatModelManager implements ChatModel{

    private Client client;

    public ChatModelManager()
    {
        client = ClientFactory.getClientFactory().getClient();
    }


    @Override
    public ArrayList<Conversation> getAllConversations(User loggedUser) {
        return null;
    }

    @Override
    public ArrayList<User> getOnlineUsers() {
        return null;
    }
}

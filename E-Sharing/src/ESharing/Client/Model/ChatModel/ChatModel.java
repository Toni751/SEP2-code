package ESharing.Client.Model.ChatModel;

import ESharing.Shared.TransferedObject.Conversation;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;

import java.util.ArrayList;

public interface ChatModel {

    ArrayList<Conversation> getAllConversations(User loggedUser);
    ArrayList<User> getOnlineUsers();
    boolean sendPrivateMessage(Message message);
}

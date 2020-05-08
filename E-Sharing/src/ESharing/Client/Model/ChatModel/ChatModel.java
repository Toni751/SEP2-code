package ESharing.Client.Model.ChatModel;

import ESharing.Shared.TransferedObject.Conversation;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.PropertyChangeSubject;

import java.util.ArrayList;

public interface ChatModel extends PropertyChangeSubject {

    ArrayList<User> getOnlineUsers();
    boolean sendPrivateMessage(Message message);
    Conversation createNewConversation(User sender, User receiver);
    void makeConversationRead(Conversation conversation);
}

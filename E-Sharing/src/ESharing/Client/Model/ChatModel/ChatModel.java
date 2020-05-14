package ESharing.Client.Model.ChatModel;

import ESharing.Shared.TransferedObject.Conversation;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.PropertyChangeSubject;

import java.util.ArrayList;
import java.util.List;

public interface ChatModel extends PropertyChangeSubject {

    List<User> getOnlineUsers();
    void sendPrivateMessage(Message message);
    Conversation createNewConversation(User sender, User receiver);
    void makeMessageRead(Message message);
    List<Message> loadConversationShortcuts();
    List<Message> loadConversation(User sender, User receiver);
    int getAllUnreadMessages();
    void userLoggedOut();

}

package ESharing.Client.Model.ChatModel;

import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.PropertyChangeSubject;
import java.util.List;

/**
 * The interface used to connect a view model layer with a model layer for all advertisement features.
 * @version 1.0
 * @author Group1
 */
public interface ChatModel extends PropertyChangeSubject {

    /**
     * Returns the list with all online users
     * @return the list with all online users
     */
    List<User> getOnlineUsers();

    /**
     * Sends a request with a new message to the networking
     * @param message the new created message
     */
    void sendPrivateMessage(Message message);

    /**
     * Sends a request to make message read
     */
    void makeMessageRead();

    /**
     * Returns the list of all conversations in short version
     * @return the list of all conversations in short version
     */
    List<Message> loadConversationShortcuts();

    /**
     * Loads a conversation as a list of messages for the given users
     * @param sender the given conversation sender
     * @param receiver the given conversation receiver
     * @return the list of messages which are a part of the conversation
     */
    List<Message> loadConversation(User sender, User receiver);

    /**
     * Returns the number of all unread messages
     * @return the number of all unread messages
     */
    int getAllUnreadMessages();

}

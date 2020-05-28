package ESharing.Client.Networking.chat;

import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.PropertyChangeSubject;

import java.util.List;

/**
 * The interface from networking layer which is responsible for sending and receiving requests related to chat to and form the server
 * and for sending/ receiving data to the server
 * @version 1.0
 * @author Group1
 */
public interface ClientChat extends PropertyChangeSubject
{
  /**
   * Initializes connection with a server
   */
  void initializeConnection();

  /**
   * Returns a conversation for given users
   * @param sender the conversation sender
   * @param receiver the conversation receiver
   * @return the conversation for given users
   */
  List<Message> loadConversation (User sender, User receiver);

  /**
   * Returns a number of unread messages for the user
   * @param user the given user
   * @return the number of unread messages for the user
   */
  int getNoUnreadMessages (User user);

  /**
   * Reruns all last massages from all conversations for given user
   * @param user the given user
   * @return the last massages from all conversations for given user
   */
  List<Message> getLastMessageWithEveryone (User user);

  /**
   * Sends a request for adding new message
   * @param message new message
   */
  void addMessage (Message message);

  /**
   * Sends a request for making message read
   * @param message the given message
   */
  void makeMessageRead(Message message);

  /**
   * Logouts user from the system
   */
  void userLoggedOut();

  /**
   * Returns a list with all online users
   * @return the list with all online users
   */
  List<User> getOnlineUsers();

  /**
   * Registers all call backs from the server
   */
  void registerForCallBack();
}

package ESharing.Shared.Networking.chat;

import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * RMI networking interface for remote method invocation on the server regarding advertisement
 * @version 1.0
 * @author Group1
 */
public interface RMIChatServer extends Remote
{
  /**
   * Sends a request to the server to retrieve a conversation between 2 users
   * @param sender the first user of the conversation
   * @param receiver the second user of the conversation
   * @return the list with all the messages they've sent each other
   * @throws RemoteException if the remote method invocation fails
   */
  List<Message> loadConversation (User sender, User receiver) throws RemoteException;

  /**
   * Sends a request to the server to add retrieve the number of unread messages for a user
   * @param user the given user
   * @return the number of unread message the user has
   * @throws RemoteException if the remote method invocation fails
   */
  int getNoUnreadMessages (User user) throws RemoteException;

  /**
   * Sends a request to the server to add retrieve a user's last messages with everyone
   * @param user the given user
   * @return the list with all the user's last messages
   * @throws RemoteException if the remote method invocation fails
   */
  List<Message> getLastMessageWithEveryone (User user) throws RemoteException;

  /**
   * Sends a request to the server to add a new message
   * @param message the given user
   * @throws RemoteException if the remote method invocation fails
   */
  void addMessage (Message message) throws RemoteException;

  /**
   * Sends a request to the server to delete a user's messages
   * @param user the given user
   * @throws RemoteException if the remote method invocation fails
   */
  void deleteMessagesForUser (User user) throws RemoteException;

  /**
   * Sends a request to the server to register a client for callback
   * @param chatClient the client to be registered for callback methods
   * @throws RemoteException if the remote method invocation fails
   */
  void registerChatCallback (RMIChatClient chatClient) throws RemoteException;

  /**
   * Sends a request to the server to mark a message as read
   * @param message the given message
   * @throws RemoteException if the remote method invocation fails
   */
  void makeMessageRead(Message message) throws RemoteException;

  /**
   * Sends a request to the server to unregister a client as lister
   * @param client the client to be unregister for callback methods
   * @throws RemoteException if the remote method invocation fails
   */
  void unRegisterUserAsAListener(RMIChatClient client) throws RemoteException;

  /**
   * Sends a request to the server to remove a user as a listener
   * @param user the user to be removed as a listener
   * @throws RemoteException if the remote method invocation fails
   */
  void userLoggedOut(User user) throws RemoteException;

  /**
   * Sends a request to the server to retrieve all online users
   * @return the list with all online users
   * @throws RemoteException if the remote method invocation fails
   */
  List<User> getOnlineUsers() throws RemoteException;
}

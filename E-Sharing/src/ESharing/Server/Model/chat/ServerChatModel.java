package ESharing.Server.Model.chat;

import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.PropertyChangeSubject;

import java.util.List;

/**
 * The interface for the server chat model
 * @version 1.0
 * @author Group1
 */
public interface ServerChatModel extends PropertyChangeSubject
{
  /**
   * Retrieves from the DAO the entire conversation between 2 users
   * @param sender the first user of the conversation
   * @param receiver the second user of the conversation
   * @return the list of all messages they've sent each other previously
   */
  List<Message> loadConversation (User sender, User receiver);

  /**
   * Retrieves from the DAO the number of unread messages for a user
   * @param user the given user
   * @return the number of unread messages that user has
   */
  int getNoUnreadMessages (User user);

  /**
   * Retrieves a list with all the last messages the user has with everyone
   * @param user the given user
   * @return the list with all of the user's last messages
   */
  List<Message> getLastMessageWithEveryone (User user);

  /**
   * Sends a request to the DAO to add a message to the database, and then fires events
   * @param message the message to be added
   */
  void addMessage (Message message);

  /**
   * Sends a request to the DAO to delete from the database all messages a user has
   * @param user the given user
   */
  void deleteMessagesForUser (User user);

  /**
   * Sends a request to the DAO to mark a certain message as read in the database
   * @param message the message to be marked as read
   */
  void makeMessageRead(Message message);

  /**
   * Prints to the console the number of listeners the server model's support instance has
   */
  void listeners();
}

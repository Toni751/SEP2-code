package ESharing.Server.Persistance.message;

import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;

import java.util.List;

/**
 * The DAO interface for managing database requests related to the chat
 * @version 1.0
 * @author Group1
 */
public interface MessageDAO
{
  /**
   * Retrieves from the database the conversation between 2 users
   * @param sender the first user involved in the conversation
   * @param receiver the second user involved in the conversation
   * @return the list with all messages between them
   */
  List<Message> loadConversation (User sender, User receiver);

  /**
   * Retrieves the number of unread messages for a user
   * @param user the given user
   * @return the number of unread messages
   */
  int getNoUnreadMessages (User user);

  /**
   * Retrieves from the database all last messages a user has, with everyone
   * @param user the given user
   * @return a list with all the last messages
   */
  List<Message> getLastMessageWithEveryone (User user);

  /**
   * Adds to the database message table a new message
   * @param message the message to be added
   */
  void addMessage (Message message);

  /**
   * Delete from the database message table a user's messages
   * @param user the user for which to delete all messsages
   */
  void deleteMessagesForUser (User user);

  /**
   * Marks a message as read in the database message table
   * @param message the message to be marked read
   * @return true if the message was made read successfully, false otherwise
   */
  boolean makeMessageRead(Message message);
}

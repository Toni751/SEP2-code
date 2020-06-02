package ESharing.Server.Persistance.message;

import ESharing.Server.Persistance.Database;
import ESharing.Server.Persistance.address.AddressDAOManager;
import ESharing.Server.Persistance.administrator.AdministratorDAO;
import ESharing.Server.Persistance.administrator.AdministratorDAOManager;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The DAO manager class for handling requests regarding the message table
 * @version 1.0
 * @author Group1
 */
public class MessageDAOManager extends Database implements MessageDAO {
  private static MessageDAOManager instance;
  private static Lock lock = new ReentrantLock();
  private AdministratorDAO administratorDAO;

  /**
   * One-argument constructor initializing the administrator DAO with the given value
   * @param administratorDAO the value to be set for the administrator DAO
   */
  public MessageDAOManager (AdministratorDAO administratorDAO)
  {
      this.administratorDAO = administratorDAO;
  }

  public Connection getConnection() throws SQLException {
    return super.getConnection();
  }


  @Override
  public List<Message> loadConversation(User sender, User receiver) {
    List<Message> conversation = new ArrayList<>();
    try (Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM message WHERE (sender_id = ? AND receiver_id = ?) OR"
              + " (sender_id = ? AND receiver_id = ?) ORDER BY id;");
      statement.setInt(1, sender.getUser_id());
      statement.setInt(2, receiver.getUser_id());
      statement.setInt(3, receiver.getUser_id());
      statement.setInt(4, sender.getUser_id());
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        searchForMessage(sender, conversation, receiver, resultSet);
      }
      return conversation;

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public int getNoUnreadMessages(User user) {
    try (Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS count FROM message WHERE receiver_id = ? AND read = false;");
      statement.setInt(1, user.getUser_id());
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next())
        return resultSet.getInt("count");

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  @Override
  public List<Message> getLastMessageWithEveryone(User user) {
    List<Message> lastMessages = new ArrayList<>();
    try (Connection connection = getConnection()) {
      List<User> allUsers = administratorDAO.getAllUsers();
      for (User allUser : allUsers) {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM get_messages(?, ?) ORDER BY message_id DESC LIMIT 1;");
        statement.setInt(1, user.getUser_id());
        statement.setInt(2, allUser.getUser_id());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
          PreparedStatement messageStatement = connection.prepareStatement("SELECT * FROM message WHERE id = ?;");
          messageStatement.setInt(1, resultSet.getInt("message_id"));
          ResultSet messageResultSet = messageStatement.executeQuery();
          if (messageResultSet.next()) {
            searchForMessage(user, lastMessages, allUser, messageResultSet);
          }
        }
      }
      System.out.println(lastMessages.toString());
      return lastMessages;

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public void addMessage(Message message) {
    try (Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("INSERT INTO message (sender_id, receiver_id, content, sent_time) VALUES (?, ?, ?, NOW()::DATE);");
      statement.setInt(1, message.getSender().getUser_id());
      statement.setInt(2, message.getReceiver().getUser_id());
      statement.setString(3, message.getContent());
      statement.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void deleteMessagesForUser(User user) {
    try (Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("DELETE FROM message WHERE sender_id = ? OR receiver_id = ?;");
      statement.setInt(1, user.getUser_id());
      statement.setInt(2, user.getUser_id());
      statement.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  @Override
  public boolean makeMessageRead(Message message) {
    try (Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement("UPDATE message SET read = ? WHERE receiver_id =?;");
      statement.setBoolean(1, true);
      statement.setInt(2, message.getReceiver().getUser_id());
      System.out.println("Message mark as read");
      int affectedRows = statement.executeUpdate();

      if (affectedRows != 0)
        return true;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Appends a message between 2 users to the last messages list
   * @param user the first user involved in the message
   * @param lastMessages the list with last messages
   * @param allUser the second user involved in the message
   * @param messageResultSet the database result set containing the content of the message
   * @throws SQLException if there is a database connection error
   */
  private void searchForMessage(User user, List<Message> lastMessages, User allUser, ResultSet messageResultSet) throws SQLException {
    Message message = null;
    if (user.getUser_id() == messageResultSet.getInt("sender_id")) {
      message = new Message(user, allUser, messageResultSet.getString("content"), messageResultSet.getBoolean("read"));
    } else {
      message = new Message(allUser, user, messageResultSet.getString("content"), messageResultSet.getBoolean("read"));
    }
    lastMessages.add(message);
  }
}

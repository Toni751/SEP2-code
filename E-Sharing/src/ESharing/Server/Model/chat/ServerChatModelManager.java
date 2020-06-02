package ESharing.Server.Model.chat;

import ESharing.Server.Persistance.administrator.AdministratorDAO;
import ESharing.Server.Persistance.administrator.AdministratorDAOManager;
import ESharing.Server.Persistance.message.MessageDAO;
import ESharing.Server.Persistance.message.MessageDAOManager;
import ESharing.Shared.Util.Events;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 * The server model class for managing chat actions
 * @version 1.0
 * @author Group1
 */
public class ServerChatModelManager implements ServerChatModel
{
  private MessageDAO messageDAO;
  private AdministratorDAO administratorDAO;
  private PropertyChangeSupport support;

  /**
   * 2-arguments constructor initializing the messageDAO and administratorDAO field variables
   * @param messageDAO the new instance value to be set for the message DAO
   * @param administratorDAO the new instance value to be set for the administrator DAO
   */
  public ServerChatModelManager(MessageDAO messageDAO, AdministratorDAO administratorDAO)
  {
    this.messageDAO = messageDAO;
    this.administratorDAO = administratorDAO;
    support = new PropertyChangeSupport(this);
  }

  @Override
  public synchronized List<Message> loadConversation(User sender, User receiver)
  {
    return messageDAO.loadConversation(sender, receiver);
  }

  @Override
  public synchronized int getNoUnreadMessages(User user)
  {
    return messageDAO.getNoUnreadMessages(user);
  }

  @Override
  public synchronized List<Message> getLastMessageWithEveryone(User user)
  {
    return messageDAO.getLastMessageWithEveryone(user);
  }

  @Override
  public synchronized void addMessage(Message message)
  {
    messageDAO.addMessage(message);
    support.firePropertyChange(Events.NEW_MESSAGE_RECEIVED.toString() + message.getReceiver().getUser_id(), null, message);
    support.firePropertyChange(Events.NEW_MESSAGE_RECEIVED.toString() + message.getSender().getUser_id(), null, message);
  }

  @Override
  public synchronized void deleteMessagesForUser(User user)
  {
    messageDAO.deleteMessagesForUser(user);
  }

  @Override
  public synchronized void makeMessageRead(Message message) {
    System.out.println("Attempting to make message read");
    if(messageDAO.makeMessageRead(message)) {
      support.firePropertyChange(Events.MAKE_MESSAGE_READ.toString() + message.getReceiver().getUser_id(), null, message);
    }
  }

  @Override
  public void addPropertyChangeListener(String eventName, PropertyChangeListener listener)
  {
    if ("".equals(eventName) || eventName == null)
      addPropertyChangeListener(listener);
    else
      support.addPropertyChangeListener(eventName, listener);
  }

  @Override
  public void addPropertyChangeListener(PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(listener);
  }

  @Override
  public void removePropertyChangeListener(String eventName, PropertyChangeListener listener)
  {
//    if ("".equals(eventName) || eventName == null)
//      removePropertyChangeListener(listener);
//    else
//    {
//      System.out.println("Support has listener for event: " + eventName + "? " + support.hasListeners(eventName));
//      support.removePropertyChangeListener(eventName, listener);
//    }
    PropertyChangeListener[] listeners = support.getPropertyChangeListeners(eventName);
    System.out.println("Number of listeners for event:" + eventName + " = " + listeners.length);
    for (int i = 0; i < listeners.length; i++)
    {
      support.removePropertyChangeListener(eventName, listeners[i]);
    }
    System.out.println("Support has listener for event: " + eventName + "? " + support.hasListeners(eventName));
  }

  @Override
  public void removePropertyChangeListener(PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(listener);
  }

  @Override
  public void listeners()
  {
    System.out.println("LISTENERS CHAT:" + support.getPropertyChangeListeners().length);
  }
}

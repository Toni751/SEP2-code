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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
  private Lock lock;

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
    lock = new ReentrantLock();
  }

  @Override
  public List<Message> loadConversation(User sender, User receiver)
  {
    List<Message> conversation;
    synchronized (lock)
    {
      conversation = messageDAO.loadConversation(sender, receiver);
    }
    return conversation;
  }

  @Override
  public int getNoUnreadMessages(User user)
  {
    int noUnreadMessages;
    synchronized (lock)
    {
      noUnreadMessages = messageDAO.getNoUnreadMessages(user);
    }
    return noUnreadMessages;
  }

  @Override
  public List<Message> getLastMessageWithEveryone(User user)
  {
    List<Message> lastMessageWithEveryone;
    synchronized (lock)
    {
      lastMessageWithEveryone = messageDAO.getLastMessageWithEveryone(user);
    }
    return lastMessageWithEveryone;
  }

  @Override
  public void addMessage(Message message)
  {
    synchronized (lock)
    {
      messageDAO.addMessage(message);
    }
    support.firePropertyChange(Events.NEW_MESSAGE_RECEIVED.toString() + message.getReceiver().getUser_id(), null, message);
    support.firePropertyChange(Events.NEW_MESSAGE_RECEIVED.toString() + message.getSender().getUser_id(), null, message);
  }

  @Override
  public void deleteMessagesForUser(User user)
  {
    synchronized (lock)
    {
      messageDAO.deleteMessagesForUser(user);
    }
  }

  @Override
  public void makeMessageRead(Message message) {
    System.out.println("Attempting to make message read");
    boolean resultMakeMessageRead;
    synchronized (lock)
    {
      resultMakeMessageRead = messageDAO.makeMessageRead(message);
    }
    if(resultMakeMessageRead) {
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

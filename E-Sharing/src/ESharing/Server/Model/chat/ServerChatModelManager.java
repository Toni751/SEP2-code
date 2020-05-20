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

public class ServerChatModelManager implements ServerChatModel
{
  private MessageDAO messageDAO;
  private AdministratorDAO administratorDAO;
  private PropertyChangeSupport support;

  public ServerChatModelManager(MessageDAO messageDAO, AdministratorDAO administratorDAO)
  {
    this.messageDAO = messageDAO;
    this.administratorDAO = administratorDAO;
    support = new PropertyChangeSupport(this);
  }

  @Override
  public List<Message> loadConversation(User sender, User receiver)
  {
    return messageDAO.loadConversation(sender, receiver);
  }

  @Override
  public int getNoUnreadMessages(User user)
  {
    return messageDAO.getNoUnreadMessages(user);
  }

  @Override
  public List<Message> getLastMessageWithEveryone(User user)
  {
    return messageDAO.getLastMessageWithEveryone(user);
  }

  @Override
  public void addMessage(Message message)
  {
    messageDAO.addMessage(message);
    support.firePropertyChange(Events.NEW_MESSAGE_RECEIVED.toString(), null, message);
  }

  @Override
  public void deleteMessagesForUser(User user)
  {
    messageDAO.deleteMessagesForUser(user);
  }

  @Override
  public void makeMessageRead(Message message) {
    System.out.println("Attempting to make message read");
    if(messageDAO.makeMessageRead(message)) {
      support.firePropertyChange(Events.MAKE_MESSAGE_READ.toString(), null, message);
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
    if ("".equals(eventName) || eventName == null)
      removePropertyChangeListener(listener);
    else
      support.removePropertyChangeListener(eventName, listener);
  }

  @Override
  public void removePropertyChangeListener(PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(listener);
  }

  public void listeners()
  {
    System.out.println("LISTENERS CHAT:" + support.getPropertyChangeListeners().length);
  }
}

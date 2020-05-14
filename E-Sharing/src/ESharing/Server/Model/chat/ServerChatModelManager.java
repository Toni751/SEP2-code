package ESharing.Server.Model.chat;

import ESharing.Server.Persistance.MessageDAO;
import ESharing.Server.Persistance.MessageDAOManager;
import ESharing.Shared.TransferedObject.Events;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;
import jdk.jfr.Event;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class ServerChatModelManager implements ServerChatModel
{
  private MessageDAO messageDAO;
  private PropertyChangeSupport support;

  public ServerChatModelManager()
  {
    this.messageDAO = MessageDAOManager.getInstance();
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
    support.firePropertyChange(Events.NEW_MESSAGE_RECEIVED.toString(), null, message);
    messageDAO.addMessage(message);
  }

  @Override
  public void deleteMessagesForUser(User user)
  {
    messageDAO.deleteMessagesForUser(user);
  }

  @Override
  public void makeMessageRead(Message message) {
    messageDAO.makeMessageRead(message);
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
}
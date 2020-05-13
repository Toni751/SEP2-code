package ESharing.Client.Networking.chat;

import ESharing.Shared.Networking.chat.RMIChatClient;
import ESharing.Shared.Networking.chat.RMIChatServer;
import ESharing.Shared.Networking.user.RMIServer;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ClientChatHandler implements ClientChat, RMIChatClient
{
  private RMIChatServer server;
  private PropertyChangeSupport support;

  /**
   * A constructor which initializes fields and tries to establish connection with the server
   */
  public ClientChatHandler()
  {
    support = new PropertyChangeSupport(this);
    try
    {
      UnicastRemoteObject.exportObject(this, 0);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    while (true)
    {
      try
      {
        server = (RMIChatServer) LocateRegistry.getRegistry("localhost", 1099).lookup("ServerChat");
        System.out.println("Client connected");
        break;
      }
      catch (RemoteException | NotBoundException e)
      {
        try
        {
          System.out.println("Client can't connect with the server... trying again after 5 seconds");
          Thread.sleep(5000);
        }
        catch (InterruptedException ex)
        {
          ex.printStackTrace();
        }
      }
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

  @Override
  public List<Message> loadConversation(User sender, User receiver)
  {
    try
    {
      return server.loadConversation(sender, receiver);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public int getNoUnreadMessages(User user)
  {
    try
    {
      return server.getNoUnreadMessages(user);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    return 0;
  }

  @Override
  public List<Message> getLastMessageWithEveryone(User user)
  {
    try
    {
      return server.getLastMessageWithEveryone(user);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public void addMessage(Message message)
  {
    try
    {
      server.addMessage(message);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  @Override
  public void deleteMessagesForUser(User user)
  {
    try
    {
      server.deleteMessagesForUser(user);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  @Override
  public void newMessageReceived(Message message)
  {

  }

  @Override
  public void newUser(User user)
  {

  }
}

package ESharing.Client.Networking.chat;

import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Networking.Connection;
import ESharing.Server.Core.StubFactory;
import ESharing.Server.Core.StubInterface;
import ESharing.Shared.Networking.chat.RMIChatClient;
import ESharing.Shared.Networking.chat.RMIChatServer;
import ESharing.Shared.Networking.user.RMIServer;
import ESharing.Shared.TransferedObject.Events;
import ESharing.Shared.TransferedObject.Message;
import ESharing.Shared.TransferedObject.User;
import jdk.jfr.Event;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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
    try {
      server = Connection.getStubInterface().getServerChatHandler();
      server.registerChatCallback(this);
    } catch (RemoteException e) {
      e.printStackTrace();
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
  public void makeMessageRead(Message message) {
    try {
      server.makeMessageRead(message);
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void logoutUser() {
    //Oliwer
  }

  @Override public void userLoggedOut()
  {
    try
    {
      server.userLoggedOut(LoggedUser.getLoggedUser().getUser());
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  @Override public List<User> getOnlineUsers()
  {
    try
    {
      return server.getOnlineUsers();
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public void newMessageReceived(Message message) {
    if(LoggedUser.getLoggedUser().getUser().getUser_id() == message.getSender().getUser_id()
        || LoggedUser.getLoggedUser().getUser().getUser_id() == message.getReceiver().getUser_id())
    support.firePropertyChange(Events.NEW_MESSAGE_RECEIVED.toString(), null, message);
    System.out.println("new message on client side");
  }

  @Override
  public void newOnlineUser(User user)
  {
    support.firePropertyChange(Events.USER_ONLINE.toString(),null, user);
    System.out.println("new user in networking");
  }

  @Override public void newOfflineUser(User user) throws RemoteException
  {
    support.firePropertyChange(Events.USER_OFFLINE.toString(),null,user);
    System.out.println("offline user in networking");
  }

}

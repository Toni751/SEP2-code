package ESharing.Client.Networking;

import ESharing.Shared.Networking.RMIClient;
import ESharing.Shared.Networking.RMIServer;
import ESharing.Shared.TransferedObject.Events;
import ESharing.Shared.TransferedObject.User;
import ESharing.Shared.Util.FailedConnection.FailedConnectionViewController;
import ESharing.Shared.Util.FailedConnection.ShowFailedConnectionView;
import javafx.application.Platform;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * The class which handles the requests from the model and communicates
 * with the server through remote method invocation
 * @version 1.0
 * @author Group 1
 */
public class ClientHandler implements Client, RMIClient
{
  private RMIServer server;
  private PropertyChangeSupport support;

  public ClientHandler()
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
        server = (RMIServer) LocateRegistry.getRegistry("localhost", 1099).lookup("Server");
        System.out.println("Client connected");
        break;
      }
      catch (RemoteException | NotBoundException e)
      {
        try
        {
          ShowFailedConnectionView.openFailedConnectionView();
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
  public boolean addUserRequest(User user)
  {
    try
    {
      return server.addUser(user);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean removeUserRequest(User user)
  {
    try
    {
      return server.removeUser(user);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean editUserRequest(User user)
  {
    try
    {
      return server.editUser(user);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public User loginUserRequest(String username, String password)
  {
    try
    {
      return server.loginUser(username, password, this);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    return null;
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

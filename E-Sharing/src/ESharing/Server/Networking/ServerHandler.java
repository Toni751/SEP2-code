package ESharing.Server.Networking;

import ESharing.Server.Model.ServerModel;
import ESharing.Shared.Networking.RMIClient;
import ESharing.Shared.Networking.RMIServer;
import ESharing.Shared.TransferedObject.Events;
import ESharing.Shared.TransferedObject.User;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * The class handles all requests which are received from clients
 * @version 1.0
 * @author Group 1
 */
public class ServerHandler implements RMIServer
{
  private ServerModel serverModel;
  private PropertyChangeListener listenForNewUser;
  private PropertyChangeListener listenForUserRemoved;
  private PropertyChangeListener listenForUserUpdated;

  /**
   * A constructor initializes fields and starts the internet connection
   * @param serverModel the server model which manages all requests
   * @throws RemoteException if the method invocation fails
   */
  public ServerHandler(ServerModel serverModel) throws RemoteException
  {
    UnicastRemoteObject.exportObject(this, 0);
    this.serverModel = serverModel;
  }

  @Override
  public boolean addUser(User user)
  {
    return serverModel.addNewUser(user);
  }

  @Override
  public boolean removeUser(User user)
  {
    return serverModel.removeUser(user);
  }

  @Override
  public boolean editUser(User user)
  {
    return serverModel.editUser(user);
  }

  @Override
  public User loginUser(String username, String password, RMIClient client)
  {
    return serverModel.loginUser(username, password);
  }

  @Override
  public void registerAdministratorCallback(RMIClient client) {
    listenForNewUser = evt -> {
      try {
        client.newUserReceived((User) evt.getNewValue());
      } catch (Exception e) {
        e.printStackTrace();
      }

    };

    listenForUserRemoved = evt -> {
      try {
        client.userRemoved((User) evt.getNewValue());
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    };

    listenForUserUpdated = evt -> {
      try {
        client.userUpdated((User) evt.getNewValue());
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    };

    serverModel.addPropertyChangeListener(Events.NEW_USER_CREATED.toString(), listenForNewUser);
    serverModel.addPropertyChangeListener(Events.USER_REMOVED.toString(), listenForUserRemoved);
    serverModel.addPropertyChangeListener(Events.USER_UPDATED.toString(), listenForUserUpdated);
  }

  @Override
  public List<User> getAllUsers() throws RemoteException {
    return serverModel.getAllUsers();
  }
}

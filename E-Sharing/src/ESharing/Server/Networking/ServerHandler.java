package ESharing.Server.Networking;

import ESharing.Server.Model.ServerModel;
import ESharing.Shared.Networking.RMIClient;
import ESharing.Shared.Networking.RMIServer;
import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * The class handles all requests which are received from clients
 * @version 1.0
 * @author Group 1
 */
public class ServerHandler implements RMIServer
{
  private ServerModel serverModel;

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

}

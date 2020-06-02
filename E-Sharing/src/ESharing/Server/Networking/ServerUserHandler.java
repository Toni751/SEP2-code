package ESharing.Server.Networking;

import ESharing.Server.Model.user.ServerUserModel;
import ESharing.Shared.Networking.user.RMIUserClient;
import ESharing.Shared.Networking.user.RMIUserServer;
import ESharing.Shared.Util.Events;
import ESharing.Shared.TransferedObject.User;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * The class handles all requests which are received from clients for handling user actions
 * @version 1.0
 * @author Group 1
 */
public class ServerUserHandler implements RMIUserServer
{
  private ServerUserModel serverUserModel;
  private PropertyChangeListener listenForNewUser;
  private PropertyChangeListener listenForUserRemoved;
  private PropertyChangeListener listenForUserUpdated;
  private PropertyChangeListener listenForAvatarUpdated;
  private PropertyChangeListener listenForNewUserReported;

  /**
   * A constructor initializes the server user model, and exports the objects
   * @param serverUserModel the value to be set for the server model
   */
  public ServerUserHandler(ServerUserModel serverUserModel) throws RemoteException
  {
    UnicastRemoteObject.exportObject(this, 0);
    this.serverUserModel = serverUserModel;
  }

  @Override
  public boolean addUser(User user)
  {
    return serverUserModel.addNewUser(user);
  }

  @Override
  public boolean removeUser(User user)
  {
    return serverUserModel.removeUser(user);
  }

  @Override
  public boolean editUser(User user)
  {
    return serverUserModel.editUser(user);
  }

  @Override
  public User loginUser(String username, String password)
  {
    return serverUserModel.loginUser(username, password);
  }

  @Override
  public void registerAdministratorCallback(RMIUserClient client) {
    listenForNewUser = evt -> {
      try {
        client.newUserReceived((User) evt.getNewValue());
      } catch (Exception e) {
        e.printStackTrace();
      }

    };
    serverUserModel.addPropertyChangeListener(Events.NEW_USER_CREATED.toString(), listenForNewUser);
  }

  @Override
  public void registerGeneralCallback(RMIUserClient client){
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

    listenForAvatarUpdated = evt -> {
      try {
        client.avatarUpdated((int)evt.getOldValue(), (byte[]) evt.getNewValue());
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    };

    listenForNewUserReported = evt -> {
      try {
        client.userReported((int) evt.getOldValue(), (int) evt.getNewValue());
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    };

    serverUserModel.addPropertyChangeListener(Events.USER_REMOVED.toString(), listenForUserRemoved);
    serverUserModel.addPropertyChangeListener(Events.USER_UPDATED.toString(), listenForUserUpdated);
    serverUserModel.addPropertyChangeListener(Events.UPDATE_AVATAR.toString(), listenForAvatarUpdated);
    serverUserModel.addPropertyChangeListener(Events.NEW_USER_REPORT.toString(), listenForNewUserReported);
  }

  @Override
  public List<User> getAllUsers(){
    return serverUserModel.getAllUsers();
  }

  @Override
  public void unRegisterUserAsAListener() {

    serverUserModel.removePropertyChangeListener(Events.USER_REMOVED.toString(), listenForUserRemoved);
    serverUserModel.removePropertyChangeListener(Events.USER_UPDATED.toString(), listenForUserUpdated);
    serverUserModel.removePropertyChangeListener(Events.NEW_USER_CREATED.toString(), listenForNewUser);
    serverUserModel.removePropertyChangeListener(Events.UPDATE_AVATAR.toString(), listenForAvatarUpdated);
    serverUserModel.removePropertyChangeListener(Events.NEW_USER_REPORT.toString(), listenForNewUserReported);

    serverUserModel.listeners();
  }

  @Override
  public void changeUserAvatar(byte[] avatarImage, int userId){
    serverUserModel.changeUserAvatar(avatarImage, userId);
  }

  @Override
  public boolean addNewUserReport(int user_id){
    return serverUserModel.addNewUserReport(user_id);
  }
}

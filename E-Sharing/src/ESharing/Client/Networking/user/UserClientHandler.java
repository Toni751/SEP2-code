package ESharing.Client.Networking.user;

import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Networking.Connection;
import ESharing.Shared.Networking.advertisement.RMIAdvertisementServer;
import ESharing.Shared.Networking.chat.RMIChatServer;
import ESharing.Shared.Networking.user.RMIUserClient;
import ESharing.Shared.Networking.user.RMIUserServer;
import ESharing.Shared.Util.Events;
import ESharing.Shared.TransferedObject.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * The class which handles the requests from the model and communicates
 * with the server through remote method invocation
 * @version 1.0
 * @author Group 1
 */
public class UserClientHandler implements UserClient, RMIUserClient
{
  private RMIUserServer server;
  private RMIChatServer chatServer;
  private RMIAdvertisementServer rmiAdvertisementServer;
  private PropertyChangeSupport support;

  /**
   * A constructor which initializes fields and tries to establish connection with the server
   */
  public UserClientHandler()
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
  }

  @Override
  public void initializeConnection()
  {
    try {
    server = Connection.getStubInterface().getServerRMI();
    chatServer = Connection.getStubInterface().getServerChatHandler();
    rmiAdvertisementServer = Connection.getStubInterface().getServerAdHandler();
      System.out.println(chatServer);
  }
    catch (RemoteException | NullPointerException e) {
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
      User loggedUser = server.loginUser(username, password);
      if(loggedUser != null && loggedUser.isAdministrator()) {
        server.registerAdministratorCallback(this);
      }
      server.registerGeneralCallback(this);
      return loggedUser;
    }
    catch (RemoteException e) {e.printStackTrace();}
    return null;
  }

  @Override
  public List<User> getAllUsersRequest() {
    try {
      return server.getAllUsers();
    } catch (RemoteException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public void logout() {
    try
    {
      server.unRegisterUserAsAListener();
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  @Override
  public void changeAvatar(byte[] avatarImage) {
    try {
      server.changeUserAvatar(avatarImage, LoggedUser.getLoggedUser().getUser().getUser_id());
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean addNewUserReport(int user_id) {
    try {
      return server.addNewUserReport(user_id);
    } catch (RemoteException e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public void newUserReceived(User newUser) {
    support.firePropertyChange(Events.NEW_USER_CREATED.toString(), null, newUser);
  }

  @Override
  public void userRemoved(User user){
    support.firePropertyChange(Events.USER_REMOVED.toString(), null, user);
  }

  @Override
  public void userUpdated(User user) {
    support.firePropertyChange(Events.USER_UPDATED.toString(), null, user);
  }

  @Override
  public void avatarUpdated(int userID, byte[] avatar){
    support.firePropertyChange(Events.UPDATE_AVATAR.toString(), userID, avatar);
  }

  @Override
  public void userReported(int userID, int reports){
    support.firePropertyChange(Events.NEW_USER_REPORT.toString(), userID, reports);
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

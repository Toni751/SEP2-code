package ESharing.Server.Model.user;

import ESharing.Server.Persistance.administrator.AdministratorDAO;
import ESharing.Server.Persistance.administrator.AdministratorDAOManager;
import ESharing.Server.Persistance.user.UserDAO;
import ESharing.Server.Persistance.user.UserDAOManager;
import ESharing.Shared.Util.Events;
import ESharing.Shared.TransferedObject.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * The class manages all requests on the server side
 * @version 1.0
 * @author Group 1
 */
public class ServerModelManager implements ServerModel
{
  private UserDAO userDAO;
  private AdministratorDAO administratorDAO;
  private PropertyChangeSupport support;
  private List<User> onlineUsers;

  /**
   * A constructor initializes all fields
   */
  public ServerModelManager(UserDAO userDAO, AdministratorDAO administratorDAO)
  {
    this.userDAO = userDAO;
    this.administratorDAO = administratorDAO;
    support = new PropertyChangeSupport(this);
    onlineUsers = new ArrayList<>();
  }

  @Override
  public boolean addNewUser(User user)
  {
    String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
    user.setCreation_date(currentDate);
    boolean result = userDAO.create(user);
    if(result)
      support.firePropertyChange(Events.NEW_USER_CREATED.toString(), null, user);
    return result;

  }

  @Override
  public boolean removeUser(User user)
  {
    boolean result = userDAO.delete(user);
    if(result)
        support.firePropertyChange(Events.USER_REMOVED.toString(), null, user);
    return result;
  }

  @Override
  public boolean editUser(User user)
  {
    boolean result = userDAO.update(user);
    if(result)
    {
     support.firePropertyChange(Events.USER_UPDATED.toString(), null, user);
    }
    return result;
  }

  @Override
  public User loginUser(String username, String password)
  {
   User user = userDAO.readByUserNameAndPassword(username, password);
   if(user != null)
   {
     onlineUsers.add(user);
     support.firePropertyChange(Events.USER_ONLINE.toString(), null, user);
   }
   return user;
  }

  @Override
  public List<User> getAllUsers() {
    List<User> users = administratorDAO.getAllUsers();
    for(int i = 0 ; i<users.size() ; i++)
    {
      if(users.get(i).isAdministrator())
        users.remove(users.get(i));
    }
    return users;
  }

  @Override public void userLoggedOut(User user)
  {
    onlineUsers.remove(user);
    support.firePropertyChange(Events.USER_OFFLINE.toString(),null,user);
  }

  @Override public List<User> getAllOnlineUsers()
  {
    return onlineUsers;
  }

  @Override
  public void changeUserAvatar(byte[] avatarByte, int userId) {
    try {
      File avatar = new File("E-Sharing/Resources/User"+ userId + "/avatar.jpg");
      FileOutputStream out = new FileOutputStream(avatar);
      out.write(avatarByte);
      out.close();

      if(userDAO.changeAvatar(avatar.toPath().toString(), userId));
        support.firePropertyChange(Events.UPDATE_AVATAR.toString(), null, avatarByte);
    } catch (IOException e) {
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
  public void listeners()
  {
    System.out.println("LISTENERS USER ACTION:" + support.getPropertyChangeListeners().length);
  }

  @Override
  public boolean addNewUserReport(int user_id) {
    return userDAO.addNewUserReport(user_id);
  }

}

package ESharing.Server.Model;

import ESharing.Server.Persistance.UserDAO;
import ESharing.Shared.TransferedObject.User;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The class manages all requests on the server side
 * @version 1.0
 * @author Group 1
 */
public class ServerModelManager implements ServerModel
{
  private UserDAO userDAO;
  private PropertyChangeSupport support;

  /**
   * A constructor initializes all fields
   * @param userDAO
   */
  public ServerModelManager(UserDAO userDAO)
  {
    this.userDAO = userDAO;
    support = new PropertyChangeSupport(this);
  }

  @Override
  public boolean addNewUser(User user)
  {
    System.out.println("Creating a new user from SMM");
    if (user.getPassword().length() > 7 && user.getPhoneNumber().length() == 8)
    {
      return userDAO.create(user);
    }
    return false;
  }

  @Override
  public boolean removeUser(User user)
  {
    return userDAO.delete(user);
  }

  @Override
  public boolean editUser(User user)
  {
    if (user.getPassword().length() > 7 && user.getPhoneNumber().length() == 8)
    {
      return userDAO.update(user);
    }
    return false;
  }

  @Override
  public User loginUser(String username, String password)
  {
    return userDAO.readByUserNameAndPassword(username, password);
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

package ESharing.Server.Model.user;

import ESharing.Server.Model.user.ServerModel;
import ESharing.Server.Persistance.AdministratorDAO;
import ESharing.Server.Persistance.AdministratorDAOManager;
import ESharing.Server.Persistance.UserDAO;
import ESharing.Shared.TransferedObject.Events;
import ESharing.Shared.TransferedObject.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.SimpleDateFormat;
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

  /**
   * A constructor initializes all fields
   * @param userDAO
   */
  public ServerModelManager(UserDAO userDAO)
  {
    this.userDAO = userDAO;
    this.administratorDAO = AdministratorDAOManager.getInstance();
    support = new PropertyChangeSupport(this);
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

    ///
    ///Test conversation
    ///

//    User user = userDAO.readByUserNameAndPassword(username, password);
//    Conversation conversation1 = new Conversation(user, getAllUsers().get(2));
//    conversation1.addMessage(new Message(user, getAllUsers().get(2), "Hello"));
//    conversation1.addMessage(new Message(getAllUsers().get(2), user,  "Oh Hello"));
//
//
//    Conversation conversation2 = new Conversation(user, getAllUsers().get(3));
//    conversation2.addMessage(new Message(user, getAllUsers().get(3), "Hello"));
//    conversation2.addMessage(new Message(getAllUsers().get(3), user,  "Oh Hello"));
//
//    user.addConversation(conversation1);
//    user.addConversation(conversation2);
    return userDAO.readByUserNameAndPassword(username, password);
  }

  @Override
  public List<User> getAllUsers() {
    return administratorDAO.getAllUsers();
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

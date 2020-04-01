package ESharing.Server.Model;

import ESharing.Server.Persistance.Database;
import ESharing.Shared.TransferedObject.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ServerModelManager implements ServerModel
{
  private Database database;
  private PropertyChangeSupport support;

  public ServerModelManager(Database database)
  {
    this.database = database;
    support = new PropertyChangeSupport(this);
  }

  @Override
  public boolean addNewUser(User user)
  {
    return false;
  }

  @Override
  public boolean removeUser(User user)
  {
    return false;
  }

  @Override
  public boolean editUser(User user)
  {
    return false;
  }

  @Override
  public User loginUser(String username, String password)
  {
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

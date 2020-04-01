package ESharing.Shared.Util;

import java.beans.PropertyChangeListener;

/**
 * Interface for adding and removing listeners to events
 */
public interface PropertyChangeSubject
{
  void addPropertyChangeListener (String eventName, PropertyChangeListener listener);
  void addPropertyChangeListener (PropertyChangeListener listener);
  void removePropertyChangeListener (String eventName, PropertyChangeListener listener);
  void removePropertyChangeListener (PropertyChangeListener listener);
}

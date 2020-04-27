package ESharing.Shared.Util;

import java.beans.PropertyChangeListener;

/**
 * Interface for adding and removing listeners to events
 * @version 1.0
 * @author Group1
 */
public interface PropertyChangeSubject
{
  /**
   * Adds new listener to the given event
   * @param eventName the name of the event
   * @param listener the given listener
   */
  void addPropertyChangeListener (String eventName, PropertyChangeListener listener);

  /**
   * Adds new listener
   * @param listener the given listener
   */
  void addPropertyChangeListener (PropertyChangeListener listener);

  /**
   * Removes the given listener from the given event
   * @param eventName the name of the event
   * @param listener the given listener
   */
  void removePropertyChangeListener (String eventName, PropertyChangeListener listener);

  /**
   * Removes the given listener
   * @param listener the given listener
   */
  void removePropertyChangeListener (PropertyChangeListener listener);
}

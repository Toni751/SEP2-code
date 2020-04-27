package ESharing.Client.Model.AppModel;

import ESharing.Shared.Util.PropertyChangeSubject;

/**
 * The interface used to connect a view model layer with a model layer for all main application features.
 * @version 1.0
 * @author Group1
 */
public interface AppOverviewModel extends PropertyChangeSubject {
    /**
     * Loads all advertisements created by users
     */
    void loadAdvertisements();
}

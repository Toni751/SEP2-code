package ESharing.Client.Model.AppModel;

import ESharing.Shared.Util.PropertyChangeSubject;

public interface AppOverviewModel extends PropertyChangeSubject {
    void loadAdvertisements();
}

package ESharing.Server.Model.advertisement;

import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.Util.PropertyChangeSubject;

public interface ServerAdvertisementModel extends PropertyChangeSubject {

    boolean addAdvertisement(Advertisement advertisement);
    boolean removeAdvertisement(Advertisement advertisement);
}

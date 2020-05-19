package ESharing.Server.Model.advertisement;

import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.Util.PropertyChangeSubject;

import java.util.List;

public interface ServerAdvertisementModel extends PropertyChangeSubject {

    boolean addAdvertisement(Advertisement advertisement);
    void approveAdvertisement (Advertisement ad);
    boolean removeAdvertisement(Advertisement advertisement);
    boolean editAdvertisement (Advertisement ad);
    List<Advertisement> selectAllAdvertisements();
}

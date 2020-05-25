package ESharing.Server.Model.advertisement;

import ESharing.Shared.TransferedObject.AdCatalogueAdmin;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.Util.PropertyChangeSubject;

import java.util.List;

public interface ServerAdvertisementModel extends PropertyChangeSubject {

    boolean addAdvertisement(Advertisement advertisement);
    boolean approveAdvertisement (int id);
    boolean removeAdvertisement(int id);
//    boolean editAdvertisement (Advertisement ad);
    List<Advertisement> selectAllAdvertisements();
    List<CatalogueAd> getAdvertisementsCatalogue();
    List<CatalogueAd> getAdvertisementsCatalogueForUser(int id);
    Advertisement getAdvertisementById (int id);
    boolean addNewAdvertisementReport(int advertisementID);
    List<AdCatalogueAdmin> getAdminAdCatalogue();
    boolean addRating (int ad_id, int user_id, double rating);
    double retrieveAdRating (int ad_id);
}

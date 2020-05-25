package ESharing.Client.Networking.advertisement;

import ESharing.Shared.TransferedObject.AdCatalogueAdmin;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;
import ESharing.Shared.Util.PropertyChangeSubject;

import java.util.List;

public interface ClientAdvertisement extends PropertyChangeSubject
{
  void initializeConnection();
  boolean addAdvertisement (Advertisement ad);
  boolean approveAdvertisement (int id);
  boolean removeAdvertisement (int id);
//  boolean editAdvertisement (Advertisement ad);
  List<Advertisement> selectAllAdvertisement();

  boolean reportAdvertisement(int advertisementID);

    List<CatalogueAd> getAllCatalogues();
  List<AdCatalogueAdmin> getAdminAdCatalogue();
  Advertisement getAdvertisement(int advertisementID);

    void registerForCallBack();

    List<CatalogueAd> getAllUserCatalogues(int user_id);
  boolean addRating (int ad_id, int user_id, double rating);
  double retrieveAdRating (int ad_id);
}

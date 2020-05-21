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
  boolean approveAdvertisement (Advertisement ad);
  boolean removeAdvertisement (Advertisement ad);
//  boolean editAdvertisement (Advertisement ad);
  List<Advertisement> selectAllAdvertisement();

  boolean reportAdvertisement(int advertisementID);

    List<CatalogueAd> getAllCatalogues();
  List<AdCatalogueAdmin> getAdminAdCatalogue();
  Advertisement getAdvertisement(int advertisementID);
}

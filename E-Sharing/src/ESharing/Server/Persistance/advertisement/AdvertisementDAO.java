package ESharing.Server.Persistance.advertisement;

import ESharing.Shared.TransferedObject.AdCatalogueAdmin;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;

import java.util.List;

public interface AdvertisementDAO {

  int create(Advertisement advertisement);
  boolean removeAdvertisement(Advertisement advertisement);
  void addImagesAndDates(Advertisement advertisement);
  List<Advertisement> getAllAdvertisements();
  CatalogueAd approveAdvertisement(Advertisement advertisement);
  List<CatalogueAd> getAdvertisementsCatalogue();
  Advertisement getAdvertisementById (int id);
  List<CatalogueAd> getAdvertisementsByUser(int user_id);
  boolean addNewAdvertisementReport(int advertisementID);
  List<AdCatalogueAdmin> getAdminAdCatalogue();
}

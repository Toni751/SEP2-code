package ESharing.Server.Persistance.advertisement;

import ESharing.Shared.TransferedObject.AdCatalogueAdmin;
import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;

import java.util.List;

public interface AdvertisementDAO {

  int create(Advertisement advertisement);
  int removeAdvertisement(int id);
  void addImagesAndDates(Advertisement advertisement);
  List<Advertisement> getAllAdvertisements();
  CatalogueAd approveAdvertisement(int id);
  List<CatalogueAd> getAdvertisementsCatalogue();
  Advertisement getAdvertisementById (int id);
  List<CatalogueAd> getAdvertisementsByUser(int user_id);
  int addNewAdvertisementReport(int advertisementID);
  List<AdCatalogueAdmin> getAdminAdCatalogue();
  AdCatalogueAdmin getAdminAdCatalogue(int id);
}

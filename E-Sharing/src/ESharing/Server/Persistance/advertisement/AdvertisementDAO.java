package ESharing.Server.Persistance.advertisement;

import ESharing.Shared.TransferedObject.Advertisement;
import ESharing.Shared.TransferedObject.CatalogueAd;

import java.util.List;

public interface AdvertisementDAO {

  int create(Advertisement advertisement);
  boolean removeAdvertisement(Advertisement advertisement);
  boolean updateAdvertisement(Advertisement advertisement);
  void addImagesAndDates(Advertisement advertisement);
  List<Advertisement> getAllAdvertisements();
  CatalogueAd approveAdvertisement(Advertisement advertisement);
  List<CatalogueAd> getAdvertisementsCatalogue();
  Advertisement getAdvertisementById (int id);
}

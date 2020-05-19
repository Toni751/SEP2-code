package ESharing.Server.Persistance.advertisement;

import ESharing.Shared.TransferedObject.Advertisement;

import java.util.List;

public interface AdvertisementDAO {

  int create(Advertisement advertisement);
  boolean removeAdvertisement(Advertisement advertisement);
  void addImagesAndDates(Advertisement advertisement);
  List<Advertisement> getAllAdvertisements();
  void approveAdvertisement(Advertisement advertisement);



}

package ESharing.Server.Persistance;

import ESharing.Shared.TransferedObject.Advertisement;

public interface AdvertisementDAO {

  int create(Advertisement advertisement);
  boolean removeAdvertisement(Advertisement advertisement);
  void addImagesAndDates(Advertisement advertisement);

}

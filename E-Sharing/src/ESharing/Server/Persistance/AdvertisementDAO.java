package ESharing.Server.Persistance;

import ESharing.Shared.TransferedObject.Advertisement;

import java.util.List;

public interface AdvertisementDAO {

  int create(Advertisement advertisement);
  boolean removeAdvertisement(Advertisement advertisement);
  void addImagesAndDates(Advertisement advertisement);
  List<Advertisement> getAllAdvertisements();
  boolean approveAdvertisement(Advertisement advertisement);



}

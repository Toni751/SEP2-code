package ESharing.Server.Persistance;

import ESharing.Shared.TransferedObject.Advertisement;

public interface AdvertisementDAO {

  boolean create(Advertisement advertisement);
  boolean removeAdvertisement(Advertisement advertisement);

}
